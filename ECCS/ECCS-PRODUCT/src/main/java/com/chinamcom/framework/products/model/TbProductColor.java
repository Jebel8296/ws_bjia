package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_color
@mbggenerated do_not_delete_during_merge 2016-11-03 17:40:29
 */
public class TbProductColor {
    /**
     * Column: tb_product_color.id
    @mbggenerated 2016-11-03 17:40:29
     */
    private Integer id;

    /**
     * Column: tb_product_color.type_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String typeCode;

    /**
     * Column: tb_product_color.model_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String modelCode;

    /**
     *   ÑÕÉ«±àÂë
     * Column: tb_product_color.color_code
    @mbggenerated 2016-11-03 17:40:29
     */
    private String colorCode;

    /**
     *   ÑÕÉ«Ãû³Æ
     * Column: tb_product_color.color_name
    @mbggenerated 2016-11-03 17:40:29
     */
    private String colorName;

    /**
     * Column: tb_product_color.create_time
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode == null ? null : colorCode.trim();
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName == null ? null : colorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}