package com.chinamcom.framework.server.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.server.codec.FrameHelper;
import com.chinamcom.framework.server.codec.FrameParser;



/**
 * Created by Administrator on 2016/6/24.
 */
public class ConnectionVerticle extends AbstractVerticle {

    private static Logger LOG = Logger.getLogger(ConnectionVerticle.class);
    private Map<String, NetSocket> socketPool = new ConcurrentHashMap<>();//did --> socket
    private Map<String, JsonObject> socketInfoPool = new ConcurrentHashMap<>();//did --> socket

    @Override
    public void start() throws Exception {

        LocalMap<String, String> env = vertx.sharedData().getLocalMap("env");
        String host = env.get("server.host");
        Integer tcp_port_mgr = Integer.valueOf(env.get("server.port.tcp.mgr"));
        Integer tcp_port = Integer.valueOf(env.get("server.port.tcp"));
        Integer tcp_timeout = Integer.valueOf(env.get("server.port.tcp.timeout"));

        NetServerOptions option = new NetServerOptions();
        option.setIdleTimeout(tcp_timeout);
        NetServer server = vertx.createNetServer(option);
        server.connectHandler(socket -> {
            String _tmpID = socket.remoteAddress().host();
            socketPool.put(_tmpID, socket);
            JsonObject socketID = new JsonObject();
            socketID.put("did", _tmpID);
            socketID.put("ip",_tmpID);
            LOG.info("socket connection : " + socket.remoteAddress());
            FrameParser parser = new FrameParser(parse -> {
                if (parse.succeeded()) {
                	try{
                		String parseResult = parse.result();
	                    LOG.debug(parseResult);
	                    if(parseResult == null || "".equals(parseResult)){
	                    	LOG.error("Received data is null.");
	                    	return;
	                    }
	                    JsonObject frame = new JsonObject(parseResult);
	                    String command = frame.getString("cmd");
	                    if(StringUtil.isEmpty(command)){
	                    	LOG.error("Command is null.");
	                    	return;
	                    }
	                    switch (command) {
	                        case "bind":
	                        	LOG.info("bind IP: " + _tmpID + " frame: " + frame);
	                            JsonObject body = frame.getJsonObject("body");
	                            String device_type = body.getString("dt");
	                            if(StringUtil.isEmpty(device_type)){
	                            	LOG.error("device_type is null.");
	                            	socket.close();
	                            	break;
	                            }
	                            String did = body.getString("did");
	                            if(StringUtil.isEmpty(did)){
	                            	LOG.error("did is null.");
	                            	socket.close();
	                            	break;
	                            }
	                            Integer uid = body.getInteger("uid");
	                            if("watch".equals(device_type)){
	                            	if(body.getJsonObject("di") != null){
	                            		vertx.eventBus().send("data.di.post", body.getJsonObject("di").toString());
	                            	}
	                            }else{
	                            	if (null != uid && uid != 0) {
		                                socketID.put("uid", uid);//bind user id.
		                            }else{
		                            	LOG.error("uid is null. Can't bind.");
		                            	socket.close();
		                            	break;
		                            }
	                            }
	                            if(socketPool.containsKey(_tmpID)){
	                            	socketPool.remove(_tmpID);
	                            }
	                            socketPool.put(did, socket);
	                            LOG.info("bind did:" + did);
	                            socketID.put("did", did);
	                            socketID.put("status", 1);
	                            socketID.put("device_type", device_type);
	                            socketID.put("server", host + ":" + tcp_port_mgr);
	                            socketInfoPool.put(did, socketID);
	                            vertx.eventBus().send("user.status.update", socketID.toString(), res->{
	                            	 if (res.succeeded()) {
	                            		 String result = res.result().body().toString();
	                            		 LOG.debug("user.status.update: " + result);
	                            		 Integer uuid = null;
	                            		 if(res.result() != null){
	                            			 JSONObject json = JSONObject.parseObject(result);
	                            			 uuid = json.getInteger("uid");
	                            		 }
	                            		 JsonObject resBody = new JsonObject();
	                            		 resBody.put("msg", "success");
	                            		 resBody.put("code", 200);
	                            		 resBody.put("cmd", "bind");
	                            		 resBody.put("ts", new Date().getTime()/1000);
	                            		 resBody.put("uid", uuid);
     	                	        	 FrameHelper.sendFrame(did, "ack", null, null, resBody, 0, socket);
     	                	        	 LOG.info("bind [" + did + "] ack: " + resBody.toString());
     	                	        	 
     	                	        	 // Push command after bind user success.
     	                	        	 if(uuid != 0 && "watch".equals(device_type) && body.getJsonObject("di") != null) {
	     	                	        	 JsonObject pushJson = new JsonObject();
		                            		 pushJson.put("uid", uuid);
		                            		 pushJson.put("imei", did);
		                            		 pushJson.put("did", did);
		                            		 try { 
			                            		// Push L5 contact
			                            		 vertx.eventBus().send("contact.member.sync.byuid", pushJson.toString());
												 
			                            		 // Push L9 SOS
												 vertx.eventBus().send("user.sossetting.push", pushJson.toString());

			                            		 // Push L10 Disturb
			                            		 vertx.eventBus().send("user.classmodel.push", pushJson.toString());
			                            		 
			                            		 // Push L11 Chat
			                            		 vertx.eventBus().send("sociality.groupchat.send.byuid", pushJson.toString());
			                            		 
			                            		 // Pust L13 Wear
			                            		 vertx.eventBus().send("notify.push.wear", pushJson.toString());
			                            		 
			                            		 // Pust L16 Target
			                            		 vertx.eventBus().send("user.target.push", pushJson.toString());
			                            		 
			                            		 // Pust L18 Friend
			                            		 vertx.eventBus().send("user.friendshow.push", pushJson.toString());
			                            		 
		                            		 } catch (Exception e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
     	                	        	 }
	                            	 }else{
	                            		 LOG.error(res.cause().getMessage(), res.cause());
	                            	 }
	                            });
	                            break;
	                        case "hbt":
	                            LOG.info("hbt did:" + socketID.getString("did") + " IP: " + socket.remoteAddress());
	                            if(!socketID.containsKey("device_type")){
	                            	LOG.error("Socket don't send bind. Dont't receive hbt.");
	                            	socket.close();
	                            }else{
	                            	if(!socketPool.containsKey(socketID.getString("did"))){
	                            		socketPool.put(socketID.getString("did"), socket);
	                            		socketID.put("status", 1);
	                            		vertx.eventBus().send("user.status.update", socketID.toString());
	                            	}
	                            }
	                            break;
	                        case "ack"://消息回执，更新消息发送状态 mid
	                        	LOG.info("ack did:" + socketID.getString("did") + " IP: " + socket.remoteAddress() + " frame: " + frame);
	                        	vertx.eventBus().send("message.status.update", new JsonObject().put("message_id", frame.getInteger("sn")).put("status", 1));
	                            break;
	                        case "send":
	                            LOG.info(frame);
	                            JsonObject sendBody = frame.getJsonObject("body");
	                            if(sendBody.containsKey("imei")){ 
	                            	//android push to watch
									String imei = sendBody.getString("imei");
									sendBody.put("did", socketID.getString("did"));
									NetSocket socketWatch = socketPool.get(imei);
									if (null != socketWatch) {
										FrameHelper.sendFrame(imei, "L20", null, null, sendBody, 1, socketWatch);
									} else {      
										JsonObject resBody = new JsonObject();
										resBody.put("msg", "socket closed");
										resBody.put("code", 500);
										resBody.put("cmd", "send");
										FrameHelper.sendFrame(socketID.getString("did"), "ack", null, null, resBody, 0, socket);
										LOG.warn(socketID.getString("did") + " failed to "+ imei + " with:not found");
									}
								} else if(sendBody.containsKey("appleid") && sendBody.getString("appleid") != null){
									//watch push to ios
									vertx.eventBus().send("watch.data.push.ios", sendBody.toString());
								}else{
	                            	//watch push to android
									String didAndroid = sendBody.getString("did");
									NetSocket socketAndroid = socketPool.get(didAndroid);
									if (null != socketAndroid) {
										FrameHelper.sendFrame(didAndroid, "L20", null, null, sendBody, 1, socketAndroid);
									} else {
										LOG.warn(socketID.getString("did") + " failed to "+ didAndroid + " with:not found");
										JsonObject resBody = new JsonObject();
										resBody.put("send", 1);
										FrameHelper.sendFrame(socketID.getString("did"), "send", null, null, resBody, 0, socket);
									}
	                            }
	                            break;
	                        default:
	                            break;
	                    }
                	}catch(Exception ex){
                		LOG.error(ex.getMessage(), ex);
                    }
                } else {
                    parse.cause().printStackTrace();
                    LOG.error(parse.cause().getMessage());
                }
            });

            socket.handler(parser::handle);
            socket.exceptionHandler(h -> {
            	LOG.info("socket exception did:" + socketID.getString("did") + " IP: " + socket.remoteAddress());
                socket.close();
                h.printStackTrace();
                LOG.error(h.getMessage(), h);
            });
            
            socket.closeHandler(v -> {
                LOG.info("socket close did:" + socketID.getString("did") + " IP: " + socket.remoteAddress());
                String did = socketID.getString("did");
                socketPool.remove(did);
                socketInfoPool.remove(did);
                if(socketID.containsKey("device_type") && StringUtil.isNotEmpty(socketID.getString("device_type")) && "watch".equals(socketID.getString("device_type"))){
                	socketID.put("status", 0);
                	vertx.eventBus().send("user.status.update", socketID.toString());
                }
            });

        });
        
        server.listen(tcp_port, res -> {
            if (res.succeeded()) {
                LOG.info("connection server listen :" + server.actualPort());
            } else {
                LOG.info("connection server listen failed");
            }
        });
        
        //ios push to watch
        vertx.eventBus().consumer("data.push.buscmd", message -> {
        	LOG.info("data.push.buscmd" + message.body());
        	JsonObject request = new JsonObject(message.body().toString());
        	String imei = request.getString("imei");
        	JsonObject result = new JsonObject();
        	if(socketPool.containsKey(imei)){
        		NetSocket socket = socketPool.get(imei);
        		request.remove("imei");
        		request.remove("cn");
        		request.remove("sn");
        		FrameHelper.sendFrame(imei, "L20", null, null, request, 1, socket);
    			result.put("code", 200);
    			result.put("msg", "success");
        	}else{
        		result.put("code", 500);
    			result.put("msg", "error");
        	}
        	message.reply(result.toString());
        });
        
        vertx.eventBus().consumer("data.server.remove", message -> {
        	JsonObject request = new JsonObject(message.body().toString());
        	String did = request.getString("imei");
        	LOG.info("data.server.remove did: " + did + " socket is exist. and remove.");
        	if(socketPool.containsKey(did)){
        		NetSocket netSocket = socketPool.get(did);
        		socketPool.remove(did);
        		socketInfoPool.remove(did);
	        	if(netSocket != null){
	        		netSocket.close();
	        	}
        	}
        	vertx.eventBus().send("data.device.del", message.body().toString());
        });
        
        vertx.eventBus().consumer("data.channel.remove", message -> {
        	JsonObject request = new JsonObject(message.body().toString());
        	String did = request.getString("did");
        	String uid = request.getString("uid");
        	LOG.info("data.channel.remove did: " + did + " socket is exist. and remove. And make uid ="+uid +"device offline.");
        	ZJSONObject parama =new ZJSONObject();
			parama.put("uid", uid);
			parama.put("did", did);
			LOG.info("device.status.update parama:"+ parama.toString());
        	vertx.eventBus().send("device.status.update", parama.toString(), res -> {});
        	if(socketPool.containsKey(did)){
        		NetSocket netSocket = socketPool.get(did);
        		socketPool.remove(did);
        		socketInfoPool.remove(did);
	        	if(netSocket != null){
	        		netSocket.close();
	        	}
        	}
        });
        
        
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route("/push-m.do").handler(context -> {
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "application/json");
            JsonObject result = new JsonObject();
            try {
                JsonObject request = context.getBodyAsJson();
                
                String to = request.getString("to");//did
                LOG.debug(to + " : " + request);
                String cmd = request.getString("cmd");//command
                Integer msgId = request.getInteger("sn");//msgId
                Integer ack = request.getInteger("ack");
                JsonObject content = request.getJsonObject("body");//content

                //point to point
                //broadcast
                if ("".equals(to)) {
                    for (NetSocket socket : socketPool.values()) {
                        FrameHelper.sendFrame(to, cmd, msgId, null, content, ack, socket);
                    }
                    result.put("code", 200);
                    result.put("msg", "success");
                    result.put("respData", request);
                    response.end(result.toString());
                } else {
                    NetSocket socket = socketPool.get(to);
                    if (null != socket) {
                        FrameHelper.sendFrame(to, cmd, msgId, null, content, ack, socket);
                        LOG.debug("sendFrame: " + request + " success to " + to);
                    } else {
                        LOG.warn(request + " failed to " + to + " with:not found");
                    }
                    result.put("code", 200);
                    result.put("msg", "success");
                    result.put("respData", request);
                    response.end(result.toString());
                }
            } catch (Exception e) {
                result.put("code", 500);
                result.put("msg", e.getMessage());
                response.end(result.toString());
            }
        });
        
        router.route("/test.do").handler(context -> {
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "application/json");
            JsonObject result = new JsonObject();
            LOG.info(context.getBodyAsString("GB2312"));
        	LOG.info(context.getBodyAsString("UTF-8"));
        	LOG.info(context.getBodyAsString("ISO-8859-1"));
            try {
            	JsonObject request = context.getBodyAsJson();
                String to = request.getString("imei");
                JSONObject queryParam = new JSONObject();
	            queryParam.put("uid", 0);
	            queryParam.put("did", to);
	            queryParam.put("to", to);
	            queryParam.put("type", "L19");
	            queryParam.put("body", request.getJsonObject("body").toString());
//	            vertx.eventBus().send("data.msg.post", queryParam.toString(), res -> {
//	            	if (res.succeeded()) {
//	                    JSONObject msgRep = JSONObject.parseObject(res.result().body().toString());
//	                    Integer msgId = msgRep.getJSONObject("respData").getInteger("msg_id");
	            		Integer msgId = 0; 
	                    JsonObject content = request.getJsonObject("body");
                        NetSocket socket = socketPool.get(to);
                        if (null != socket) {
                            FrameHelper.sendFrame(to, "L19", msgId, null, content, 1, socket);
                        } else {
                            LOG.warn(request + " failed to " + to + " with:not found");
                        }
                        result.put("code", 200);
                        result.put("msg", "success");
                        result.put("result", request.toString());
                        response.end(result.toString());
//	                }
//	            });
            } catch (Exception e) {
                result.put("code", 500);
                result.put("msg", e.getMessage());
                response.end(result.toString());
            }
        });
        
        router.route("/getPool.do").handler(context -> {
            HttpServerResponse response = context.response();
            response.putHeader("content-type", "application/json");
            JsonObject result = new JsonObject();
            try {
                JsonObject json = new JsonObject();
                List<String> list = new ArrayList<String>();
                for(String key: socketPool.keySet()){
                	list.add(key);
                }
                Collections.sort(list);
                json.put("didList",list);
                json.put("didInfo", socketInfoPool);
                response.end(json.toString());
            } catch (Exception e) {
                result.put("code", 500);
                result.put("msg", e.getMessage());
                response.end(result.toString());
            }
        });
        
        vertx.createHttpServer().requestHandler(router::accept).listen(tcp_port_mgr);
    }

}
