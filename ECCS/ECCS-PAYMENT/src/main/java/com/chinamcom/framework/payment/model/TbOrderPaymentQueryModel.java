package com.chinamcom.framework.payment.model;

import java.math.BigDecimal;
import java.util.Date;

import com.chinamcom.framework.common.model.QueryModel;

public class TbOrderPaymentQueryModel extends QueryModel {

	private TbOrderPayment payment;

	public TbOrderPaymentQueryModel() {
		this(new TbOrderPayment());
	}

	public TbOrderPaymentQueryModel(TbOrderPayment payment) {
		this.payment = payment;
	}

	public Integer getId() {
		return payment.getId();
	}

	public void setId(Integer id) {
		payment.setId(id);
	}

	public Integer getOrderId() {
		return payment.getOrderId();
	}

	public void setOrderId(Integer orderId) {
		payment.setOrderId(orderId);
	}

	public BigDecimal getFee() {
		return payment.getFee();
	}

	public void setFee(BigDecimal fee) {
		payment.setFee(fee);
	}

	public Integer getPayType() {
		return payment.getPayType();
	}

	public void setPayType(Integer payType) {
		payment.setPayType(payType);
	}

	public String getPayChannel() {
		return payment.getPayChannel();
	}

	public void setPayChannel(String payChannel) {
		payment.setPayChannel(payChannel);
	}

	public Integer getStatus() {
		return payment.getStatus();
	}

	public void setStatus(Integer status) {
		payment.setStatus(status);
	}

	public Date getCreateTime() {
		return payment.getCreateTime();
	}

	public void setCreateTime(Date createTime) {
		payment.setCreateTime(createTime);
	}

	public Date getUpdateTime() {
		return payment.getUpdateTime();
	}

	public void setUpdateTime(Date updateTime) {
		payment.setUpdateTime(updateTime);
	}

	public String getPayReq() {
		return payment.getPayReq();
	}

	public void setPayReq(String payReq) {
		payment.setPayReq(payReq);
	}

	public Date getPayReqTime() {
		return payment.getPayReqTime();
	}

	public void setPayReqTime(Date payReqTime) {
		payment.setPayReqTime(payReqTime);
	}

	public String getPayRsp() {
		return payment.getPayRsp();
	}

	public void setPayRsp(String payRsp) {
		payment.setPayRsp(payRsp);
	}

	public Date getPayRspTime() {
		return payment.getPayRspTime();
	}

	public void setPayRspTime(Date payRspTime) {
		payment.setPayRspTime(payRspTime);
	}

	public String getPayRspCode() {
		return payment.getPayRspCode();
	}

	public void setPayRspCode(String payRspCode) {
		payment.setPayRspCode(payRspCode);
	}

	public String getPayRspMsg() {
		return payment.getPayRspMsg();
	}

	public void setPayRspMsg(String payRspMsg) {
		payment.setPayRspMsg(payRspMsg);
	}

	public String getExt01() {
		return payment.getExt01();
	}

	public void setExt01(String ext01) {
		payment.setExt01(ext01);
	}

	public String getExt02() {
		return payment.getExt02();
	}

	public void setExt02(String ext02) {
		payment.setExt02(ext02);
	}

	public String getExt03() {
		return payment.getExt03();
	}

	public void setExt03(String ext03) {
		payment.setExt03(ext03);
	}
}
