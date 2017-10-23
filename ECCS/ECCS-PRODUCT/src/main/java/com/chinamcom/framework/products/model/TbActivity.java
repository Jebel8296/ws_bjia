package com.chinamcom.framework.products.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_activity
@mbggenerated do_not_delete_during_merge 2016-10-21 13:43:05
 */
public class TbActivity {
    /**
     * Column: tb_activity.ID
    @mbggenerated 2016-10-21 13:43:05
     */
    private Integer id;

    /**
     *   产品ID
     * Column: tb_activity.PRODID
    @mbggenerated 2016-10-21 13:43:05
     */
    private Integer prodid;

    /**
     *   产品编号
     * Column: tb_activity.PRODCODE
    @mbggenerated 2016-10-21 13:43:05
     */
    private String prodcode;

    /**
     *   活动类型1特价2周年庆.....
     * Column: tb_activity.TYPE
    @mbggenerated 2016-10-21 13:43:05
     */
    private Integer type;

    /**
     *   活动价格
     * Column: tb_activity.PRICE
    @mbggenerated 2016-10-21 13:43:05
     */
    private BigDecimal price;

    /**
     *   活动描述
     * Column: tb_activity.DESC
    @mbggenerated 2016-10-21 13:43:05
     */
    private String desc;

    /**
     *   开始时间
     * Column: tb_activity.BEGIN_TIME
    @mbggenerated 2016-10-21 13:43:05
     */
    private Date beginTime;

    /**
     *   结束时间
     * Column: tb_activity.END_TIME
    @mbggenerated 2016-10-21 13:43:05
     */
    private Date endTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}