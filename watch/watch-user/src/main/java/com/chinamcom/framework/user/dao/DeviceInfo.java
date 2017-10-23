package com.chinamcom.framework.user.dao;

public class DeviceInfo {
	private Integer id ;
	private String imei;
	private String imsi;
	private String productId;
	private String fwVer;
	private String fwBuild;
	private String mcuBuild;
	private String btName;
	private String btMac;
	private String phoneNo;
	
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
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getFwVer() {
		return fwVer;
	}
	public void setFwVer(String fwVer) {
		this.fwVer = fwVer;
	}
	public String getFwBuild() {
		return fwBuild;
	}
	public void setFwBuild(String fwBuild) {
		this.fwBuild = fwBuild;
	}
	public String getMcuBuild() {
		return mcuBuild;
	}
	public void setMcuBuild(String mcuBuild) {
		this.mcuBuild = mcuBuild;
	}
	public String getBtName() {
		return btName;
	}
	public void setBtName(String btName) {
		this.btName = btName;
	}
	public String getBtMac() {
		return btMac;
	}
	public void setBtMac(String btMac) {
		this.btMac = btMac;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
