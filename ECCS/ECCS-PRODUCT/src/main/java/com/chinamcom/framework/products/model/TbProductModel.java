package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_model
@mbggenerated do_not_delete_during_merge 2016-11-03 17:40:29
 */
public class TbProductModel {
    /**
     * Column: tb_product_model.id
    @mbggenerated 2016-11-03 17:40:29
     */
    private Integer id;

    /**
     *   产品类型40：手表41：耳机
     * Column: tb_product_model.type_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String typeCode;

    /**
     *   产品型号
     * Column: tb_product_model.model_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String modelCode;

    /**
     *   型号名称
     * Column: tb_product_model.model_name
    @mbggenerated 2016-11-03 17:40:29
     */
    private String modelName;

    /**
     * Column: tb_product_model.create_time
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

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}