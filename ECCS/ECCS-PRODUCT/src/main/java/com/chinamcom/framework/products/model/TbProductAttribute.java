package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_attribute
@mbggenerated do_not_delete_during_merge 2016-10-21 13:43:06
 */
public class TbProductAttribute {
    /**
     * Column: tb_product_attribute.ID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer id;

    /**
     *   产品ID
     * Column: tb_product_attribute.PRODID
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer prodid;

    /**
     *   产品编码
     * Column: tb_product_attribute.PRODCODE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String prodcode;

    /**
     *   属性
     * Column: tb_product_attribute.ATTRIBUTE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String attribute;

    /**
     *   属性值
     * Column: tb_product_attribute.VALUE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String value;

    /**
     * Column: tb_product_attribute.SVALUE
    @mbggenerated 2016-10-21 13:43:06
     */
    private String svalue;

    /**
     *   数据类型1 String  2 DECIMAL 3 int
     * Column: tb_product_attribute.DATATYPE
    @mbggenerated 2016-10-21 13:43:06
     */
    private Integer datatype;

    /**
     * Column: tb_product_attribute.CREATE_TIME
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getSvalue() {
        return svalue;
    }

    public void setSvalue(String svalue) {
        this.svalue = svalue == null ? null : svalue.trim();
    }

    public Integer getDatatype() {
        return datatype;
    }

    public void setDatatype(Integer datatype) {
        this.datatype = datatype;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}