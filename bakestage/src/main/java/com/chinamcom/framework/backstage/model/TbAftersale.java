package com.chinamcom.framework.backstage.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_aftersale
@mbggenerated do_not_delete_during_merge 2017-06-05 14:54:37
 */
public class TbAftersale {
    /**
     * Column: tb_aftersale.id
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer id;

    /**
     *   �ۺ������:yyyyMMddHHmmssXXXX
     * Column: tb_aftersale.aftercode
    @mbggenerated 2017-06-05 14:54:37
     */
    private String aftercode;

    /**
     *   �ۺ����� 1�˻� 2���� 3ά��
     * Column: tb_aftersale.aftertype
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer aftertype;

    /**
     *   �ۺ�״̬  0������1���ʼ�2������3�ۺ����4�ر�5������
     * Column: tb_aftersale.status
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer status;

    /**
     *   ����ID
     * Column: tb_aftersale.order_id
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer orderId;

    /**
     *   �������
     * Column: tb_aftersale.order_code
    @mbggenerated 2017-06-05 14:54:37
     */
    private String orderCode;

    /**
     *   ��Ʒ���룬�����ͨ���豸����ύ����Ϊ�豸���
     * Column: tb_aftersale.product_code
    @mbggenerated 2017-06-05 14:54:37
     */
    private String productCode;

    /**
     *   �û�ID
     * Column: tb_aftersale.uid
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer uid;

    /**
     *   �ۺ�ԭ��
     * Column: tb_aftersale.reason
    @mbggenerated 2017-06-05 14:54:37
     */
    private String reason;

    /**
     *   �ۺ��Ʒ����
     * Column: tb_aftersale.product_type
    @mbggenerated 2017-06-05 14:54:37
     */
    private String productType;

    /**
     *   ��ע
     * Column: tb_aftersale.remark
    @mbggenerated 2017-06-05 14:54:37
     */
    private String remark;

    /**
     *   �ͷ�����
     * Column: tb_aftersale.reply
    @mbggenerated 2017-06-05 14:54:37
     */
    private String reply;

    /**
     *   ����PC��,�ƶ���
     * Column: tb_aftersale.channel
    @mbggenerated 2017-06-05 14:54:37
     */
    private String channel;

    /**
     * Column: tb_aftersale.create_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date createTime;

    /**
     * Column: tb_aftersale.update_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date updateTime;

    /**
     *   ����ʱ��
     * Column: tb_aftersale.handle_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date handleTime;

    /**
     * Column: tb_aftersale.finish_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date finishTime;

    /**
     *   ����ʱ��
     * Column: tb_aftersale.replenish_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date replenishTime;

    /**
     *   �ͻ��ύ���˵���ʱ��
     * Column: tb_aftersale.logistics_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date logisticsTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAftercode() {
        return aftercode;
    }

    public void setAftercode(String aftercode) {
        this.aftercode = aftercode == null ? null : aftercode.trim();
    }

    public Integer getAftertype() {
        return aftertype;
    }

    public void setAftertype(Integer aftertype) {
        this.aftertype = aftertype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
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

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getReplenishTime() {
        return replenishTime;
    }

    public void setReplenishTime(Date replenishTime) {
        this.replenishTime = replenishTime;
    }

    public Date getLogisticsTime() {
        return logisticsTime;
    }

    public void setLogisticsTime(Date logisticsTime) {
        this.logisticsTime = logisticsTime;
    }
}