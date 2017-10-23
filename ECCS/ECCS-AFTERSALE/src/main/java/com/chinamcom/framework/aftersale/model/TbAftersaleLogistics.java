package com.chinamcom.framework.aftersale.model;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_aftersale_logistics
@mbggenerated do_not_delete_during_merge 2016-11-09 13:35:22
 */
public class TbAftersaleLogistics {
    /**
     * Column: tb_aftersale_logistics.id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer id;

    /**
     * Column: tb_aftersale_logistics.after_id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer afterId;

    /**
     * Column: tb_aftersale_logistics.after_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String afterCode;

    /**
     *   收货地址
     * Column: tb_aftersale_logistics.express_id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer expressId;

    /**
     *   物流单号
     * Column: tb_aftersale_logistics.logistics_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String logisticsCode;

    /**
     *   物流状态
     * Column: tb_aftersale_logistics.logistics_status
    @mbggenerated 2016-11-09 13:35:22
     */
    private String logisticsStatus;

    /**
     * Column: tb_aftersale_logistics.name
    @mbggenerated 2016-11-09 13:35:22
     */
    private String name;

    /**
     * Column: tb_aftersale_logistics.phone
    @mbggenerated 2016-11-09 13:35:22
     */
    private String phone;

    /**
     * Column: tb_aftersale_logistics.zipcode
    @mbggenerated 2016-11-09 13:35:22
     */
    private String zipcode;

    /**
     * Column: tb_aftersale_logistics.address
    @mbggenerated 2016-11-09 13:35:22
     */
    private String address;

    /**
     * Column: tb_aftersale_logistics.province
    @mbggenerated 2016-11-09 13:35:22
     */
    private String province;

    /**
     * Column: tb_aftersale_logistics.city
    @mbggenerated 2016-11-09 13:35:22
     */
    private String city;

    /**
     * Column: tb_aftersale_logistics.area
    @mbggenerated 2016-11-09 13:35:22
     */
    private String area;

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

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode == null ? null : logisticsCode.trim();
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus == null ? null : logisticsStatus.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }
}