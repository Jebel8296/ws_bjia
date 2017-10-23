package com.chinamcom.framework.payment.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_order_payment
@mbggenerated do_not_delete_during_merge 2016-12-01 14:03:40
 */
public class TbOrderPayment {
    /**
     * Column: tb_order_payment.ID
    @mbggenerated 2016-12-01 14:03:40
     */
    private Integer id;

    /**
     *   ����ID
     * Column: tb_order_payment.ORDER_ID
    @mbggenerated 2016-12-01 14:03:40
     */
    private Integer orderId;

    /**
     *   �������
     * Column: tb_order_payment.ORDER_CODE
    @mbggenerated 2016-12-01 14:03:40
     */
    private String orderCode;

    /**
     *   ֧������
     * Column: tb_order_payment.FEE
    @mbggenerated 2016-12-01 14:03:40
     */
    private BigDecimal fee;

    /**
     *   ֧������1����2����
     * Column: tb_order_payment.PAY_TYPE
    @mbggenerated 2016-12-01 14:03:40
     */
    private Integer payType;

    /**
     *   ֧����ʽ
     * Column: tb_order_payment.PAY_CHANNEL
    @mbggenerated 2016-12-01 14:03:40
     */
    private String payChannel;

    /**
     *   ״̬1��֧��2��֧��3֧��ʧ��4ȡ��
     * Column: tb_order_payment.STATUS
    @mbggenerated 2016-12-01 14:03:40
     */
    private Integer status;

    /**
     *   ����ʱ��
     * Column: tb_order_payment.CREATE_TIME
    @mbggenerated 2016-12-01 14:03:40
     */
    private Date createTime;

    /**
     *   ����ʱ��
     * Column: tb_order_payment.UPDATE_TIME
    @mbggenerated 2016-12-01 14:03:40
     */
    private Date updateTime;

    /**
     *   ������
     * Column: tb_order_payment.PAY_REQ
    @mbggenerated 2016-12-01 14:03:40
     */
    private String payReq;

    /**
     *   ����ʱ��
     * Column: tb_order_payment.PAY_REQ_TIME
    @mbggenerated 2016-12-01 14:03:40
     */
    private Date payReqTime;

    /**
     *   ���ر���
     * Column: tb_order_payment.PAY_RSP
    @mbggenerated 2016-12-01 14:03:40
     */
    private String payRsp;

    /**
     *   ����ʱ��
     * Column: tb_order_payment.PAY_RSP_TIME
    @mbggenerated 2016-12-01 14:03:40
     */
    private Date payRspTime;

    /**
     *   ������
     * Column: tb_order_payment.PAY_RSP_CODE
    @mbggenerated 2016-12-01 14:03:40
     */
    private String payRspCode;

    /**
     *   ������Ϣ
     * Column: tb_order_payment.PAY_RSP_MSG
    @mbggenerated 2016-12-01 14:03:40
     */
    private String payRspMsg;

    /**
     *   ��չ
     * Column: tb_order_payment.EXT01
    @mbggenerated 2016-12-01 14:03:40
     */
    private String ext01;

    /**
     * Column: tb_order_payment.EXT02
    @mbggenerated 2016-12-01 14:03:40
     */
    private String ext02;

    /**
     * Column: tb_order_payment.EXT03
    @mbggenerated 2016-12-01 14:03:40
     */
    private String ext03;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPayReq() {
        return payReq;
    }

    public void setPayReq(String payReq) {
        this.payReq = payReq == null ? null : payReq.trim();
    }

    public Date getPayReqTime() {
        return payReqTime;
    }

    public void setPayReqTime(Date payReqTime) {
        this.payReqTime = payReqTime;
    }

    public String getPayRsp() {
        return payRsp;
    }

    public void setPayRsp(String payRsp) {
        this.payRsp = payRsp == null ? null : payRsp.trim();
    }

    public Date getPayRspTime() {
        return payRspTime;
    }

    public void setPayRspTime(Date payRspTime) {
        this.payRspTime = payRspTime;
    }

    public String getPayRspCode() {
        return payRspCode;
    }

    public void setPayRspCode(String payRspCode) {
        this.payRspCode = payRspCode == null ? null : payRspCode.trim();
    }

    public String getPayRspMsg() {
        return payRspMsg;
    }

    public void setPayRspMsg(String payRspMsg) {
        this.payRspMsg = payRspMsg == null ? null : payRspMsg.trim();
    }

    public String getExt01() {
        return ext01;
    }

    public void setExt01(String ext01) {
        this.ext01 = ext01 == null ? null : ext01.trim();
    }

    public String getExt02() {
        return ext02;
    }

    public void setExt02(String ext02) {
        this.ext02 = ext02 == null ? null : ext02.trim();
    }

    public String getExt03() {
        return ext03;
    }

    public void setExt03(String ext03) {
        this.ext03 = ext03 == null ? null : ext03.trim();
    }
}