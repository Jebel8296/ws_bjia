package com.chinamcom.framework.products.model;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_devices
@mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
 */
public class TbProductDevices {
    /**
     * Column: tb_product_devices.ID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer id;

    /**
     *   产品ID
     * Column: tb_product_devices.PRODID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer prodid;

    /**
     *   产品编码
     * Column: tb_product_devices.PRODCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String prodcode;

    /**
     *   设备编码
     * Column: tb_product_devices.DEVICESCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String devicescode;

    /**
     *   产品类型40手表41耳机
     * Column: tb_product_devices.PRODTYPE
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer prodtype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProdid() {
        return prodid;
    }

    public void setProdid(Integer prodid) {
        this.prodid = prodid;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode == null ? null : prodcode.trim();
    }

    public String getDevicescode() {
        return devicescode;
    }

    public void setDevicescode(String devicescode) {
        this.devicescode = devicescode == null ? null : devicescode.trim();
    }

    public Integer getProdtype() {
        return prodtype;
    }

    public void setProdtype(Integer prodtype) {
        this.prodtype = prodtype;
    }
}