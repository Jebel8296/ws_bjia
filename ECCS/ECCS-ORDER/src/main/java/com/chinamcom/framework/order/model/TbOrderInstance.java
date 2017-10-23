package com.chinamcom.framework.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_order_instance
@mbggenerated do_not_delete_during_merge 2016-10-18 17:07:00
 */
public class TbOrderInstance {
    /**
     * Column: tb_order_instance.ID
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer id;

    /**
     *   ����ID
     * Column: tb_order_instance.ORDER_ID
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer orderId;

    /**
     *   ��ƷID
     * Column: tb_order_instance.PRODUCT_ID
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer productId;

    /**
     *   ��Ʒ����
     * Column: tb_order_instance.PRODUCT_CODE
    @mbggenerated 2016-10-18 17:07:00
     */
    private String productCode;

    /**
     *   ��ƷID
     * Column: tb_order_instance.SKU_ID
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer skuId;

    /**
     *   ��Ʒ����
     * Column: tb_order_instance.PRODUCT_NAME
    @mbggenerated 2016-10-18 17:07:00
     */
    private String productName;

    /**
     *   ��Ʒ�۸�
     * Column: tb_order_instance.PRODUCT_PRICE
    @mbggenerated 2016-10-18 17:07:00
     */
    private BigDecimal productPrice;

    /**
     *   ��Ʒ����
     * Column: tb_order_instance.PRODUCT_TOTAL
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer productTotal;

    /**
     *   Ӧ�����
     * Column: tb_order_instance.PAY_FEE
    @mbggenerated 2016-10-18 17:07:00
     */
    private BigDecimal payFee;

    /**
     *   �������
     * Column: tb_order_instance.REDUCE_FEE
    @mbggenerated 2016-10-18 17:07:00
     */
    private BigDecimal reduceFee;

    /**
     *   ����ʱ��
     * Column: tb_order_instance.CREATE_TIME
    @mbggenerated 2016-10-18 17:07:00
     */
    private Date createTime;

    /**
     *   ����ʱ��
     * Column: tb_order_instance.UPDATE_TIME
    @mbggenerated 2016-10-18 17:07:00
     */
    private Date updateTime;

    /**
     *   �û�ID
     * Column: tb_order_instance.USER_ID
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer userId;

    /**
     *   ״̬1���ﳵ3����2ɾ��4ȡ��
     * Column: tb_order_instance.STATUS
    @mbggenerated 2016-10-18 17:07:00
     */
    private Integer status;
    
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
     
}