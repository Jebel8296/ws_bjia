package com.chinamcom.framework.backstage.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_aftersale_reply
@mbggenerated do_not_delete_during_merge 2017-07-04 15:56:44
 */
public class TbAftersaleReply {
    /**
     * Column: tb_aftersale_reply.id
    @mbggenerated 2017-07-04 15:56:44
     */
    private Integer id;

    /**
     * Column: tb_aftersale_reply.afterid
    @mbggenerated 2017-07-04 15:56:44
     */
    private Integer afterid;

    /**
     * Column: tb_aftersale_reply.aftercode
    @mbggenerated 2017-07-04 15:56:44
     */
    private String aftercode;

    /**
     * Column: tb_aftersale_reply.status_from
    @mbggenerated 2017-07-04 15:56:44
     */
    private Integer statusFrom;

    /**
     * Column: tb_aftersale_reply.status_to
    @mbggenerated 2017-07-04 15:56:44
     */
    private Integer statusTo;

    /**
     * Column: tb_aftersale_reply.reply
    @mbggenerated 2017-07-04 15:56:44
     */
    private String reply;

    /**
     * Column: tb_aftersale_reply.create_time
    @mbggenerated 2017-07-04 15:56:44
     */
    private Date createTime;

    /**
     * Column: tb_aftersale_reply.remark
    @mbggenerated 2017-07-04 15:56:44
     */
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAfterid() {
        return afterid;
    }

    public void setAfterid(Integer afterid) {
        this.afterid = afterid;
    }

    public String getAftercode() {
        return aftercode;
    }

    public void setAftercode(String aftercode) {
        this.aftercode = aftercode == null ? null : aftercode.trim();
    }

    public Integer getStatusFrom() {
        return statusFrom;
    }

    public void setStatusFrom(Integer statusFrom) {
        this.statusFrom = statusFrom;
    }

    public Integer getStatusTo() {
        return statusTo;
    }

    public void setStatusTo(Integer statusTo) {
        this.statusTo = statusTo;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}