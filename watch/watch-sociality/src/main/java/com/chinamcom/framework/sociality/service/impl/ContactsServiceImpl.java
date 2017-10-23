package com.chinamcom.framework.sociality.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamcom.framework.common.json.ZJSONObject;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.sociality.dao.ContacksMapper;
import com.chinamcom.framework.sociality.model.Contact;
import com.chinamcom.framework.sociality.model.ContactPhone;
import com.chinamcom.framework.sociality.service.IContactsService;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;

@Service
public class ContactsServiceImpl extends BaseService implements IContactsService {
	@Autowired
	private ContacksMapper contacksMapper;

	@Override
	public String selectContacts(String serialNumber, ZJSONObject reqData) throws Exception {
		// TODO selectContacts
		String reply = null;
		List<Contact> list = new ArrayList<Contact>();
		Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
		Optional<String> name = Optional.ofNullable(reqData.getString("name"));
		if (uid.isPresent()) {
			Contact contact = new Contact();
			contact.setUid(uid.get());
			if (name.isPresent()) {
				contact.setName(name.get());
			}
			log.info("param:" + Json.encode(contact));
			Optional<List<Contact>> cList = Optional.ofNullable(contacksMapper.selectContact(contact));
			if (cList.isPresent()) {
				log.info("search contact_info succeed.");
				cList.get().forEach(item -> {
					Contact c = (Contact) item;
					ContactPhone p = new ContactPhone();
					p.setCid(c.getId());
					Optional<List<ContactPhone>> pList = Optional.ofNullable(contacksMapper.selectContactPhone(p));
					if (pList.isPresent()) {
						log.info("search contact_phone succeed.cid:" + c.getId());
						c.setPhones(pList.get());
					}
					list.add(c);
				});
			}
			reply = respWriter.toSuccess(serialNumber, list);
		} else {
			reply = respWriter.toError(serialNumber, "请求参数有误，请重新提交.");
		}

		return reply != null ? reply : respWriter.toError(serialNumber);
	}

	@Override
	public String addContactInfo(String serialNumber, ZJSONObject reqData) throws Exception {
		// TODO addContactInfo
		String reply = null;
		List<Contact> lContact = new ArrayList<Contact>();
		Contact contact = new Contact();
		List<ContactPhone> list = new ArrayList<ContactPhone>();
		Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
		Optional<String> name = Optional.ofNullable(reqData.getString("name"));
		Optional<String> head = Optional.ofNullable(reqData.getString("head"));
		Optional<String> address = Optional.ofNullable(reqData.getString("address"));
		Optional<String> remark = Optional.ofNullable(reqData.getString("remark"));
		Optional<Integer> watchstat = Optional.ofNullable(reqData.getInteger("watchstat"));
		Optional<JSONArray> phones = Optional.ofNullable(reqData.getJSONArray("phones"));
		if (uid.isPresent() && name.isPresent()) {
			contact.setUid(uid.get());
			contact.setName(name.get());
			contact.setWatchstat(watchstat.get());
			if(head.isPresent()){
				contact.setHead(head.get());
			}
			if (address.isPresent()) {
				contact.setAddress(address.get());
			}
			if (remark.isPresent()) {
				contact.setRemark(remark.get());
			}
			contacksMapper.insertContactInfo(contact);
			log.info("add contact_info succeed! id:" + contact.getId());
			if (phones.isPresent()) {
				phones.get().forEach(item -> {
					JSONObject phone = (JSONObject) item;
					ContactPhone p = new ContactPhone();
					p.setCid(contact.getId());
					p.setPhone(phone.getString("phone"));
					p.setLabel(phone.containsKey("label") ? phone.getString("label") : "手机");
					p.setPriority(0);
					list.add(p);
				});
				contacksMapper.insertContactPhone(list);
				log.info("add contact_phone succeed.");
				contact.setPhones(list);
				lContact.add(contact);
				reply = respWriter.toSuccess(serialNumber, lContact);
			}
		} else {
			reply = respWriter.toError(serialNumber, "请求参数有误，请重新提交.");
		}
		return reply != null ? reply : respWriter.toError(serialNumber);

	}

