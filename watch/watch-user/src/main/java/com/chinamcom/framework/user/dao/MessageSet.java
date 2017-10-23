package com.chinamcom.framework.user.dao;

import java.util.Date;

public class MessageSet {
	
	private Integer id;
	private Integer voiceStatus;
	private Integer shockStatus;
	private Integer aaronli;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	private Date updateTime;
	private String did;
	
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVoiceStatus() {
		return voiceStatus;
	}
	public void setVoiceStatus(Integer voiceStatus) {
		this.voiceStatus = voiceStatus;
	}
	public Integer getShockStatus() {
		return shockStatus;
	}
	public void setShockStatus(Integer shockStatus) {
		this.shockStatus = shockStatus;
	}
	public Integer getAaronli() {
		return aaronli;
	}
	public void setAaronli(Integer aaronli) {
		this.aaronli = aaronli;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
