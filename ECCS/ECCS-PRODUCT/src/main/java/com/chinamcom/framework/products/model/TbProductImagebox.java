package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_imagebox
@mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
 */
public class TbProductImagebox {
    /**
     * Column: tb_product_imagebox.ID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer id;

    /**
     *   ��ƷID
     * Column: tb_product_imagebox.PRODID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer prodid;

    /**
     * Column: tb_product_imagebox.PRODCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String prodcode;

    /**
     *   ͼƬ��ַ
     * Column: tb_product_imagebox.URI
    @mbggenerated 2016-10-21 13:43:06
     */
    private String uri;

    /**
     *   ����1���ڲ�Ʒ��Ϣ2���ڹ���ʹ��0��ҳչʾ
     * Column: tb_product_imagebox.TYPE
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer type;

    /**
     *   ���
     * Column: tb_product_imagebox.TYPENUM
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer typenum;

    /**
     *   ״̬ 1����0������
     * Column: tb_product_imagebox.STATUS
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer status;

    /**
     *   ����λ��
     * Column: tb_product_imagebox.LOCATION
    @mbggenerated 2016-10-21 13:43:06
     */
    private String location;

    /**
     *   ����ʱ��
     * Column: tb_product_imagebox.CREATE_TIME
    @mbggenerated 2016-10-21 13:43:06
     */
    private Date createTime;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTypenum() {
        return typenum;
    }

    public void setTypenum(Integer typenum) {
        this.typenum = typenum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}