package com.chinamcom.framework.sport.model;

import java.util.Date;

public class LocationInfo {
	public Integer id;
	public String imei; //设备IMEI
	public String sportType;//运动类型
	public String locationType;//定位类型
	public Integer accuracy;//精确性
	public String lon;//纬度
	public String lat;//经度
	public String lonFix;//改变纬度
	public String latFix;//改变经度
	public String lbsData;//基站数据
	public Integer battery;//电量
	public Date locationTime;//定位时间
	public Date uploadTime;//上传时间
	public Integer show;//是否显示点,0不显示，1显示
	public String map;//地图
	public String address;//地址
	public Integer msgId;//记录手表回传的消息ID
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLonFix() {
		return lonFix;
	}
	public void setLonFix(String lonFix) {
		this.lonFix = lonFix;
	}
	public String getLatFix() {
		return latFix;
	}
	public void setLatFix(String latFix) {
		this.latFix = latFix;
	}
	public String getLbsData() {
		return lbsData;
	}
	public void setLbsData(String lbsData) {
		this.lbsData = lbsData;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public Date getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(Date locationTime) {
		this.locationTime = locationTime;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Integer getShow() {
		return show;
	}
	public void setShow(Integer show) {
		this.show = show;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
}
