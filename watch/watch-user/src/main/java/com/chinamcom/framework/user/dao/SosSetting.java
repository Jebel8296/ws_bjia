package com.chinamcom.framework.user.dao;

import java.util.Date;
import java.util.List;

import com.chinamcom.framework.user.push.SosPhonePushUtil;

public class SosSetting {
	private Integer id;
	private Integer userId;
	private String deviceImei;
	private String phoneNo1;
	private String phoneNo2;
	private String phoneNo3;
	private String smsContent;
	private Date createTime;
	private String  sosMsg;
	private List<SosPhonePushUtil> list;
	
	public String getSosMsg() {
		return sosMsg;
	}
	public void setSosMsg(String sosMsg) {
		this.sosMsg = sosMsg;
	}
	public List<SosPhonePushUtil> getList() {
		return list;
	}
	public void setList(List<SosPhonePushUtil> list) {
		this.list = list;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDeviceImei() {
		return deviceImei;
	}
	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	public String getPhoneNo1() {
		return phoneNo1;
	}
	public void setPhoneNo1(String phoneNo1) {
		this.phoneNo1 = phoneNo1;
	}
	public String getPhoneNo2() {
		return phoneNo2;
	}
	public void setPhoneNo2(String phoneNo2) {
		this.phoneNo2 = phoneNo2;
	}
	public String getPhoneNo3() {
		return phoneNo3;
	}
	public void setPhoneNo3(String phoneNo3) {
		this.phoneNo3 = phoneNo3;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
}
