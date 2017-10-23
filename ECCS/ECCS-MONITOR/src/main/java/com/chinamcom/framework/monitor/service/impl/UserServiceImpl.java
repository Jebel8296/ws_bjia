package com.chinamcom.framework.monitor.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinamcom.framework.common.response.RespCode;
import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.SecurityUtil;
import com.chinamcom.framework.monitor.mapper.BackstageUserMapper;
import com.chinamcom.framework.monitor.model.BackstageUser;
import com.chinamcom.framework.monitor.model.BackstageUserExample;
import com.chinamcom.framework.monitor.service.IUserService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;

@Service("UserService")
public class UserServiceImpl extends BaseService implements IUserService {

	@Autowired
	private BackstageUserMapper dao;

	@Override
	public void login(JsonObject param, JWTAuth jwt, Handler<AsyncResult<JsonObject>> resultHandler) {
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
					Future.succeededFuture(respWriter.toSuccess(result, param.getString("sn")));
				} else {
					Future.failedFuture(respWriter.toError(param.getString("sn"), RespCode.CODE_508));
				}
			}
		} catch (Exception e) {
			LOG.error(e);
			Future.failedFuture(respWriter.toError(param.getString("sn")));
		}

	}

	@Override
	public void addBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {
		try {
			BackstageUser record = new BackstageUser();
			record.setUsername(param.getString("username"));
			record.setPassword(SecurityUtil.encryptWithMD5(param.getString("password")));
			record.setPhone(param.getString("username"));
			record.setCreateTime(new Date());
			dao.insertSelective(record);
			String result = respWriter.toSuccess(record, param.getString("sn"));
			resultHandler.handle(Future.succeededFuture(new JsonObject(result)));
		} catch (Exception e) {
			LOG.error(e);
			resultHandler.handle(Future.failedFuture(e.getMessage()));
		}
	}

	@Override
	public void updateBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {

	}

	@Override
	public void deleteBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {

	}

	@Override
	public void queryBackstageUser(JsonObject param, Handler<AsyncResult<JsonObject>> resultHandler) {

	}

}
