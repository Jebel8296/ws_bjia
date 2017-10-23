package com.chinamcom.framework.sport.model;

import java.util.Date;

public class HeartRateInfo {
	public Integer id;
	public Integer type;//运动类型
	public String imei;
	public String heartRate;
	public Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
}
