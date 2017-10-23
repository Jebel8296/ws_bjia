package com.chinamcom.framework.user.dao;

import java.util.Date;

public class DeviceWearInfo {
	private Integer id;
	private String devicePhone;//设备电话号码
	private String deviceImei;//设备的imei号
	private String deviceName;//设备名称
	private String nickName;//名称
	private Integer uid;//关联的用户id
	private String deviceHead;//设备头像地址
	private Integer deviceSex;//设备性别,0男1女
	private Date deviceBirthday;//生日
	private String deviceAge;//设备年龄,默认6岁
	private Integer deviceHeight;//设备身高,默认170
	private Integer deviceWeight;//设备体重
	private Integer deviceStepd;//步长
	private String phone;//电话
	private Date deviceUpdateTime;//设备上传时间
	private String deviceDisable;//设备是否禁用,0表示禁用,1(默认)表示不禁用
	private String firm;//固件版本号
	private Date first;//首次激活时间
	private Integer deviceStatus;//
	private String shortNumber;//短号
	private String address;//地址
	private String addnotes;//备注
	private String balance;//公交余额
	private String phoneBalance;//手机余额
	
	public Date getDeviceBirthday() {
		return deviceBirthday;
	}
	public void setDeviceBirthday(Date deviceBirthday) {
		this.deviceBirthday = deviceBirthday;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getDeviceHeight() {
		return deviceHeight;
	}
	public void setDeviceHeight(Integer deviceHeight) {
		this.deviceHeight = deviceHeight;
	}
	public Integer getDeviceWeight() {
		return deviceWeight;
	}
	public void setDeviceWeight(Integer deviceWeight) {
		this.deviceWeight = deviceWeight;
	}
	public Integer getDeviceStepd() {
		return deviceStepd;
	}
	public void setDeviceStepd(Integer deviceStepd) {
		this.deviceStepd = deviceStepd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDevicePhone() {
		return devicePhone;
	}
	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}
	public String getDeviceImei() {
		return deviceImei;
	}
	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceHead() {
		return deviceHead;
	}
	public void setDeviceHead(String deviceHead) {
		this.deviceHead = deviceHead;
	}
	public Integer getDeviceSex() {
		return deviceSex;
	}
	public void setDeviceSex(Integer deviceSex) {
		this.deviceSex = deviceSex;
	}
	public String getDeviceAge() {
		return deviceAge;
	}
	public void setDeviceAge(String deviceAge) {
		this.deviceAge = deviceAge;
	}
	public Date getDeviceUpdateTime() {
		return deviceUpdateTime;
	}
	public void setDeviceUpdateTime(Date deviceUpdateTime) {
		this.deviceUpdateTime = deviceUpdateTime;
	}
	public String getDeviceDisable() {
		return deviceDisable;
	}
	public void setDeviceDisable(String deviceDisable) {
		this.deviceDisable = deviceDisable;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public Date getFirst() {
		return first;
	}
	public void setFirst(Date first) {
		this.first = first;
	}
	public Integer getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getShortNumber() {
		return shortNumber;
	}
	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}
	
	public String getAddnotes() {
		return addnotes;
	}
	public void setAddnotes(String addnotes) {
		this.addnotes = addnotes;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getPhoneBalance() {
		return phoneBalance;
	}
	public void setPhoneBalance(String phoneBalance) {
		this.phoneBalance = phoneBalance;
	}
	
}
