package com.chinamcom.framework.wallet.model;

import java.util.Date;

public class BusRecharge {

	public Integer id;
	public String platform; //平台（android、ios、其它）
	public String cardno; //卡号（公交卡卡号）
	public Integer bigDecimal; //金额（分）
	public Date rechargeTime; //时间
	public Integer rechargeResult; //充值结果（0：失败、1：成功）
	public String orderno; //订单号
	public String rechargeChannel; //支付方式（unionpay:银联,alipay:支付宝,wechat:微信）
	public Date createTime; //创建时间
	public String imei;
	public Integer uid;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
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
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public Integer getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(Integer bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
	public Integer getRechargeResult() {
		return rechargeResult;
	}
	public void setRechargeResult(Integer rechargeResult) {
		this.rechargeResult = rechargeResult;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getRechargeChannel() {
		return rechargeChannel;
	}
	public void setRechargeChannel(String rechargeChannel) {
		this.rechargeChannel = rechargeChannel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
