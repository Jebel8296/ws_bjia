package com.chinamcom.framework.device.verticle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.constants.Constants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.rest.RestTemplateFactory;
import com.chinamcom.framework.common.utils.PropertyConfigLoader;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.device.service.ChatSessionService;
import com.chinamcom.framework.device.service.FriendshipService;
import com.chinamcom.framework.device.service.UserGroupService;
import com.chinamcom.framework.device.util.IOSUtil;
import com.chinamcom.framework.device.util.RestUtil;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;


/**
 * Created by Administrator on 2016/8/15.
 */
@Service
public class PushService extends BaseVerticle {
    
    @Autowired
    private PropertyConfigLoader config;
    
    @Autowired
    private UserGroupService userGroupService;
    
    @Autowired
    private ChatSessionService chatSessionService;
    
    @Autowired
    private FriendshipService friendshipService;
    
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    private Map<Integer,ApnsService> apnsServiceMap;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void start() throws Exception {
    	apnsServiceMap = new HashMap<Integer, ApnsService>();
    	apnsServiceMap.put(1, APNS.newService()
				.withCert(config.get("apple.cert.path1"),config.get("apple.cert.pass")).withSandboxDestination().build());
    	apnsServiceMap.put(2, APNS.newService()
				.withCert(config.get("apple.cert.path2"),config.get("apple.cert.pass")).withProductionDestination().build());
    	apnsServiceMap.put(3, APNS.newService()
				.withCert(config.get("apple.cert.path3"),config.get("apple.cert.pass")).withSandboxDestination().build());
    	apnsServiceMap.put(4, APNS.newService()
				.withCert(config.get("apple.cert.path4"),config.get("apple.cert.pass")).withProductionDestination().build());
    	
    	vertx.eventBus().consumer("notify.push.message", message -> {
            log.info("notify.push.message: " + message.body());
            try {
            	ZJSONObject params = Json.decode(message.body().toString(),ZJSONObject.class);
                String type = params.getString("type");
                String gname = params.getString("gname");
                Integer uid = params.getInteger("uid");
                Integer id = params.getInteger("id");
                String head = params.getString("head");
                
                RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

                JSONObject jsonBody = new JSONObject();
                jsonBody.put("type", type);
                jsonBody.put("from_gid", id);
                jsonBody.put("from_gname", gname);
                jsonBody.put("from_uid", uid);
                jsonBody.put("head", head);
                log.info("push body:" + jsonBody.toString());
                vertx.eventBus().send("user.device.query", params.toString(), res -> {
                    if (res.succeeded()) {
                        JSONObject resp = JSONObject.parseObject(res.result().body().toString());
                        JSONArray deviceInfos = resp.getJSONArray("data");
                        for (Object _deviceInfo : deviceInfos) {
                            JSONObject di = (JSONObject) _deviceInfo;
                            String device_type = di.getString("device_type");
                            Integer uidd = di.getInteger("uid");
                            String did = di.getString("did");
                            int screen = chatSessionService.getChatSessionScreenStatus(uid, id);
                            JSONObject p = new JSONObject();
                            jsonBody.put("uid", uidd);
                            p.put("cmd", "L4");
                            if ("ios".equalsIgnoreCase(device_type)) {
                            	log.info("push screen: " + screen + device_type + " uid: "+ uidd + " did: " + did);
                            	if(screen == 0){
	                            	p.put("body", jsonBody);
	                            	String token = di.getString("apple_id");
	                            	log.debug("ios appid:" + token + "cert: " + config.get("apple.cert.path" + di.getInteger("env")));
	                            	Integer env = di.getInteger("env");
	                            	taskExecutor.execute(new IOSPushConsumer(token, p.toString(), env));
                            	}
                            } else if("watch".equals(device_type)){
                            	String msgType = params.getString("msgType");
                            	if(StringUtil.isNotEmpty(msgType)){
                            		continue;
                            	}
                            	if(id >= Constants.GROUP_ID){
	                            	int status = userGroupService.showGroupOnWatchByUidAndGid(uidd, id);
	                            	log.info("push status:" + status + device_type + " uid: " + uidd + " gid: " + id);
	                            	if(status > 0){
	                            		p.put("to", did);
	                                    HttpEntity<String> reqEntity = new HttpEntity(p.toString(), headers);
	                                    taskExecutor.execute(new PushConsumer(restTemplate, di.getString("server"), reqEntity));
	                            	}
                            	}else{
                            		int status = friendshipService.isShowWatch(uidd, uid);
	                            	log.info("push status:" + status + device_type + " uid: " + uidd + " gid: " + id);
	                            	if(status > 0){
	                            		p.put("to", did);
	                                    HttpEntity<String> reqEntity = new HttpEntity(p.toString(), headers);
	                                    taskExecutor.execute(new PushConsumer(restTemplate, di.getString("server"), reqEntity));
	                            	}
                            	}
                            } else{
                            	log.info("push screen: " + screen + device_type + " uid: "+ uidd + " did: " + did);
                            	if(screen == 0){
	                            	p.put("body", jsonBody);
	                                p.put("to", did);
	                                HttpEntity reqEntity = new HttpEntity(p.toString(), headers);
	                                taskExecutor.execute(new PushConsumer(restTemplate, di.getString("server"), reqEntity));
                            	}
                            }
                        }
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error(ex);
            }
        });
        
    	
    	vertx.eventBus().consumer("watch.data.push.ios", message->{
    		ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
    		String token = request.getString("appleid");
    		Integer env = request.getInteger("env");
    		JSONObject pushData = new JSONObject();
    		pushData.put("cmd", "L20");
    		pushData.put("body", request);
    		IOSUtil.postData(token, env, pushData.toString(), "", "");
    	});
    	
        vertx.eventBus().consumer("device.data.push", message -> {
        	log.debug("device.data.push: " + message.body().toString());
        	try{
	        	ZJSONObject request = Json.decode(message.body().toString(), ZJSONObject.class);
	        	String cmd = request.getString("cmd");
	        	Integer uid = request.getInteger("uid");
	            String did = request.getString("did");
	            JSONObject queryParam = new JSONObject();
	            queryParam.put("uid", uid);
	            queryParam.put("did", did);
	            queryParam.put("to", uid);
	            queryParam.put("type", cmd);
	            queryParam.put("body", request.getJSONObject("body") != null ? request.getJSONObject("body").toString() : null);
	            vertx.eventBus().send("data.msg.post", queryParam.toString(), res -> {
	            	if (res.succeeded()) {
	                     JSONObject msgRep = JSONObject.parseObject(res.result().body().toString());
	                     queryParam.put("device_type", "watch");
	                     vertx.eventBus().send("user.device.query", queryParam.toString(), resq -> {
		                	 if (resq.succeeded()) {
		                         JSONObject deviceRep = JSONObject.parseObject(resq.result().body().toString());
		                         JSONArray deviceInfos = deviceRep.getJSONArray("data");
		                         try{
			                         if(deviceInfos != null && deviceInfos.size() > 0){
			                        	 Integer msgId = msgRep.getJSONObject("respData").getInteger("msg_id");
			                        	 JSONObject di = deviceInfos.getJSONObject(0);
			                        	 JSONObject p = new JSONObject();
			             	             p.put("to", di.getString("did"));
			             	             p.put("cmd", cmd);
			             	             p.put("sn", msgId);
			             	             if(request.getJSONObject("body") != null){
			             	            	 p.put("body", request.getJSONObject("body"));
			             	            	 p.put("ack", 1);
			             	             }
		                            	 RestUtil.postData(di.getString("server"), p);
			                         }
		                         }catch(Exception ex){
		                     		ex.printStackTrace();
		                            log.error(ex);
		                    	}
		                     }
		                });
	                }
	            });
        	}catch(Exception ex){
        		ex.printStackTrace();
                log.error(ex);
        	}
        });
    }
    
    class PushConsumer implements Runnable{
    	private RestTemplate restTemplate;
    	private String server;
    	private HttpEntity<String> httpEntity;
    	
    	public PushConsumer(RestTemplate restTemplate, String server, HttpEntity<String> httpEntity){
    		this.restTemplate = restTemplate;
    		this.server = server;
    		this.httpEntity = httpEntity;
    	}
    	@Override
    	public void run() {
    		restTemplate.postForObject("http://" + server + "/push-m.do", httpEntity, Object.class);
    	}
    	
    }

    class IOSPushConsumer implements Runnable{

    	private String token;
    	private String postInfo;
    	private Integer env;
    	
    	public IOSPushConsumer(String token,String postInfo,Integer env){
    		this.token = token;
    		this.postInfo = postInfo;
    		this.env = env;
    	}
    	@Override
    	public void run() {
    		String payload = APNS.newPayload().badge(1).customField("cmd", postInfo).alertTitle("群聊通知").alertBody("您有一条新消息。").build();
        	ApnsService service = apnsServiceMap.get(env);
        	service.push(token, payload);
    	}
    	
    }
}


