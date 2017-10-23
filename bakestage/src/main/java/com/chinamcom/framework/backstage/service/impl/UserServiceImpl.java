package com.chinamcom.framework.backstage.service.impl;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.backstage.mapper.BackstageUserMapper;
import com.chinamcom.framework.backstage.model.BackstageUser;
import com.chinamcom.framework.backstage.model.BackstageUserExample;
import com.chinamcom.framework.backstage.reply.Reply;
import com.chinamcom.framework.backstage.service.AbstractBackstageService;
import com.chinamcom.framework.backstage.service.IUserService;
import com.chinamcom.framework.backstage.util.SecurityUtil;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;

@Service("UserService")
public class UserServiceImpl extends AbstractBackstageService implements IUserService {

	@Autowired
	private BackstageUserMapper dao;

	@Override
	public JsonObject login(JsonObject param, JWTAuth jwt) {
		JsonObject message = new JsonObject();
		try {
			BackstageUserExample example = new BackstageUserExample();
			BackstageUserExample.Criteria criteria = example.createCriteria();
			criteria.andUsernameEqualTo(param.getString("username"));
			List<BackstageUser> users = dao.selectByExample(example);
			if (users != null && users.size() > 0) {
				BackstageUser user = users.stream().findFirst().get();
				if (user.getPassword().equals(SecurityUtil.encryptWithMD5(param.getString("password")))) {
					JsonObject payload = new JsonObject();
					payload.put("uid", user.getId());
					JsonObject result = new JsonObject();
					result.put("token", jwt.generateToken(payload, new JWTOptions().setExpiresInSeconds(86400L)));
					result.put("uid", user.getId());
					message = new Reply(Response.Status.OK.getStatusCode(), "successed", result).toJson();
				} else {
					message = new Reply(Response.Status.BAD_REQUEST.getStatusCode(), "密码不正确.", null).toJson();
				}
			} else {
				message = new Reply(Response.Status.BAD_REQUEST.getStatusCode(), "用户不存在,请先注册.", null).toJson();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", new JsonObject())
					.toJson();
		}
		log.info("response:" + message);
		return message;
	}

	@Override
	public JsonObject addBackstageUser(JsonObject param) {
		JsonObject message = new JsonObject();
		try {
			BackstageUser record = new BackstageUser();
			record.setUsername(param.getString("username"));
			record.setPassword(SecurityUtil.encryptWithMD5(param.getString("password")));
			record.setPhone(param.getString("username"));
			record.setCreateTime(new Date());
			dao.insertSelective(record);
			message = new Reply(Response.Status.OK.getStatusCode(), "successed", record).toJson();
		} catch (Exception e) {
			e.printStackTrace();
			message = new Reply(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "error", new JsonObject())
					.toJson();
		}
		log.info("response:" + message);
		return message;
	}

	@Override
	public JsonObject updateBackstageUser(JsonObject param) {
		return null;
	}

	@Override
	public JsonObject deleteBackstageUser(JsonObject param) {
		return null;

	}

	@Override
	public JsonObject queryBackstageUser(JsonObject param) {
		return null;

	}

}
