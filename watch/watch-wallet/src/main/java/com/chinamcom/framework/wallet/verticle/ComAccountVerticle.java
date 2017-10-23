package com.chinamcom.framework.wallet.verticle;

import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.bss.model.mhall.ResponseObject;
import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.utils.StringUtil;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.wallet.Runner;
import com.chinamcom.framework.wallet.service.ComAccountService;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/12
 */
@Component
public class ComAccountVerticle extends BaseVerticle {
	
	@Autowired
	private ComAccountService comAccountService;
	
	@Override
	public void start() throws Exception{
		Properties config = Runner.config;
		RedisClient redis = RedisClient.create(vertx, new RedisOptions()
		.setHost(config.getProperty("redis.host", "localhost"))
		.setPort(Integer.parseInt(config.getProperty("redis.port")))
		.setTcpKeepAlive(true)
		.setSelect(Integer.valueOf(config.getProperty("redis.db", "3"))));
		
		vertx.eventBus().consumer(
				ServerConstants.SERVER_COM_ACCOUNT_QUERY,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String channel = reqData.getString("cn");
					vertx.eventBus().send("user.appuserinfo.queryappuserinfo",message.body().toString(), resq-> {
						if(resq.succeeded()){
							ZJSONObject resqData = Json.decode(resq.result().body().toString(), ZJSONObject.class);
							if(resqData.containsKey("respData") && resqData.getJSONObject("respData").containsKey("appuserinfo")){
								JSONObject wear = resqData.getJSONObject("respData").getJSONObject("appuserinfo");
								if(wear != null && StringUtil.isNotEmpty(wear.getString("phonenum"))){
									String phoneNo = wear.getString("phonenum");
//									redis.get(phoneNo + "_account", redisReq -> {
//					                	if(redisReq.succeeded()){
//					                		String result = "";
//					                		if(StringUtil.isNotEmpty(redisReq.result())){
//					                			JSONObject jsonRedis = JSONObject.parseObject(redisReq.result());
//					                			jsonRedis.put("phoneNo", phoneNo);
//					                			result = respWriter.toSuccess(serial_number, jsonRedis);
//					                		}else{
//					                			try {
//													ResponseObject ro = comAccountService.getComBalance(phoneNo, channel);
//													if("200".equals(ro.getCode())){
//														ro.getResult().put("phoneNo", phoneNo);
//														redis.setex(phoneNo + "_account", 60 * 30, Json.encode(ro.getResult()),redisSetReq->{});
//														result = respWriter.toSuccess(serial_number, ro.getResult());
//													}else{
//														result = respWriter.toError(serial_number, RespCode.CODE_3000, ro.getMessage());
//													}
//												}catch(Exception e){
//													log.error(e.getMessage(), e);
//													result = respWriter.toError(serial_number, RespCode.CODE_3000, e.getMessage());
//												}
//					                		}
//					                		message.reply(result);
//					                	}else{
//					                		message.reply(respWriter.toError(serial_number, RespCode.CODE_3000, redisReq.cause().getMessage()));
//					                	}
//					                });
									String result = "";
									ResponseObject ro = comAccountService.getComBalance(phoneNo, channel);
									if("200".equals(ro.getCode())){
										ro.getResult().put("phoneNo", phoneNo);
//										redis.setex(phoneNo + "_account", 60 * 30, Json.encode(ro.getResult()),redisSetReq->{});
										result = respWriter.toSuccess(serial_number, ro.getResult());
									}else{
										result = respWriter.toError(serial_number, RespCode.CODE_3000, ro.getMessage());
									}
									message.reply(result);
								}else{
									message.reply(respWriter.toError(serial_number, RespCode.CODE_3000, "请到“我”页面点击头像进入“个人资料”里添加手机号码"));
								}
							}else{
								message.reply(respWriter.toError(serial_number, RespCode.CODE_3000, "请到“我”页面点击头像进入“个人资料”里添加手机号码"));
							}
						}else{
							message.reply(respWriter.toError(serial_number, RespCode.CODE_3000, resq.cause().getMessage()));
						}
					});
				});
		vertx.eventBus().consumer(
				ServerConstants.SERVER_COM_ACCOUNT_RECHARGE,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String channel = reqData.getString("cn");
					String phoneNo = reqData.getString("phoneNo");
					Integer amount = reqData.getInteger("amount");
					String payChannel = reqData.getString("payChannel");
					String result = "";
					try {
						ResponseObject ro = comAccountService.recharge(phoneNo, amount, payChannel, channel);
						if("200".equals(ro.getCode())){
							result = respWriter.toSuccess(serial_number, ro.getResult());
						}else{
							result = respWriter.toError(serial_number, RespCode.CODE_3000, ro.getMessage());
						}
					} catch(Exception e){
						log.error(e.getMessage(), e);
						result = respWriter.toError(serial_number, RespCode.CODE_3000);
					}
					message.reply(result);
				});
		vertx.eventBus().consumer(
				ServerConstants.SERVER_COM_ACCOUNT_RECHARGE_HISTORY,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String phoneNo = reqData.getString("phoneNo");
					String month = reqData.getString("month");
					String channel = reqData.getString("cn");
					String result = "";
					try {
						ResponseObject ro = comAccountService.rechargeList(phoneNo, month, channel);
						if("200".equals(ro.getCode())){
							result = respWriter.toSuccess(serial_number, ro.getResult());
						}else{
							result = respWriter.toError(serial_number, RespCode.CODE_5000, ro.getMessage());
						}
					} catch(Exception e){
						log.error(e.getMessage(), e);
						result = respWriter.toError(serial_number, RespCode.CODE_5000);
					}
					message.reply(result);
				});
		vertx.eventBus().consumer(
				ServerConstants.VERSON_UPGRADE,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String channel = reqData.getString("cn");
					String appOnlyKey = reqData.getString("appOnlyKey");
					Integer fileType = reqData.getInteger("fileType");
					Integer versionCode = reqData.getInteger("versionCode");
					String result = "";
					try {
						ResponseObject ro = comAccountService.getVersionInfo(appOnlyKey, fileType, versionCode, channel);
						if("200".equals(ro.getCode())){
							result = respWriter.toSuccess(serial_number, ro.getResult());
						}else{
							result = respWriter.toError(serial_number, RespCode.CODE_5001, ro.getMessage());
						}
					} catch(Exception e){
						log.error(e.getMessage(), e);
						result = respWriter.toError(serial_number, RespCode.CODE_5001);
					}
					message.reply(result);
				});
		vertx.eventBus().consumer(
				ServerConstants.SERVER_COM_ACCOUNT_RECHARGE_MONTHLIST,
				message -> {
					log.info("message received:" + message.body());
					ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
					String serial_number = getSerialNumber(reqData);
					String result = "";
					try {
						result = respWriter.toSuccess(serial_number, comAccountService.getSelectMonthList());
					} catch(Exception e){
						log.error(e.getMessage(), e);
						result = respWriter.toError(serial_number, RespCode.CODE_500);
					}
					message.reply(result);
				});
	}
}
