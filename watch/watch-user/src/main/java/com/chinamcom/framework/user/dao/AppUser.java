package com.chinamcom.framework.user.dao;

import java.util.Date;

public class AppUser {
	private Integer uid;
	private String name;
	private String alias;
	private String pass;
	private String head;
	private String imei;
	private String weiboId;
	private String weixinId;
	private String qqId;
	private Integer status;
	private Date createtime;
	private Date updatetime;
	private Integer loginModel;
	private AppUserInfo appuserinfo;
	private String token; //登陆时，为客户端生成token
	private String[] targetbaseinfoarray;
	
	public String[] getTargetbaseinfoarray() {
		return targetbaseinfoarray;
	}
	public void setTargetbaseinfoarray(String[] targetbaseinfoarray) {
		this.targetbaseinfoarray = targetbaseinfoarray;
	}
	public Integer getLoginModel() {
		return loginModel;
	}
	public void setLoginModel(Integer loginModel) {
		this.loginModel = loginModel;
	}
	public AppUserInfo getAppuserinfo() {
		return appuserinfo;
	}
	public void setAppuserinfo(AppUserInfo appuserinfo) {
		this.appuserinfo = appuserinfo;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getWeiboId() {
		return weiboId;
	}
	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}
	public String getWeixinId() {
		return weixinId;
	}
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}
	public String getQqId() {
		return qqId;
	}
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
