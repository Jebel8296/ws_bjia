package com.chinamcom.framework.products.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: tb_product_stock
@mbggenerated do_not_delete_during_merge 2017-06-27 15:24:51
 */
public class TbProductStock {
    /**
     * Column: tb_product_stock.ID
    @mbggenerated 2017-06-27 15:24:51
     */
    private Integer id;

    /**
     *   商品ID
     * Column: tb_product_stock.PRODID
    @mbggenerated 2017-06-27 15:24:51
     */
    private Integer prodid;

    /**
     *   商品编码
     * Column: tb_product_stock.PRODCODE
    @mbggenerated 2017-06-27 15:24:51
     */
    private String prodcode;

    /**
     *   配额
     * Column: tb_product_stock.QUOTA
    @mbggenerated 2017-06-27 15:24:51
     */
    private Integer quota;

    /**
     *   库存
     * Column: tb_product_stock.STOCK
    @mbggenerated 2017-06-27 15:24:51
     */
    private Integer stock;

    /**
     *   销量
     * Column: tb_product_stock.SALES
    @mbggenerated 2017-06-27 15:24:51
     */
    private Integer sales;

    /**
     *   创建时间
     * Column: tb_product_stock.CREATE_TIME
    @mbggenerated 2017-06-27 15:24:51
     */
    private Date createTime;

    /**
     *   更新时间
     * Column: tb_product_stock.UPDATE_TIME
    @mbggenerated 2017-06-27 15:24:51
     */
    private Date updateTime;

    /**
     *   库存预警 green：库存充足（STOCK>100)   red:库存紧张(STOCK<100)  gray:无库存(STOCK=0)
     * Column: tb_product_stock.WARNING
    @mbggenerated 2017-06-27 15:24:51
     */
    private String warning;

    /**
     *   管易产品编码
     * Column: tb_product_stock.ITEMCODE
    @mbggenerated 2017-06-27 15:24:51
     */
    private String itemcode;

    /**
     *   备注
     * Column: tb_product_stock.REMARK
    @mbggenerated 2017-06-27 15:24:51
     */
    private String remark;

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

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning == null ? null : warning.trim();
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode == null ? null : itemcode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}