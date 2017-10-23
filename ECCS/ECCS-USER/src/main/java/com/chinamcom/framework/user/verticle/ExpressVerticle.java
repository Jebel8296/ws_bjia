package com.chinamcom.framework.user.verticle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constant.ConsumerConstant;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.user.services.IExpressService;

import io.vertx.core.json.JsonObject;

/**
 * 用户服务
 * 
 * @author xuxg
 * @since 20160831
 *
 */
@Component
public class ExpressVerticle extends BaseVerticle {

	@Autowired
	private IExpressService expressServiceImpl;

	@Override
	public void start() throws Exception {
		super.start();
		vertx.eventBus().consumer(ConsumerConstant.EXPRESS_ADD, message -> {
			// TODO add
			LOG.info("add received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				Optional<String> name = Optional.ofNullable(reqData.getString("name"));// 收货人
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));// 手机号
				Optional<String> zipcode = Optional.ofNullable(reqData.getString("zipcode"));// 邮编
				Optional<String> address = Optional.ofNullable(reqData.getString("address"));// 街道地址
				if (!uid.isPresent() || !name.isPresent() || !phone.isPresent() || !zipcode.isPresent()
						|| !address.isPresent()) {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				} else {
					reply = expressServiceImpl.addExpress(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("添加收货地址异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("add reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.EXPRESS_MOD, message -> {
			// TODO mod
			LOG.info("mod received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> eid = Optional.ofNullable(reqData.getInteger("eid"));// ID
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				Optional<String> name = Optional.ofNullable(reqData.getString("name"));// 收货人
				Optional<String> phone = Optional.ofNullable(reqData.getString("phone"));// 手机号
				Optional<String> zipcode = Optional.ofNullable(reqData.getString("zipcode"));// 邮编
				Optional<String> address = Optional.ofNullable(reqData.getString("address"));// 街道地址
				if (!uid.isPresent() || !eid.isPresent() || !name.isPresent() || !phone.isPresent()
						|| !zipcode.isPresent() || !address.isPresent()) {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				} else {
					reply = expressServiceImpl.modExpress(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("修改收货地址异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("mod reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.EXPRESS_DEL, message -> {
			// TODO del
			LOG.info("del received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> eid = Optional.ofNullable(reqData.getInteger("eid"));// 地址ID
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				if (!eid.isPresent() || !uid.isPresent()) {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				} else {
					reply = expressServiceImpl.delExpress(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("删除收货地址异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("del reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.EXPRESS_DEF, message -> {
			// TODO def
			LOG.info("def received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> eid = Optional.ofNullable(reqData.getInteger("eid"));// 地址ID
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				if (!eid.isPresent() || !uid.isPresent()) {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				} else {
					reply = expressServiceImpl.defExpress(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("收货地址默认设置异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("def reply:" + reply);
			message.reply(reply);
		});

		vertx.eventBus().consumer(ConsumerConstant.EXPRESS_LIST, message -> {
			// TODO list
			LOG.info("list received:" + message.body());
			JsonObject reqData = (JsonObject) message.body();
			String sn = reqData.getString("sn");
			String reply = respWriter.toError(sn);
			try {
				Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));// 用户ID
				if (!uid.isPresent()) {
					reply = respWriter.toError(sn, "请求参数不符合规范，请重新提交.");
				} else {
					reply = expressServiceImpl.listExpress(sn, reqData);
				}
			} catch (Exception e) {
				LOG.error("检索收货地址异常：" + e);
				reply = respWriter.toError(sn, "操作失败，请重新提交.");
			}
			LOG.info("list reply:" + reply);
			message.reply(reply);
		});

	}

	public void setExpressServiceImpl(IExpressService expressServiceImpl) {
		this.expressServiceImpl = expressServiceImpl;
	}

}
