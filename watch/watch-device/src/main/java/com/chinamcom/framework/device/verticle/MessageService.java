package com.chinamcom.framework.device.verticle;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.model.ChatMessageInfo;
import com.chinamcom.framework.device.service.ChatSessionService;
import com.chinamcom.framework.device.service.FriendshipService;
import com.chinamcom.framework.device.service.UserGroupService;


/**
 * Created by Administrator on 2016/7/25.
 */
@Service
public class MessageService extends BaseVerticle {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PropertyConfigLoader config;
    
    @Autowired
    private UserGroupService userGroupService;
    
    @Autowired
    private ChatSessionService chatSessionService;
    
    @Autowired
    private FriendshipService friendShipService;
    
	@Override
    public void start() throws Exception {
    	
        vertx.eventBus().consumer("data.message.post", message -> {
            String serial_number = null;
            String cmd = null;
            try {
                ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
                serial_number = getSerialNumber(request);
                cmd = getCmd(request);
                String cn = request.getString("cn");
                Integer id = request.getInteger("id");
                Integer uid = request.getInteger("uid");
                String did = request.getString("did");
                Integer to = request.getInteger("to");
                String type = request.getString("type");
                log.info("data.message.post: " + " id:" + id +  " uid:" + uid + " did:" + did + " to:" + to + " type:" + type);
                if(StringUtil.isEmpty(to) || to < 10000000){
                	message.reply(respWriter.toCmdError(serial_number, cmd, RespCode.CODE_450));
                	return;
                }
                if (to < Constants.GROUP_ID) {
	                if(!friendShipService.isFriend(uid, to)){
	                	message.reply(respWriter.toCmdError(serial_number, cmd, RespCode.CODE_4003));
	                	return;
	                }
                }
                
                Integer to_back = request.getInteger("to");
                
                if(id == null){
	                if(chatSessionService.chatSessionExist(uid, to) == false){
	                	to = chatSessionService.addChatSession(uid, to);
	                }else{
	                	to = chatSessionService.getChatSessionId(uid, to);
	                }
                }else{
                	to = id;
                }
                if (to >= Constants.GROUP_ID) {
	                if(userGroupService.selectUserStatusOnGroup(uid, to) == 0){
	                	message.reply(respWriter.toCmdError(serial_number, cmd, RespCode.CODE_4006));
	                	return;
	                }
	                if(!request.containsKey("gname")){
	                	request.put("gname", userGroupService.selectGroupName(to));
	                }
                }else{
                	request.put("gname", friendShipService.getUserName(uid, to_back));
                }
                Integer toFinal = to;
                String body = request.getString("body");
                Integer duration = request.getInteger("duration");
                String _sql01 = "insert into app_message_send(source,target,type,body,device_id,duration,channel) values(?,?,?,?,?,?,?)";
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(_sql01, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, uid);
                    ps.setObject(2, toFinal);
                    ps.setObject(3, type);
                    ps.setObject(4, body);
                    ps.setObject(5, did);
                    ps.setObject(6, duration);
                    ps.setObject(7, cn);
                    return ps;
                }, keyHolder);

                Integer message_send_id = keyHolder.getKey().intValue();

                // 将发送给群组信息分别插入到每个组员消息列表
                if (to >= Constants.GROUP_ID) {
                    String _sql02 = "" +
                            "INSERT INTO app_user_message " +
                            "SELECT " +
                            "  NULL," +
                            "  t2.UID AS uid," +
                            "  t1.TYPE AS TYPE," +
                            "  t1.body AS body," +
                            "  t1.source AS from_id," +
                            "  t2.GID AS from_gid," +
                            "  NOW() AS create_time," +
                            "  0 AS STATUS," +
                            "  t1.DEVICE_ID AS device_id," +
                            "  t1.DURATION AS duration, " +
                            "  t1.channel AS channel " +
                            "FROM" +
                            "  app_message_send t1, " +
                            "  app_group_user t2, " +
                            "  app_group t3 " +
                            "WHERE t1.TARGET = t2.GID " +
                            "  AND t2.gid = t3.id " +
                            "  AND t1.id = ? " +
                            "  AND t2.status = 1 " +
                            "  AND t3.status = 1 " +
                            "  AND t2.UID <> ? " +
                            "  AND t2.GID = ?";
                    jdbcTemplate.update(_sql02, message_send_id, "", to);
                } else {
                    String sql = "insert into app_user_message(uid,type,body,from_id,from_gid,create_time,status,device_id,duration,channel) values(?,?,?,?,?,now(),?,?,?,?)";
                    jdbcTemplate.update(sql, to_back, type, body, uid, to, 0, did,duration,cn);
                }
                
                chatSessionService.updateChatSessionStatus(to);
                
                //保存成功之后，发送异步通知（因为基于偏移量、无需保证一定通知成功-不成功仅仅会影响一点及时性）
                JSONObject notify = (JSONObject) request.clone();
                if(notify.get("id") == null){
                	notify.put("id", to);
                }
                notify.remove("body");
                log.info("notify.push.message: 消息推送，"+ notify.toString());
                vertx.eventBus().send("notify.push.message", notify.toString());

                JSONObject response_data = new JSONObject();
                response_data.put("msg_id", message_send_id);
                response_data.put("id", to);
                log.info("data.message.post结果："+response_data.toString());
                message.reply(respWriter.toCmdSuccess(serial_number, cmd, response_data));

            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number, cmd));
            }
        });

        // 根据偏移量获取消息列表（排除本机设备，节约消耗）
        vertx.eventBus().consumer("data.message.get", message -> {
            log.info("data.message.get: " + message.body());
            String serial_number = null;
            String cmd = null;
            try {
                ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
                serial_number = getSerialNumber(request);
                cmd = getCmd(request);
                String cn = getCn(request);
                Integer syncKey = request.getInteger("syncKey");
                Integer limit = request.getInteger("limit");
                Integer uid = request.getInteger("uid");
                String did = request.getString("did");
                Integer newSyncKey = syncKey;
                Map<String,Object> resultMap = new HashMap<String,Object>();
                Map<String,Object> response_data = new HashMap<String,Object>();
                JSONArray arrays = new JSONArray();

                // 第一次、数据清除、卸载重新获取只获取最新消息序列号,不返回历史数据（量大） | 也可以返回最近的N条记录
                List<Map<String, Object>> result = null;
                if(limit == null){
                	limit = 10;
                }
                String selSql = "select a.*, "
                		+ " CASE a.channel "
                		+ " WHEN 'watch'  "
                		     + " THEN  "
                		        + " CASE IFNULL(e.ALIAS_0,'') " 
                		         + " WHEN ''  "
                		             + " THEN   "
                		                 + " CASE IFNULL(b.alias,'') " 
                		                     + " WHEN ''  "
                		                          + " THEN c.nickname "
                		                     + " ELSE b.alias "
                		                 + " END  "
                		         + " ELSE e.ALIAS_0 "
                		        + " END  "
                			+ " ELSE  "
                		     + " CASE IFNULL(e.ALIAS_0,'') " 
                		         + " WHEN ''  "
                		             + " THEN   "
                		                 + " CASE IFNULL(b.alias,'') " 
                		                     + " WHEN ''  "
                		                          + " THEN c.nickname "
                		                     + " ELSE b.alias "
                		                 + " END  "
                		         + " ELSE e.ALIAS_0 "
                		     + " END  "
                		+ " END as alias, " 
                		  + " c.headimage as headimage "
                		+ " FROM "
                		+ " app_user_message a "
        				+ " LEFT JOIN app_group_user b on a.FROM_ID = b.uid and a.FROM_GID = b.gid and b.`status` = 1 "
        				+ " LEFT JOIN app_userinfo c on c.uid = a.FROM_ID "
        				+ " LEFT JOIN app_friendship e on a.UID = e.UID_0 and a.FROM_ID = e.UID_1 and e.STATUS = 1 ";
                if (syncKey == 0) {
                    String sql = "SELECT MAX(id) FROM app_user_message t WHERE t.UID = ? ";
                    Integer _newKey = jdbcTemplate.queryForObject(sql, new Object[]{uid}, Integer.class);
                    if (null != _newKey) {
                        newSyncKey = _newKey;
                    }
                    if("watch".equals(cn)){
                    	selSql += " where a.uid = ? and a.id > ? and a.device_id <> ? order by a.id desc limit ? ";
                    	result = jdbcTemplate.queryForList(selSql, uid, syncKey, did, limit);
                    }else{
                    	selSql += " where (a.uid = ? or a.from_id = ?) and a.id > ? GROUP BY a.from_gid,a.body,a.create_time order by a.id desc limit ? ";
                    	// if uninsatll app, and get msg include myself.
//                    	result = jdbcTemplate.queryForList(selSql, uid,  uid, syncKey, limit);
                    	// if uninsatll app, do not get msg .
                    	result = jdbcTemplate.queryForList(selSql, uid,  uid, syncKey, 0);
                    }
                    Collections.reverse(result);
                } else {
                	selSql += " where a.uid = ? and a.id > ? and (a.device_id <> ? or a.DEVICE_ID is null) order by a.id      limit ? ";
                	result = jdbcTemplate.queryForList(selSql, uid, syncKey, did, limit);
                }
                for (Map<String, Object> row : result) {
                	Integer id = Integer.parseInt(row.get("ID").toString());
                	String strUid = row.get("UID").toString();
            		Integer iuid = Integer.parseInt(strUid);
            		String type = row.get("TYPE").toString();
            		String body = row.get("BODY").toString();
            		Integer from = Integer.parseInt(row.get("FROM_ID").toString());
            		Integer fromg = Integer.parseInt(row.get("FROM_GID").toString());
            		Long create = ((Timestamp)row.get("CREATE_TIME")).getTime();
            		Integer duration = null;
            		if(row.get("DURATION") != null){
            			duration = Integer.parseInt(row.get("DURATION").toString());
            		}
            		String nickName = null;
            		if(row.get("alias") != null){
            			nickName = row.get("alias").toString();
            		}
            		String headImg = null;
            		if(row.get("headimage") != null){
            			headImg = "" + row.get("headimage");
            			if(!headImg.contains("http://")){
            				headImg = config.get("uploadFile.host") + headImg;
            			}
            		}
                	ChatMessageInfo msgInfo = new ChatMessageInfo(id, iuid, type, body, from, fromg, create, duration, nickName, headImg);
                    newSyncKey = id;
                    arrays.add(msgInfo);
                }
                response_data.put("data", arrays);
                response_data.put("syncKey", newSyncKey);
                resultMap.put("code", 200);
                resultMap.put("msg", "success");
                resultMap.put("respData", response_data);
                if(StringUtil.isNotEmpty(cmd)){
                	resultMap.put("cmd",cmd);
                }
                if(StringUtil.isNotEmpty(serial_number)){
                	resultMap.put("sn",serial_number);
                }
                message.reply(Json.encode(resultMap));
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex.getMessage(), ex);
                message.reply(respWriter.toCmdError(serial_number, cmd, ex.getMessage()));
            }
        });

        //预留--更新消息的状态（发送、读取状态）
        vertx.eventBus().consumer("message.status.update", message -> {
        	ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
            log.debug("message.status.update: " + request);
            try {
                Integer message_id = request.getInteger("message_id");
                Integer status = request.getInteger("status");
                jdbcTemplate.update("update app_message_send set status = ? where id = ?", status, message_id);
                request.put("code", 200);
                request.put("msg", "success");
                log.debug(request);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.put("code", 500);
                request.put("msg", ex.getMessage());
                log.error(request);
            }
        });
        
        vertx.eventBus().consumer("data.msg.post", message -> {
          log.info("data.msg.post: " + message.body());
          JSONObject response = new JSONObject();
          try {
              ZJSONObject request = Json.decode(message.body().toString(),ZJSONObject.class);
              Integer uid = request.getInteger("uid");
              String did = request.getString("did");
              String to = request.getString("to");
              String type = request.getString("type");
              String body = null;
              if(request.containsKey("body")){
            	  body = request.getString("body");
              }
              String finalBody = body;
              String _sql01 = "insert into app_message_send(source,target,type,body,device_id) values(?,?,?,?,?)";
              KeyHolder keyHolder = new GeneratedKeyHolder();
              jdbcTemplate.update(connection -> {
                  PreparedStatement ps = connection.prepareStatement(_sql01, Statement.RETURN_GENERATED_KEYS);
                  ps.setObject(1, uid);
                  ps.setObject(2, to);
                  ps.setObject(3, type);
                  ps.setObject(4, finalBody);
                  ps.setObject(5, did);
                  log.debug(ps);
                  return ps;
              }, keyHolder);
              Integer message_send_id = keyHolder.getKey().intValue();
              JSONObject response_data = new JSONObject();
              response_data.put("msg_id", message_send_id);
              response.put("code", 200);
              response.put("msg", "success");
              response.put("respData", response_data);
              message.reply(response.toString());
          } catch (Exception ex) {
              ex.printStackTrace();
              response.put("code", 500);
              response.put("msg", ex.getMessage());
              message.reply(response.toString());
          }
      });
    }
}
