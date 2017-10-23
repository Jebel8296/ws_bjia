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
     *   商品ID
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
     *   图片地址
     * Column: tb_product_imagebox.URI
    @mbggenerated 2016-10-21 13:43:06
     */
    private String uri;

    /**
     *   类型1用于产品信息2用于购买使用0首页展示
     * Column: tb_product_imagebox.TYPE
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer type;

    /**
     *   序号
     * Column: tb_product_imagebox.TYPENUM
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer typenum;

    /**
     *   状态 1可用0不可用
     * Column: tb_product_imagebox.STATUS
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer status;

    /**
     *   本地位置
     * Column: tb_product_imagebox.LOCATION
    @mbggenerated 2016-10-21 13:43:06
     */
    private String location;

    /**
     *   创建时间
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