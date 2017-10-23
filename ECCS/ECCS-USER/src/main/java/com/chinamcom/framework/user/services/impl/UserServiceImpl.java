package com.chinamcom.framework.user.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinamcom.framework.common.service.BaseService;
import com.chinamcom.framework.common.util.DateUtil;
import com.chinamcom.framework.common.util.SecurityUtil;
import com.chinamcom.framework.user.services.IUserService;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;

@Transactional
public class UserServiceImpl extends BaseService implements IUserService {

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String register(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {

			String phone = param.getString("phone");
			//String password = SecurityUtil.encryptWithMD5(param.getString("password"));
			String password = SecurityUtil.encryptWithSaltMD5(phone,param.getString("password"));
			List<Map<String, Object>> o = jdbcTemplate.queryForList("SELECT id,password FROM tb_user WHERE phone=?",new Object[] { phone });
			if (!o.isEmpty()) {
				return respWriter.toError(sn, "手机号已注册.");
			}
			/*
			Optional<Map<String, Object>> sms = Optional.ofNullable(jdbcTemplate.queryForMap(
					"SELECT phone,auth_code authcode,(now()-invalid_time) as invalid FROM tb_sms_his WHERE SMSSN=?",
					new Object[] { sn }));
			if (!sms.isPresent() || sms.get().containsKey("invalid")) {
				return respWriter.toError(sn, "短信验证码无效，请重新发送.");
			}
			Integer invalid = (Integer) sms.get().get("invalid");
			if (invalid > 300) {
				return respWriter.toError(sn, "短信验证码失效，请重新发送.");
			}
			*/
			jdbcTemplate.update("INSERT INTO tb_user(username,phone,password) VALUES(?,?,?)",new Object[] { "ZM" + phone, phone, password });
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String loginCn(JWTAuth jwt, String sn, String phone, String password) throws Exception {
		String message = respWriter.toError(sn);
		try {
			//password = SecurityUtil.encryptWithMD5(password);
			password = SecurityUtil.encryptWithSaltMD5(phone, password);
			List<Map<String, Object>> o = jdbcTemplate.queryForList("SELECT id,password,phone,username FROM tb_user WHERE phone=?",new Object[] { phone });
			if (!o.isEmpty()) {
				Map<String, Object> user = o.stream().findFirst().get();
				String pass = user.get("password").toString();
				if (password.equals(pass)) {
					String token = jwt.generateToken(new JsonObject(), new JWTOptions().setExpiresInSeconds(86400L));
					message = respWriter.toSuccess(new JsonObject().put("token", token).put("uid", user.get("id")).put("phone", user.get("phone")), sn);
				} else {
					message = respWriter.toError(sn, "密码错误，请重新输入.");
				}
			} else {
				message = respWriter.toError(sn, "用户不存在，请先注册.");
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void loginWx(JsonObject param, JsonObject reply) throws Exception {

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void loginWb(JsonObject param, JsonObject reply) throws Exception {

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void loginQq(JsonObject param, JsonObject reply) throws Exception {

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String modifyPass(String sn,JsonObject param) throws Exception {
		String message = respWriter.toError(sn);
		try {
			String phone = param.getString("phone");
			String password = SecurityUtil.encryptWithSaltMD5(param.getString("phone"), param.getString("password"));
			/**
			Optional<Map<String, Object>> sms = Optional.ofNullable(jdbcTemplate.queryForMap(
					"SELECT phone,auth_code authcode,(now()-invalid_time) as invalid FROM tb_sms_his WHERE SMSSN=?",
					new Object[] { sn }));
			if (!sms.isPresent() || sms.get().containsKey("invalid")) {
				return respWriter.toError(sn, "短信验证码无效，请重新发送.");
			}
			Integer invalid = (Integer) sms.get().get("invalid");
			if (invalid > 300) {
				return respWriter.toError(sn, "短信验证码无效，请重新发送.");
			}
			*/
			List<Map<String, Object>> o = jdbcTemplate.queryForList("SELECT id,password,phone,username FROM tb_user WHERE phone=?",new Object[] { phone });
			if (!o.isEmpty()) {
				StringBuilder sql = new StringBuilder("UPDATE tb_user SET password = ? , updatetime = NOW() WHERE PHONE=?");
				jdbcTemplate.update(sql.toString(), new Object[] { password, phone });
				message = respWriter.toSuccess(sn);
			} else {
				message = respWriter.toError(sn, "用户不存在，请先注册.");
			}
		} catch (Exception e) {
			throw e;
		}
		return message;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String sendSMS(String sn, JsonObject param,Integer ex) throws Exception {
		String message = respWriter.toError(sn);
		try {
			Date ctime = new Date();
			Date itime = DateUtil.addMinutes(ctime, ex);
			StringBuilder sql = new StringBuilder(
					"INSERT INTO tb_sms_his(smssn,phone,content,type,status,auth_code,channel,create_time,invalid_time) VALUES(?,?,?,?,?,?,?,?,?)");
			jdbcTemplate.update(sql.toString(),
					new Object[] { param.getString("smssn"), param.getString("phone"), param.getString("content"),
							param.getString("type"), 0, param.getString("authcode"), param.getString("channel"), ctime,
							itime });
			LOG.info(param);
			message = respWriter.toSuccess(new JsonObject().put("smssn", param.getString("smssn"))
					.put("phone", param.getString("phone")).put("authcode", param.getString("authcode"))
					.put("invalidtime", DateUtil.parseDate2String(itime)), sn);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return message;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String updateSMS(String sn, JsonObject param) {
		String message = respWriter.toError(sn);
		try {
			jdbcTemplate.update("UPDATE tb_sms_his SET status=1 where smssn=?",new Object[] { param.getString("smssn") });
			message = respWriter.toSuccess(sn);
		} catch (Exception e) {
			throw e;
		}
		return message;
	}
	
	

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean verifyCode(String smssn,String code) throws Exception {
		boolean checked = Boolean.FALSE;
		List<Map<String, Object>> sms = jdbcTemplate.queryForList(
				"SELECT phone,auth_code authcode,(now()-invalid_time) as invalid FROM tb_sms_his WHERE SMSSN=?",
				new Object[] { smssn });
		if (!sms.isEmpty()) {
			Long invalid = (Long) sms.get(0).get("invalid");
			if (invalid <= 3600L && sms.get(0).get("authcode").equals(code)) {
				checked = Boolean.TRUE;
			}
		}
		return checked;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void unLogin(JsonObject param, JsonObject reply) throws Exception {

	}

}
