package com.chinamcom.framework.user.verticle;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bjcathay.pay.service.util.ClientSignHelper;
import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.util.Base64Utils;
import com.chinamcom.framework.common.util.RedisClientUtils;
import com.chinamcom.framework.common.util.StringUtil;
import com.chinamcom.framework.common.util.VerifyCodeUtils;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.services.IUserService;

import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.RedisClient;

/**
 * 用户服务
 * 
 * @author xuxg
 * @since 20160831
 *
 */
@Component
public class UserVerticle extends BaseVerticle {

	@Autowired
	private IUserService userServiceImpl;

	@Override
	public void start() throws Exception {
		super.start();
		vertx.eventBus().consumer(ConsumerConstant.ZM3C_USER_REGISTER, message -> {
			LOG.info("received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));
				Optional<String> password = Optional.ofNullable(reqData.getString("password"));
				if (phone.isPresent() && password.isPresent()) {
					reply = userServiceImpl.register(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error(e);
				reply = respWriter.toError(sn, "register faild.");
			}
			LOG.info("reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ConsumerConstant.ZM3C_USER_LOGIN_CN, message -> {
			LOG.info("received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));
				Optional<String> password = Optional.ofNullable(reqData.getString("password"));
				if (phone.isPresent() && password.isPresent()) {
					reply = userServiceImpl.loginCn(jwt, sn, phone.get(), password.get());
				}
			} catch (Exception e) {
				LOG.error(e);
				reply = respWriter.toError(sn, "login faild.");
			}
			LOG.info("reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.PASSWORD_MODIFY, message -> {
			LOG.info("modify_password_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));
				Optional<String> password = Optional.ofNullable(reqData.getString("password"));
				if (phone.isPresent() && password.isPresent()) {
					reply = userServiceImpl.modifyPass(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("修改密码异常:" + e);
				reply = respWriter.toError(sn, "修改密码失败，请重新操作.");
			}
			LOG.info("modify_password_reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.SEND_SMS, message -> {
			LOG.info("sendsms received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<String> type = Optional.ofNullable(reqData.getString("type"));
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));
				if (type.isPresent() && phone.isPresent()) {
					String authcode = StringUtil.getSmsAuthCode();// 4位随机码
					String content = StringUtil.getSmsContent(authcode);// 短信内容
					String smssn = StringUtil.getSmsSn(phone.get());
					reqData.put("authcode", authcode);
					reqData.put("content", content);
					reqData.put("channel", reqData.containsKey("channel") ? reqData.getString("channel") : "PC");
					reqData.put("smssn", smssn);
					reply = userServiceImpl.sendSMS(sn, reqData, 3);// 3分钟后失效
					// 调用发送短信接口
					String service = config.getProperty("send_sms", "bop.orders.sendInfoToUser");
					Integer port = Integer.valueOf(config.getProperty("send_port", "8050"));
					String host = config.getProperty("send_host", "localhost");
					String requestURI = config.getProperty("send_uri", "/esb/json");
					JsonObject sms = new JsonObject().put("delay_minute", 0).put("send_channel", "sms")
							.put("phoneno", phone.get()).put("content", content);
					JsonObject o = new JsonObject();
					o.put("channel", "web");
					o.put("platform_type", "CUNT");
					o.put("request_data", sms);
					o.put("ret_type", "json");
					o.put("serial_number", sn);
					o.put("service_name", service);
					HttpClientRequest request = vertx.createHttpClient().post(port, host, requestURI, ct -> {
						int code = ct.statusCode();
						LOG.info("sendsms_reply_code=" + code);
						if (code == 200) {
							ct.bodyHandler(body -> {
								JsonObject result = body.toJsonObject();
								LOG.info("sendsms_reply_result=" + result);
								if (result.getString("ret_code").equals("0")) {
									userServiceImpl.updateSMS(sn, new JsonObject().put("smssn", smssn));
									LOG.info("发送短信验证码成功.");
								} else {
									LOG.info("发送短信验证码失败.");
								}
							});
						} else {
							LOG.info("调用发送短信服务接口失败.");
						}
					});
					request.end(o.toString());
				} else {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				}
			} catch (Exception e) {
				LOG.error("发送短信异常：" + e);
				reply = respWriter.toError(sn, "发送短信失败，请重新发送.");
			}
			LOG.info("sendsms reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.GENERATE_CODE, message -> {
			LOG.info("generate_code_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
				if (uid.isPresent()) {
					int w = 200, h = 80;
					String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					VerifyCodeUtils.outputImage(w, h, output, verifyCode);
					String smssn = StringUtil.getSmsSn("10020");
					// 验证码是否要保存到Redis
					Optional<RedisClient> client = Optional.ofNullable(RedisClientUtils.getRedisClient(vertx));
					if (client.isPresent()) {
						client.get().setex(uid.get().toString() + "_verifycode", 3600L, verifyCode, handler -> {
						});
					} else {
						reqData.put("authcode", verifyCode);
						reqData.put("content", "验证码为" + verifyCode);
						reqData.put("channel", reqData.containsKey("channel") ? reqData.getString("channel") : "PC");
						reqData.put("smssn", smssn);
						reqData.put("phone", "10020");
						reqData.put("type", "verifycode");
						userServiceImpl.sendSMS(sn, reqData, 60);// 60分钟失效
					}
					reply = respWriter.toSuccess(new JsonObject().put("uid", uid.get()).put("code", verifyCode)
							.put("smssn", smssn).put("url", Base64Utils.encode(output.toByteArray())), sn);
				} else {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				}
			} catch (Exception e) {
				LOG.error("生成验证码异常:" + e);
				reply = respWriter.toError(sn, "生成验证码失败，请重新操作.");
			}
			LOG.info("generate_code_reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.VERIFY_CODE, message -> {
			LOG.info("verify_code_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			JsonObject reply = new JsonObject();
			try {
				String smssn = Optional.ofNullable(reqData.getString("smssn")).orElse("10020");
				String code = Optional.ofNullable(reqData.getString("code")).orElse("10020");
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
				Optional<RedisClient> client = Optional.ofNullable(RedisClientUtils.getRedisClient(vertx));
				if (client.isPresent()) {
					client.get().get(uid.get().toString() + "_verifycode", handler -> {
						if (handler.succeeded()) {
							if (code.equals(handler.result())) {
								reply.put("checked", Boolean.TRUE);
							} else {
								reply.put("checked", Boolean.FALSE);
							}
						} else {
							LOG.error("从Redis取验证码校异常,直接返回验证失败:" + handler.cause());
							reply.put("checked", Boolean.FALSE);
						}
						LOG.info("verify_code_reply:" + reply);
						message.reply(reply);
					});
				} else {
					reply.put("checked", userServiceImpl.verifyCode(smssn, code));
					LOG.info("verify_code_reply:" + reply);
					message.reply(reply);
				}
			} catch (Exception e) {
				LOG.error("验证码校验异常,直接返回验证失败:" + e);
				reply.put("checked", Boolean.FALSE);
				message.reply(reply);
			}
		});
		vertx.eventBus().consumer(ConsumerConstant.BUILD_SIGN_CODE, message -> {
			LOG.info("build_sign_received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			JsonObject reply = new JsonObject();
			String result = respWriter.toError(reqData.getString("sn"));
			try {
				// 取签名
				List<String> buildParam = new ArrayList<String>();
				reqData.forEach(param -> {
					String value = (String) param.getValue();
					buildParam.add(value);
				});
				String sign = ClientSignHelper.getSign(config.getProperty("login.domainSecret", "test"), buildParam);
				reply.put("sign", sign);
				result = respWriter.toSuccess(reply, reqData.getString("sn"));
			} catch (Exception e) {
				LOG.error("获取签名异常:" + e);
			}
			LOG.info("build_sign_reply:" + result);
			message.reply(result);
		});
	}

	public void setUserServiceImpl(IUserService userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

}
