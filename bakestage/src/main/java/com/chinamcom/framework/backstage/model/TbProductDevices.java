package com.chinamcom.framework.backstage.model;

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
     *   ��ƷID
     * Column: tb_product_devices.PRODID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer prodid;

    /**
     *   ��Ʒ����
     * Column: tb_product_devices.PRODCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String prodcode;

    /**
     *   �豸����
     * Column: tb_product_devices.DEVICESCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String devicescode;

    /**
     *   ��Ʒ����40�ֱ�41����
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