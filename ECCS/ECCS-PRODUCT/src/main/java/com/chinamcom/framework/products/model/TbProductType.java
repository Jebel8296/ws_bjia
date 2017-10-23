package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_type
@mbggenerated do_not_delete_during_merge 2016-11-03 17:40:29
 */
public class TbProductType {
    /**
     * Column: tb_product_type.id
    @mbggenerated 2016-11-03 17:40:29
     */
    private Integer id;

    /**
     *   产品类型40：手表41：耳机
     * Column: tb_product_type.type_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String typeCode;

    /**
     *   产品类型名称
     * Column: tb_product_type.type_name
    @mbggenerated 2016-11-03 17:40:29
     */
    private String typeName;

    /**
     * Column: tb_product_type.create_time
    @mbggenerated 2016-11-03 17:40:29
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}