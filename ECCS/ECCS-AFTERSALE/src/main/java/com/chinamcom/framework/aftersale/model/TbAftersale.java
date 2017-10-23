package com.chinamcom.framework.aftersale.model;

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
     *   售后服务编号:yyyyMMddHHmmssXXXX
     * Column: tb_aftersale.aftercode
    @mbggenerated 2017-06-05 14:54:37
     */
    private String aftercode;

    /**
     *   售后类型 1退货 2换货 3维修
     * Column: tb_aftersale.aftertype
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer aftertype;

    /**
     *   售后状态  0待受理1待邮寄2处理中3售后完成4关闭5待补充
     * Column: tb_aftersale.status
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer status;

    /**
     *   订单ID
     * Column: tb_aftersale.order_id
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer orderId;

    /**
     *   订单编号
     * Column: tb_aftersale.order_code
    @mbggenerated 2017-06-05 14:54:37
     */
    private String orderCode;

    /**
     *   商品编码，如果是通过设备编号提交，则为设备编号
     * Column: tb_aftersale.product_code
    @mbggenerated 2017-06-05 14:54:37
     */
    private String productCode;

    /**
     *   用户ID
     * Column: tb_aftersale.uid
    @mbggenerated 2017-06-05 14:54:37
     */
    private Integer uid;

    /**
     *   售后原因
     * Column: tb_aftersale.reason
    @mbggenerated 2017-06-05 14:54:37
     */
    private String reason;

    /**
     *   售后产品类型
     * Column: tb_aftersale.product_type
    @mbggenerated 2017-06-05 14:54:37
     */
    private String productType;

    /**
     *   备注
     * Column: tb_aftersale.remark
    @mbggenerated 2017-06-05 14:54:37
     */
    private String remark;

    /**
     *   客服反馈
     * Column: tb_aftersale.reply
    @mbggenerated 2017-06-05 14:54:37
     */
    private String reply;

    /**
     *   渠道PC端,移动端
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
     *   处理时间
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
     *   补充时间
     * Column: tb_aftersale.replenish_time
    @mbggenerated 2017-06-05 14:54:37
     */
    private Date replenishTime;

    /**
     *   客户提交货运单号时间
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