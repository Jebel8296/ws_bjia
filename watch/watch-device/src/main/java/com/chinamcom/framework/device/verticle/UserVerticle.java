package com.chinamcom.framework.device.verticle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.CmdConstants;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.model.ClockInfo;
import com.chinamcom.framework.device.model.GroupUser;
import com.chinamcom.framework.device.model.UserDevice;
import com.chinamcom.framework.device.service.ChatSessionService;
import com.chinamcom.framework.device.service.FriendshipService;
import com.chinamcom.framework.device.service.UserDeviceService;
import com.chinamcom.framework.device.service.UserService;

/**
 * Created by Administrator on 2016/7/5.
 */
@Service
public class UserVerticle extends BaseVerticle {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
	private UserDeviceService userDeviceService;
    
    @Autowired
	private UserService userService;
    
    @Autowired
    private FriendshipService friendshipService;
    
    @Autowired
    private PropertyConfigLoader config;
    
    @Autowired
    private ChatSessionService chatSessionService;
    
    private Lock lock = new ReentrantLock();
    
	@Override
    public void start() throws Exception {

        // 用户设备查询,需要排除当前设备
        vertx.eventBus().consumer("user.device.query", message -> {
            String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				cmd = getCmd(request);
				log.info("user.device.query接收参数："+request.toString());
                Integer uid = request.getInteger("to");
                String did = request.getString("did");
                String device_type = request.getString("device_type");
                if(did == null){
                	did = "0";
                }
                String sql = "select * from app_user_device where uid = ? and status = 1 and did <> ?";
                if (uid >= Constants.GROUP_ID) {
                    sql = "SELECT * FROM app_user_device WHERE STATUS = 1 AND uid IN(SELECT UID FROM app_group_user t WHERE t.GID = ? and t.screenstat = 0 and t.`status` = 1 ) and did <> ?";
                }
                List<UserDevice> userDevices = null;
                if(device_type != null){
                	sql = "select * from app_user_device where uid = ? and status = 1 and device_type = ?";
                	userDevices = jdbcTemplate.query(sql, new Object[]{uid, device_type}, (rs, i) -> {
	                    UserDevice ud = new UserDevice();
	                    ud.setId(rs.getInt("id"));
	                    ud.setUid(rs.getInt("uid"));
	                    ud.setDid(rs.getString("did"));
	                    ud.setStatus(rs.getInt("status"));
	                    ud.setServer(rs.getString("server"));
	                    ud.setDevice_type(rs.getString("device_type"));
	                    ud.setApple_id(rs.getString("apple_id"));
	                    ud.setEnv(rs.getString("env"));
	                    return ud;
	                });
                }else{
	                userDevices = jdbcTemplate.query(sql, new Object[]{uid, did}, (rs, i) -> {
	                    UserDevice ud = new UserDevice();
	                    ud.setId(rs.getInt("id"));
	                    ud.setUid(rs.getInt("uid"));
	                    ud.setDid(rs.getString("did"));
	                    ud.setStatus(rs.getInt("status"));
	                    ud.setServer(rs.getString("server"));
	                    ud.setDevice_type(rs.getString("device_type"));
	                    ud.setApple_id(rs.getString("apple_id"));
	                    ud.setEnv(rs.getString("env"));
	                    return ud;
	                });
                }
                JSONObject response_data = new JSONObject();
                response_data.put("data", userDevices);
                log.info("user.device.query结果："+response_data.toString());
                message.reply(response_data.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number,cmd));
            }
        });
        
        //用户设备在线状态更新
        vertx.eventBus().consumer("user.status.update", message -> {
            log.debug("user.status.update: "+ message.body());
            String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				cmd = getCmd(request);
                Integer uid = request.getInteger("uid");
                String did = request.getString("did");
                String server = request.getString("server");
                String device_type = request.getString("device_type");
                Integer status = request.getInteger("status");
                String apple_id = request.getString("apple_id");
                String env = request.getString("env");
                int ret = 0;
                if(uid == null){
                	String sql = "SELECT MAX(UID) FROM app_user t WHERE t.IMEI = ? ";
                    Integer _newKey = jdbcTemplate.queryForObject(sql, new Object[]{did}, Integer.class);
                    if(_newKey != null){
                    	uid = _newKey;
                    }else{
                    	uid = 0;
                    }
                }
                if(StringUtil.isNotEmpty(server)){
                	ret = jdbcTemplate.update("update app_user_device set status = ?, server = ?, apple_id = ?,env = ? where did = ? and uid = ?", status, server, apple_id, env, did, uid);
                }else{
                	ret = jdbcTemplate.update("update app_user_device set status = ?, apple_id = ?,env = ? where did = ? and uid = ?", status, apple_id, env, did, uid);
                }
                if (ret <= 0) {
                    jdbcTemplate.update("insert into app_user_device(UID,DID,STATUS,SERVER,DEVICE_TYPE,APPLE_ID,ENV) values(?,?,?,?,?,?,?)", uid, did, 1, server, device_type, apple_id,env);
                }
                
                JSONObject data = new JSONObject();
                data.put("uid", uid);
                message.reply(data.toJSONString());
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number, cmd));
            }
        });
        
        
      //用户设备在线状态更新
        vertx.eventBus().consumer("login.status.update", message -> {
            log.info("user.status.update: "+ message.body());
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
                Integer uid = request.getInteger("uid");
                String did = request.getString("did");
                jdbcTemplate.update("update app_user_device set status = ? where did = ? and uid = ?", 1, did, uid);
            	JSONObject data = new JSONObject();
            	data.put("uid", uid);
            	data.put("did", did);
            	vertx.eventBus().send("notify.push.offline", data.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
            }
        });
        
        //更新app在线状态，防止一个app登陆多个手表时消息推送在线app问题
        vertx.eventBus().consumer("device.status.update", message -> {
            log.info("device.status.update: "+ message.body());
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
                Integer uid = request.getInteger("uid");
                String did = request.getString("did");
                jdbcTemplate.update("update app_user_device set status = ? where did = ? and uid = ?", 1, did, uid);
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
            }
        });

        vertx.eventBus().consumer("data.chatGroup.get", message -> {
            log.debug("data.chatGroup.get: " + message.body());
            String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				cmd = getCmd(request);
				String cn = request.getString("cn");
                Integer uid = request.getInteger("uid");
                String sql = "SELECT g.*, IFNULL(gu.alias, c.nickname) alias, gu.screenstat FROM app_group_user gu "
                		+ " LEFT JOIN app_group g ON gu.gid = g.id LEFT JOIN app_userinfo c ON c.uid = gu.UID "
                		+ " WHERE gu.uid = ? AND gu. STATUS = 1 AND g. STATUS = 1";
                List<GroupUser> groups = jdbcTemplate.query(sql, new Object[]{uid}, new RowMapper<GroupUser>() {
                    @Override
                    public GroupUser mapRow(ResultSet rs, int i) throws SQLException {
                    	if(Constants.CHANNEL_WATCH.equals(cn)){
                    		GroupUser gu = new GroupUser();
                            gu.setId(rs.getInt("id"));
                            gu.setName(rs.getString("name"));
                            return gu;
                    	}else{
                    		GroupUser gu = new GroupUser();
                            gu.setId(rs.getInt("id"));
                            gu.setName(rs.getString("name"));
                            gu.setHead(config.get("uploadFile.host") + rs.getString("head"));
                            gu.setCreate_time(rs.getDate("create_time"));
                            gu.setCreate_uid(rs.getInt("create_uid"));
                            gu.setStatus(rs.getInt("status"));
                            gu.setDescription(rs.getString("description"));
                            gu.setAlias(rs.getString("alias"));
                            gu.setScreenstat(rs.getInt("screenstat"));
                            return gu;
                    	}
                    }
                });
                JSONObject data = new JSONObject();
                data.put("data", groups);
                message.reply(respWriter.toCmdSuccess(serial_number, cmd, data));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number, cmd));
            }
        });
        
        vertx.eventBus().consumer("data.chatSession.get", message -> {
            log.info("data.chatSession.get: " + message.body());
            String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				cmd = getCmd(request);
				String cn = request.getString("cn");
                Integer uid = request.getInteger("uid");
                String sqlUnion = "SELECT DISTINCT id,uid,headimage,name,create_uid,screenstat,top_status,alias,CASE WHEN create_time='' THEN NOW() WHEN create_time IS NULL THEN NOW() ELSE create_time END AS create_time FROM ( "+
						" SELECT a.sid id, a.uid,b.screenstat,c.head headimage, c.`name`, c.create_uid,a.top_status,a.top_time,b.alias FROM chat_session_user a  LEFT JOIN app_group_user b on a.sid = b.gid and a.uid = b.uid  LEFT JOIN app_group c on a.sid = c.id   where a.uid = ? and a.sid >= "+Constants.GROUP_ID+"  and a.`status` = 1 and b.`status` = 1 and c.`status` = 1 "+
						" union all "+
						" SELECT a.sid id,a.uid,a.screenstat,c.headimage headimage, CASE IFNULL(b.ALIAS_0,'') WHEN '' THEN c.nickname ELSE b.ALIAS_0 END name, NULL as create_uid,a.top_status,a.top_time, null as alias FROM (SELECT sid,uid,screenstat,top_status,top_time FROM chat_session_user where sid in(SELECT sid FROM chat_session_user where uid = ? and sid < " + Constants.GROUP_ID + ") and uid != ? and status = 1) a  LEFT JOIN (SELECT UID_1,ALIAS_0 FROM app_friendship where UID_0 = ?) b ON b.UID_1 = a.uid LEFT JOIN app_userinfo c on a.uid = c.uid "+
						" ) a LEFT JOIN (SELECT MAX(create_time) CREATE_TIME,FROM_GID FROM app_user_message  where UID = ? OR FROM_ID = ? GROUP BY FROM_GID) b on a.id = b.FROM_GID ORDER BY top_status DESC, a.top_time DESC ";
                List<GroupUser> sessions = jdbcTemplate.query(sqlUnion, new Object[]{uid,uid,uid,uid,uid,uid}, new RowMapper<GroupUser>() {
                    @Override
                    public GroupUser mapRow(ResultSet rs, int i) throws SQLException {
                    	if(Constants.CHANNEL_WATCH.equals(cn)){
                    		GroupUser gu = new GroupUser();
                            gu.setId(rs.getInt("id"));
                            gu.setName(rs.getString("name"));
                            return gu;
                    	}else{
                    		GroupUser gu = new GroupUser();
                            gu.setId(rs.getInt("id"));
                            gu.setUid(rs.getInt("uid"));
                            gu.setName(rs.getString("name"));
                            String headImg = "";
                            if(rs.getString("headimage") != null){
                    			headImg = "" + rs.getString("headimage");
                    			if(!headImg.contains("http://")){
                    				headImg = config.get("uploadFile.host") + headImg;
                    			}
                    		}
                            gu.setHead(headImg);
                            gu.setCreate_uid(rs.getInt("create_uid"));
                            gu.setCreate_time(rs.getDate("create_time"));
                            gu.setAlias(rs.getString("alias"));
                            gu.setScreenstat(rs.getInt("screenstat"));
                            gu.setTopStatus(rs.getInt("top_status"));
                            return gu;
                    	}
                    }
                });
                JSONObject data = new JSONObject();
                data.put("data", sessions);
                message.reply(respWriter.toCmdSuccess(serial_number, cmd, data));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number, cmd));
            }
        });
        
        vertx.eventBus().consumer("data.chatSession.getOne", message -> {
            log.info("data.chatSession.getOne: " + message.body());
            String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				cmd = getCmd(request);
                Integer uid = request.getInteger("uid");
                Integer to = request.getInteger("to");
                String sqlUnion = "";
                Object[] params;
                if(to < Constants.GROUP_ID){
                	sqlUnion = " SELECT a.sid id,a.uid,a.screenstat,c.headimage headimage, null as watchstat, CASE IFNULL(b.ALIAS_0,'') WHEN '' THEN c.nickname ELSE b.ALIAS_0 END name, NULL as create_uid,a.top_status,a.top_time, null as alias FROM (SELECT sid,uid,screenstat,top_status,top_time FROM chat_session_user where sid in(SELECT sid FROM chat_session_user where uid = ? and sid < " + Constants.GROUP_ID + ") and uid = ? and status = 1) a  LEFT JOIN (SELECT UID_1,ALIAS_0 FROM app_friendship where UID_0 = ?) b ON b.UID_1 = a.uid LEFT JOIN app_userinfo c on a.uid = c.uid ";
                	params = new Object[]{uid,to,uid};
                }else{
                	sqlUnion = " SELECT a.sid id, a.uid,b.screenstat,c.head headimage, c.`name`, c.create_uid,a.top_status,a.top_time,b.alias,b.watchstat FROM chat_session_user a  LEFT JOIN app_group_user b on a.sid = b.gid and a.uid = b.uid  LEFT JOIN app_group c on a.sid = c.id   where a.uid = ? and a.sid = ? and a.`status` = 1 and b.`status` = 1 and c.`status` = 1 ";
                	params = new Object[]{uid,to};
                }
                List<GroupUser> sessions = jdbcTemplate.query(sqlUnion, params, new RowMapper<GroupUser>() {
                    @Override
                    public GroupUser mapRow(ResultSet rs, int i) throws SQLException {
                		GroupUser gu = new GroupUser();
                        gu.setId(rs.getInt("id"));
                        gu.setUid(rs.getInt("uid"));
                        gu.setName(rs.getString("name"));
                        String headImg = "";
                        if(rs.getString("headimage") != null){
                			headImg = "" + rs.getString("headimage");
                			if(!headImg.contains("http://")){
                				headImg = config.get("uploadFile.host") + headImg;
                			}
                		}
                        gu.setHead(headImg);
                        gu.setCreate_uid(rs.getInt("create_uid"));
                        gu.setAlias(rs.getString("alias"));
                        gu.setScreenstat(rs.getInt("screenstat"));
                        gu.setTopStatus(rs.getInt("top_status"));
                        gu.setWatchstat(rs.getInt("watchstat"));
                        return gu;
                    }
                });
                JSONObject data = new JSONObject();
                GroupUser u = null;
                if(sessions != null && sessions.size() > 0){
                	u =  sessions.get(0);
                }
                if(u != null){
                	data.put("screenstat", u.getScreenstat());
                	data.put("topStatus", u.getTopStatus());
                	data.put("watchstat", u.getWatchstat());
                	data.put("id", u.getId());
                }else{
                	try{
                		lock.lock();
	                	if(!chatSessionService.chatSessionExist(uid, to)){
	                		int id = chatSessionService.addChatSession(uid, to);
	                    	data.put("screenstat", 0);
	                    	data.put("topStatus", 0);
	                    	data.put("watchstat", 0);
	                    	data.put("id", id);
	                	}
                	}catch(Exception ex){
                		ex.printStackTrace();
                        log.error(ex.getMessage(), ex);
                        message.reply(respWriter.toError(serial_number, cmd));
                	}finally{
                		lock.unlock();
                	}
                }
                message.reply(respWriter.toSuccess(serial_number, data));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number, cmd));
            }
        });
        
        vertx.eventBus().consumer("data.chatSession.del", message -> {
            log.info("data.chatSession.del: " + message.body());
            String serial_number = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
                Integer uid = request.getInteger("uid");
                Integer id = request.getInteger("id");
                String sql = "update chat_session_user set status = 0 where sid = ? and uid = ?";
                jdbcTemplate.update(sql, new Object[]{id, uid});
                message.reply(respWriter.toSuccess(serial_number));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number));
            }
        });
        
        vertx.eventBus().consumer("data.chatSession.screen", message -> {
            log.info("data.chatSession.screen: " + message.body());
            String serial_number = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
				Integer id = request.getInteger("id");
				Integer uid = request.getInteger("uid");
                Integer status = request.getInteger("status");
                String sql = "update chat_session_user set screenstat = ? where sid = ? and uid = ?";
                jdbcTemplate.update(sql, new Object[]{status, id, uid});
                message.reply(respWriter.toSuccess(serial_number));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number));
            }
        });
        
        vertx.eventBus().consumer("data.chatSession.top", message -> {
            log.info("data.chatSession.top: " + message.body());
            String serial_number = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				serial_number = getSerialNumber(request);
                Integer uid = request.getInteger("uid");
                Integer id = request.getInteger("id");
                Integer status = request.getInteger("status");
                String sql = "update chat_session_user set top_status = ?, top_time = now() where sid = ? and uid = ?";
                jdbcTemplate.update(sql, new Object[]{status, id, uid});
                message.reply(respWriter.toSuccess(serial_number));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toError(serial_number));
            }
        });
        
        vertx.eventBus().consumer("data.contact.post", message->{
        	log.info("data.contact.post: " + message.body());
        	String serial_number = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				String sn = getSerialNumber(request);
				serial_number = sn;
				String cmdd = getCmd(request);
				cmd = cmdd;
	        	String imei = request.getString("imei");
	        	String imeif = request.getString("imei_f");
	        	List<Integer> userList = friendshipService.addFriend(imei, imeif);
	        	if(userList.get(0) != null && userList.get(1) != null){
	        		JSONObject json = new JSONObject();
		        	json.put("uid0", userList.get(0));
		        	json.put("uid1", userList.get(1));
		        	vertx.eventBus().send(ServerConstants.SERVER_AGREEWITH2FRIEND_BLUETOOTH_OPT, json.toString(), resp->{
		        		if(resp.succeeded()){
		        			JSONObject js = (JSONObject)JSONObject.parse(resp.result().body().toString());
		        			String code = js.get("code")+"";
		        			if(!code.equals("200")){
		        				log.info("蓝牙添加好友返回结果："+resp.result().body());
		        				message.reply(respWriter.toCmdError(sn, cmdd,RespCode.CODE_4001));
		        			}else{
		        				message.reply(respWriter.toCmdSuccess(sn, cmdd));
		        			}
		        		}else{
		        			log.error(resp.cause().getMessage(), resp.cause());
		        			message.reply(respWriter.toCmdError(sn,cmdd));
		        		}
		        	});
	        	}else{
	        		message.reply(respWriter.toCmdError(sn,cmdd,"好友添加失败，请APP登录后再添加好友。"));
	        	}
			} catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number,cmd));
            }
        });
        
        
        vertx.eventBus().consumer(CmdConstants.S1, message -> {
        	log.info(CmdConstants.S1 + ": " + message.body());
        	String sn = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				sn = getSerialNumber(request);
				cmd = getCmd(request);
	        	Integer id = request.getInteger("id");
	        	ClockInfo ci = userService.getClockInfoById(id);
	        	ZJSONObject result = new ZJSONObject();
	        	result.put("id", ci.getId());
	        	result.put("voiceBody", ci.getVoiceBody());
	        	message.reply(respWriter.toCmdSuccess(sn, cmd, result));
			} catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(sn,cmd));
            }
        });
        
        vertx.eventBus().consumer("data.device.del", message -> {
        	log.info("data.device.del: " + message.body());
        	String sn = null;
			String cmd = null;
			try {
				ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
				Integer uid = request.getInteger("uid");
	        	String did = request.getString("imei");
	        	userDeviceService.delUserDevice(uid, did);
	        	message.reply(respWriter.toSuccess(sn, cmd));
			} catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(sn,cmd));
            }
        });
    }

}
