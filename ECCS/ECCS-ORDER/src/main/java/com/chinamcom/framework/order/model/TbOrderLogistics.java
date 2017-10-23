package com.chinamcom.framework.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_order_logistics
@mbggenerated do_not_delete_during_merge 2016-10-18 17:07:20
 */
public class TbOrderLogistics {
    /**
     * Column: tb_order_logistics.ID
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer id;

    /**
     *   ����ID
     * Column: tb_order_logistics.ORDER_ID
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer orderId;

    /**
     *   �ռ�������
     * Column: tb_order_logistics.NAME
    @mbggenerated 2016-10-18 17:07:20
     */
    private String name;

    /**
     *   ��ϵ�绰
     * Column: tb_order_logistics.PHONE
    @mbggenerated 2016-10-18 17:07:20
     */
    private String phone;

    /**
     *   ��������
     * Column: tb_order_logistics.ZIPCODE
    @mbggenerated 2016-10-18 17:07:20
     */
    private String zipcode;

    /**
     *   ʡ
     * Column: tb_order_logistics.PROVINCE
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer province;

    /**
     *   ����
     * Column: tb_order_logistics.CITY
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer city;

    /**
     *   ����
     * Column: tb_order_logistics.AREA
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer area;

    /**
     *   ��ַ
     * Column: tb_order_logistics.ADDRESS
    @mbggenerated 2016-10-18 17:07:20
     */
    private String address;

    /**
     *   �ͻ�ʱ��1������2˫����3����
     * Column: tb_order_logistics.DISPATCH_TIME
    @mbggenerated 2016-10-18 17:07:20
     */
    private Integer dispatchTime;

    /**
     *   ��Ʊ̧ͷ
     * Column: tb_order_logistics.INVOCE
    @mbggenerated 2016-10-18 17:07:20
     */
    private String invoce;

    /**
     *   ��������
     * Column: tb_order_logistics.LOGISTICS_SERIAL
    @mbggenerated 2016-10-18 17:07:20
     */
    private String logisticsSerial;

    /**
     *   ��ݷ���
     * Column: tb_order_logistics.PRICE
    @mbggenerated 2016-10-18 17:07:20
     */
    private BigDecimal price;

    /**
     *   ������˾����
     * Column: tb_order_logistics.LOGISTICS_COMPANY_NAME
    @mbggenerated 2016-10-18 17:07:20
     */
    private String logisticsCompanyName;

    /**
     * Column: tb_order_logistics.CREATE_TIME
    @mbggenerated 2016-10-18 17:07:20
     */
    private Date createTime;

    /**
     * Column: tb_order_logistics.UPDATE_TIME
    @mbggenerated 2016-10-18 17:07:20
     */
    private Date updateTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Integer dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getInvoce() {
        return invoce;
    }

    public void setInvoce(String invoce) {
        this.invoce = invoce == null ? null : invoce.trim();
    }

    public String getLogisticsSerial() {
        return logisticsSerial;
    }

    public void setLogisticsSerial(String logisticsSerial) {
        this.logisticsSerial = logisticsSerial == null ? null : logisticsSerial.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName == null ? null : logisticsCompanyName.trim();
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
}