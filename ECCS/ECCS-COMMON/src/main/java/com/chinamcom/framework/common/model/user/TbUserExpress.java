package com.chinamcom.framework.common.model.user;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_user_express
@mbggenerated do_not_delete_during_merge 2016-10-27 10:56:50
 */
public class TbUserExpress {
    /**
     * Column: tb_user_express.ID
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer id;

    /**
     *   �û�ID
     * Column: tb_user_express.USERID
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer userid;

    /**
     *   �û�����
     * Column: tb_user_express.NAME
    @mbggenerated 2016-10-27 10:56:50
     */
    private String name;

    /**
     *   ��ϵ�绰
     * Column: tb_user_express.PHONE
    @mbggenerated 2016-10-27 10:56:50
     */
    private String phone;

    /**
     *   ʡID
     * Column: tb_user_express.PROVENCEID
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer provenceid;

    /**
     *   ��ID
     * Column: tb_user_express.CITYID
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer cityid;

    /**
     *   ��ID
     * Column: tb_user_express.AREAID
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer areaid;

    /**
     *   ��������
     * Column: tb_user_express.ZIPCODE
    @mbggenerated 2016-10-27 10:56:50
     */
    private String zipcode;

    /**
     *   ��ϸ��ַ
     * Column: tb_user_express.ADDRESS
    @mbggenerated 2016-10-27 10:56:50
     */
    private String address;

    /**
     *   ����ʱ��
     * Column: tb_user_express.CREATETIME
    @mbggenerated 2016-10-27 10:56:50
     */
    private Date createtime;

    /**
     *   ����ʱ��
     * Column: tb_user_express.UPDATETIME
    @mbggenerated 2016-10-27 10:56:50
     */
    private Date updatetime;

    /**
     *   0����1Ĭ��2ɾ��
     * Column: tb_user_express.STATUS
    @mbggenerated 2016-10-27 10:56:50
     */
    private Integer status;

    /**
     *   ʡ
     * Column: tb_user_express.province
    @mbggenerated 2016-10-27 10:56:50
     */
    private String province;

    /**
     *   ��
     * Column: tb_user_express.city
    @mbggenerated 2016-10-27 10:56:50
     */
    private String city;

    /**
     *   ����
     * Column: tb_user_express.area
    @mbggenerated 2016-10-27 10:56:50
     */
    private String area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
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

    public Integer getProvenceid() {
        return provenceid;
    }

    public void setProvenceid(Integer provenceid) {
        this.provenceid = provenceid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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