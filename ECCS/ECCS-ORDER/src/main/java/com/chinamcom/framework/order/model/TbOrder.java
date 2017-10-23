package com.chinamcom.framework.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_order
@mbggenerated do_not_delete_during_merge 2017-05-22 14:16:05
 */
public class TbOrder {
    /**
     * Column: tb_order.ID
    @mbggenerated 2017-05-22 14:16:05
     */
    private Integer id;

    /**
     *   ҵ��Ψһ���к�
     * Column: tb_order.SERIAL_NUMBER
    @mbggenerated 2017-05-22 14:16:05
     */
    private String serialNumber;

    /**
     *   ��������40����41�ֱ�99����
     * Column: tb_order.ORDER_TYPE
    @mbggenerated 2017-05-22 14:16:05
     */
    private Integer orderType;

    /**
     *   ����ԭ��
     * Column: tb_order.FEE
    @mbggenerated 2017-05-22 14:16:05
     */
    private BigDecimal fee;

    /**
     *   ʵ�����
     * Column: tb_order.PAY_FEE
    @mbggenerated 2017-05-22 14:16:05
     */
    private BigDecimal payFee;

    /**
     *   ������
     * Column: tb_order.REDUCE_FEE
    @mbggenerated 2017-05-22 14:16:05
     */
    private BigDecimal reduceFee;

    /**
     *   ��ݷ���
     * Column: tb_order.EXPRESS_PAY
    @mbggenerated 2017-05-22 14:16:05
     */
    private BigDecimal expressPay;

    /**
     *   ����
     * Column: tb_order.CHANNEL
    @mbggenerated 2017-05-22 14:16:05
     */
    private String channel;

    /**
     *   ����״̬1��֧��2��֧����������3�ѷ���4�������5��ȡ��6����7���׹ر�
     * Column: tb_order.STATUS
    @mbggenerated 2017-05-22 14:16:05
     */
    private Integer status;

    /**
     *   �û�ID
     * Column: tb_order.USER_ID
    @mbggenerated 2017-05-22 14:16:05
     */
    private Integer userId;

    /**
     *   ����ʱ��
     * Column: tb_order.CREATE_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private Date createTime;

    /**
     *   ֧��ʱ��
     * Column: tb_order.PAY_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private Date payTime;

    /**
     *   ����ʱ��
     * Column: tb_order.SEND_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private Date sendTime;

    /**
     *   �������ʱ��
     * Column: tb_order.COMPATE_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private Date compateTime;

    /**
     *   ����ʱ��
     * Column: tb_order.UPDATE_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private Date updateTime;

    /**
     *   ������Ϣ
     * Column: tb_order.DESCRIPTION
    @mbggenerated 2017-05-22 14:16:05
     */
    private String description;

    /**
     *   �ջ���ַ
     * Column: tb_order.EXPRESS_ID
    @mbggenerated 2017-05-22 14:16:05
     */
    private Integer expressId;

    /**
     *   ������˾
     * Column: tb_order.LOGISTICS_NAME
    @mbggenerated 2017-05-22 14:16:05
     */
    private String logisticsName;

    /**
     *   ��������
     * Column: tb_order.LOGISTICS_CDOE
    @mbggenerated 2017-05-22 14:16:05
     */
    private String logisticsCdoe;

    /**
     *   ��������
     * Column: tb_order.LOGISTICS_NUMBER
    @mbggenerated 2017-05-22 14:16:05
     */
    private String logisticsNumber;

    /**
     *   �ͻ�ʱ��
     * Column: tb_order.DELIVERS_TIME
    @mbggenerated 2017-05-22 14:16:05
     */
    private String deliversTime;

    /**
     *   ��Ʊ̧ͷ
     * Column: tb_order.INVOCE_HEAD
    @mbggenerated 2017-05-22 14:16:05
     */
    private String invoceHead;

    /**
     *   �ۺ��ţ��������ۺ�
     * Column: tb_order.AFTERSALE_CODE
    @mbggenerated 2017-05-22 14:16:05
     */
    private String aftersaleCode;

    /**
     *   ��ע
     * Column: tb_order.REMARK
    @mbggenerated 2017-05-22 14:16:05
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public BigDecimal getReduceFee() {
        return reduceFee;
    }

    public void setReduceFee(BigDecimal reduceFee) {
        this.reduceFee = reduceFee;
    }

    public BigDecimal getExpressPay() {
        return expressPay;
    }

    public void setExpressPay(BigDecimal expressPay) {
        this.expressPay = expressPay;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCompateTime() {
        return compateTime;
    }

    public void setCompateTime(Date compateTime) {
        this.compateTime = compateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName == null ? null : logisticsName.trim();
    }

    public String getLogisticsCdoe() {
        return logisticsCdoe;
    }

    public void setLogisticsCdoe(String logisticsCdoe) {
        this.logisticsCdoe = logisticsCdoe == null ? null : logisticsCdoe.trim();
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber == null ? null : logisticsNumber.trim();
    }

    public String getDeliversTime() {
        return deliversTime;
    }

    public void setDeliversTime(String deliversTime) {
        this.deliversTime = deliversTime == null ? null : deliversTime.trim();
    }

    public String getInvoceHead() {
        return invoceHead;
    }

    public void setInvoceHead(String invoceHead) {
        this.invoceHead = invoceHead == null ? null : invoceHead.trim();
    }

    public String getAftersaleCode() {
        return aftersaleCode;
    }

    public void setAftersaleCode(String aftersaleCode) {
        this.aftersaleCode = aftersaleCode == null ? null : aftersaleCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}