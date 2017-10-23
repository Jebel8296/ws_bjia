package com.chinamcom.framework.user.dao;

import java.util.Date;
import java.util.List;

import com.chinamcom.framework.user.push.RepeatDateUtil;

public class ClassModel {
	private Integer id;
	private String did;
	private String time;
	private String repeat;
	private List<RepeatDateUtil> repeatList;
	private Date createTime;
	private Integer status;
	private Integer uid;
	private Integer power;
	private String imei;
	private Integer displayOrder;
	
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	public List<RepeatDateUtil> getRepeatList() {
		return repeatList;
	}
	public void setRepeatList(List<RepeatDateUtil> repeatList) {
		this.repeatList = repeatList;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
