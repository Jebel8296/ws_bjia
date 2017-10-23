package com.chinamcom.framework.backstage.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_aftersale_product
@mbggenerated do_not_delete_during_merge 2016-11-09 13:35:22
 */
public class TbAftersaleProduct {
    /**
     * Column: tb_aftersale_product.id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer id;

    /**
     *   �ۺ�ID
     * Column: tb_aftersale_product.after_id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer afterId;

    /**
     * Column: tb_aftersale_product.after_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String afterCode;

    /**
     *   ��Ʒ����
     * Column: tb_aftersale_product.product_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String productCode;

    /**
     *   ��Ʒ����
     * Column: tb_aftersale_product.product_name
    @mbggenerated 2016-11-09 13:35:22
     */
    private String productName;

    /**
     *   �豸����

     * Column: tb_aftersale_product.device_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String deviceCode;

    /**
     *   ǩ������
     * Column: tb_aftersale_product.sign_time
    @mbggenerated 2016-11-09 13:35:22
     */
    private Date signTime;

    /**
     *   �绰
     * Column: tb_aftersale_product.phone
    @mbggenerated 2016-11-09 13:35:22
     */
    private String phone;

    /**
     * Column: tb_aftersale_product.quantity
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer quantity;

    /**
     * Column: tb_aftersale_product.create_time
    @mbggenerated 2016-11-09 13:35:22
     */
    private Date createTime;

    /**
     * Column: tb_aftersale_product.update_time
    @mbggenerated 2016-11-09 13:35:22
     */
    private Date updateTime;

    /**
     *   ��ע
     * Column: tb_aftersale_product.remark
    @mbggenerated 2016-11-09 13:35:22
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAfterId() {
        return afterId;
    }

    public void setAfterId(Integer afterId) {
        this.afterId = afterId;
    }

    public String getAfterCode() {
        return afterCode;
    }

    public void setAfterCode(String afterCode) {
        this.afterCode = afterCode == null ? null : afterCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode == null ? null : deviceCode.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}