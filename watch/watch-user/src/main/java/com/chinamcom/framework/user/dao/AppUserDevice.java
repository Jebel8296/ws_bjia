package com.chinamcom.framework.user.dao;

public class AppUserDevice {
	private Integer id;
	private Integer uid;
	private String did;
	private Integer status;
	private String server;
	private String deviceType;
	private String appleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAppleId() {
		return appleId;
	}
	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}
}