	@Override
	public String updateContactInfo(String serialNumber, ZJSONObject reqData) throws Exception {
		// TODO addContactInfo
		String reply = null;
		Contact contact = new Contact();
		List<ContactPhone> list = new ArrayList<ContactPhone>();
		Optional<Integer> cid = Optional.ofNullable(reqData.getInteger("cid"));
		Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
		Optional<String> name = Optional.ofNullable(reqData.getString("name"));
		Optional<String> head = Optional.ofNullable(reqData.getString("head"));
		Optional<String> address = Optional.ofNullable(reqData.getString("address"));
		Optional<String> remark = Optional.ofNullable(reqData.getString("remark"));
		Optional<JSONArray> phones = Optional.ofNullable(reqData.getJSONArray("phones"));
		Optional<Integer> watchstat = Optional.ofNullable(reqData.getInteger("watchstat"));
		if (cid.isPresent() && uid.isPresent()) {
			contact.setId(cid.get());
			contact.setName(name.get());
			if(head.isPresent()){
				contact.setHead(head.get());
			}
			if (address.isPresent()) {
				contact.setAddress(address.get());
			}
			if (remark.isPresent()) {
				contact.setRemark(remark.get());
			}
			if(watchstat.isPresent()){
				contact.setWatchstat(watchstat.get());
			}
			contacksMapper.updateContactInfo(contact);
			log.info("update contact_info succeed!");
			if (phones.isPresent()) {
				phones.get().forEach(item -> {
					JSONObject phone = (JSONObject) item;
					ContactPhone p = new ContactPhone();
					p.setCid(cid.get());
					p.setPhone(phone.getString("phone"));
					p.setLabel(phone.containsKey("label") ? phone.getString("label") : "手机");
					list.add(p);
				});
				ContactPhone phone = new ContactPhone();
				phone.setCid(cid.get());
				contacksMapper.deleteContactPhone(phone);
				log.info("delete contact_phone succeed.");
				contacksMapper.insertContactPhone(list);
				log.info("add contact_phone succeed.");
				reply = respWriter.toSuccess(serialNumber);
			}
		} else {
			reply = respWriter.toError(serialNumber, "请求参数有误，请重新提交.");
		}
		return reply != null ? reply : respWriter.toError(serialNumber);

	}
	@Override
	public String updateWatchstatByContact(String serialNumber, ZJSONObject reqData) throws Exception {
		// TODO updateWatchstatByContact
		String reply = respWriter.toError(serialNumber);;
		Contact contact = new Contact();
		Optional<Integer> cid = Optional.ofNullable(reqData.getInteger("cid"));
		Optional<Integer> watchstat = Optional.ofNullable(reqData.getInteger("watchstat"));
		if (cid.isPresent() && watchstat.isPresent()) {
			contact.setId(cid.get());
			contact.setWatchstat(watchstat.get());
			contacksMapper.updateWatchstatByContact(contact);
			log.info("update contact_info succeed!");
			reply = respWriter.toSuccess(serialNumber);
		} else {
			reply = respWriter.toError(serialNumber, "请求参数有误，请重新提交.");
		}
		return reply != null ? reply : respWriter.toError(serialNumber);

	}

	@Override
	public String deleteContactInfo(String serialNumber, ZJSONObject reqData) throws Exception {
		// TODO deleteContactInfo
		String reply = null;
		Optional<Integer> uid = Optional.ofNullable(reqData.getInteger("uid"));
		Optional<Integer> cid = Optional.ofNullable(reqData.getInteger("cid"));
		if (uid.isPresent() && cid.isPresent()) {
			ContactPhone phone = new ContactPhone();
			phone.setId(cid.get());
			contacksMapper.deleteContactPhone(phone);
			log.info("delete contact_info succeed.");
			Contact contact = new Contact();
			contact.setId(cid.get());
			contact.setUid(uid.get());
			contacksMapper.deleteContactInfo(contact);
			log.info("delete contact_phone succeed.");
			reply = respWriter.toSuccess(serialNumber);
		} else {
			reply = respWriter.toError(serialNumber, "请求参数有误，请重新提交.");
		}
		return reply != null ? reply : respWriter.toError(serialNumber);

	}

	@Override
	public String syncContactInfo(Integer uid,String did) {
		// TODO syncContactInfo
		log.info("syn contact_info begin. uid:" + uid);
		JSONObject syncContact = new JSONObject();
		syncContact.put("cmd", "L5");
		syncContact.put("uid", uid);
		syncContact.put("did", did != null ? did : "");
		JSONObject data = new JSONObject();
		Contact contact = new Contact();
		contact.setUid(uid);
		contact.setWatchstat(1);
		Optional<List<Contact>> cList = Optional.ofNullable(contacksMapper.selectContact(contact));
		if (cList.isPresent()) {
			JSONArray array = new JSONArray();
			cList.get().forEach(item -> {
				Contact c = (Contact) item;
				//syncContact.put("name", item.getName());
				ContactPhone p = new ContactPhone();
				p.setCid(c.getId());
				Optional<List<ContactPhone>> pList = Optional.ofNullable(contacksMapper.selectContactPhone(p));
				if (pList.isPresent()) {
					pList.get().forEach(items -> {
						JSONObject o = new JSONObject();
						ContactPhone cp = (ContactPhone) items;
						o.put("id", c.getId());
						o.put("name", c.getName());
						o.put("icon", 0);
						o.put("phone", cp.getPhone());
						array.add(o);
					});
				}
			});
			data.put("data", array);
			syncContact.put("body", data);
		}else{
			data.put("data", new JsonArray());
			syncContact.put("body", data);
		}
		log.info(syncContact.toString());
		log.info("syn contact_info end.");
		return syncContact.toString();
	}

}
