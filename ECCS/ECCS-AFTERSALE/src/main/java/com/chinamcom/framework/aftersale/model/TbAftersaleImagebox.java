package com.chinamcom.framework.aftersale.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_aftersale_imagebox
@mbggenerated do_not_delete_during_merge 2016-11-09 13:35:22
 */
public class TbAftersaleImagebox {
    /**
     * Column: tb_aftersale_imagebox.id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer id;

    /**
     *   售后服务ID
     * Column: tb_aftersale_imagebox.after_id
    @mbggenerated 2016-11-09 13:35:22
     */
    private Integer afterId;

    /**
     * Column: tb_aftersale_imagebox.after_code
    @mbggenerated 2016-11-09 13:35:22
     */
    private String afterCode;

    /**
     * Column: tb_aftersale_imagebox.image_uri
    @mbggenerated 2016-11-09 13:35:22
     */
    private String imageUri;

    /**
     * Column: tb_aftersale_imagebox.create_time
    @mbggenerated 2016-11-09 13:35:22
     */
    private Date createTime;

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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri == null ? null : imageUri.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}