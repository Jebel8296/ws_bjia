package com.chinamcom.framework.sociality.verticle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chinamcom.framework.common.constants.ServerConstants;
import com.chinamcom.framework.common.json.Json;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.verticle.BaseVerticle;
import com.chinamcom.framework.sociality.service.impl.ContactsServiceImpl;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/08/03
 */
@Component
public class ContactsVerticle extends BaseVerticle {

	@Autowired
	private ContactsServiceImpl contactsServiceImpl;

	@Override
	public void start() throws Exception {

		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_ADD, message -> {
			log.info(">>>>contact member add received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			String reply = null;
			try {
				reply = contactsServiceImpl.addContactInfo(serialNumber, reqData);
				// 同步通讯录
				ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
				if (mess.containsKey("code") && mess.getInteger("code") == 200) {
					Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
					String did = reqData.containsKey("did") ? reqData.getString("did") : null;
					vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid,did));
				}
			} catch (Exception e) {
				log.error(e);
				reply = respWriter.toError(serialNumber);
			}
			log.info("reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_LIST, message -> {
			log.info(">>>>contact member list received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			String reply = null;
			try {
				reply = contactsServiceImpl.selectContacts(serialNumber, reqData);
			} catch (Exception e) {
				log.error(e);
				reply = respWriter.toError(serialNumber);
			}
			log.info("reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_DEL, message -> {
			log.info(">>>>contact member del received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			String reply = null;
			try {
				reply = contactsServiceImpl.deleteContactInfo(serialNumber, reqData);
				// 同步通讯录
				ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
				if (mess.containsKey("code") && mess.getInteger("code") == 200) {
					Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
					String did = reqData.containsKey("did") ? reqData.getString("did") : null;
					vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid,did));
				}
			} catch (Exception e) {
				log.error(e);
				reply = respWriter.toError(serialNumber);
			}
			log.info("reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_EDIT, message -> {
			log.info(">>>>contact member edit received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			String reply = null;
			try {
				reply = contactsServiceImpl.updateContactInfo(serialNumber, reqData);
				// 同步通讯录
				ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
				if (mess.containsKey("code") && mess.getInteger("code") == 200) {
					Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
					String did = reqData.containsKey("did") ? reqData.getString("did") : null;
					vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid,did));
				}
			} catch (Exception e) {
				log.error(e);
				reply = respWriter.toError(serialNumber);
			}
			log.info("reply:" + reply);
			message.reply(reply);
		});
		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_SETWATCH, message -> {
			log.info(">>>>contact member setwatch received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			String reply = null;
			try {
				reply = contactsServiceImpl.updateWatchstatByContact(serialNumber, reqData);
				// 同步通讯录
				ZJSONObject mess = Json.decode(reply, ZJSONObject.class);
				if (mess.containsKey("code") && mess.getInteger("code") == 200) {
					Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
					String did = reqData.containsKey("did") ? reqData.getString("did") : null;
					vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid,did));
				}
			} catch (Exception e) {
				log.error(e);
				reply = respWriter.toError(serialNumber);
			}
			log.info("reply:" + reply);
			message.reply(reply);
		});
		
		vertx.eventBus().consumer(ServerConstants.MOLDEL_SYNCHRONIZE_COMMUNICATION, message -> {
			log.info(">>>>contact member sycn byuid received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			String serialNumber = getSerialNumber(reqData);
			// 同步通讯录
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
			String did = reqData.containsKey("did") ? reqData.getString("did") : null;
			vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid, did));
			message.reply(respWriter.toSuccess(serialNumber));
		});
		
		vertx.eventBus().consumer(ServerConstants.SERVER_CONTACK_MEMBER_SYNC_BYUID, message -> {
			log.info(">>>>contact member sycn byuid received:" + message.body() + "<<<<");
			ZJSONObject reqData = Json.decode(message.body().toString(), ZJSONObject.class);
			// 同步通讯录
			Integer uid = reqData.containsKey("uid") ? reqData.getInteger("uid") : 0;
			String did = reqData.containsKey("did") ? reqData.getString("did") : null;
			vertx.eventBus().send("device.data.push", contactsServiceImpl.syncContactInfo(uid, did));
		});
	}
}
