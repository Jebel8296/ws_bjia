package com.chinamcom.framework.products.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

import io.vertx.core.json.JsonObject;

/**
 * [STRATO MyBatis Generator] Table: tb_product_info
 * 
 * @mbggenerated do_not_delete_during_merge 2016-10-31 15:59:58
 */
public class TbProductInfo {
	/**
	 * Column: tb_product_info.ID
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Integer id;

	/**
	 * ��Ʒ���� Column: tb_product_info.PRODCODE
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private String prodcode;

	/**
	 * ��Ʒ���� Column: tb_product_info.PRODNAME
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private String prodname;

	/**
	 * ��Ʒ���� Column: tb_product_info.SECONDNAME
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private String secondname;

	/**
	 * ��Ʒ���� Column: tb_product_info.PRODDESC
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private String proddesc;

	/**
	 * ��Ʒ�۸� Column: tb_product_info.PRODPRICE
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private BigDecimal prodprice;

	/**
	 * ��Ʒ����1�ֱ�2����3�ֻ� Column: tb_product_info.PRODTYPE
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Integer prodtype;

	/**
	 * ��Ʒ�ϼ�����34�����豸 Column: tb_product_info.PARENTTYPE
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Integer parenttype;

	/**
	 * ״̬1����0���� Column: tb_product_info.STATUS
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Integer status;

	/**
	 * Column: tb_product_info.CREATE_TIME
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Date createTime;

	/**
	 * �Ƿ��л1��0�� Column: tb_product_info.ACTIVITY
	 * 
	 * @mbggenerated 2016-10-31 15:59:58
	 */
	private Integer activity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProdcode() {
		return prodcode;
	}

	public void setProdcode(String prodcode) {
		this.prodcode = prodcode == null ? null : prodcode.trim();
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname == null ? null : prodname.trim();
	}

	public String getSecondname() {
		return secondname;
	}

	public void setSecondname(String secondname) {
		this.secondname = secondname == null ? null : secondname.trim();
	}

	public String getProddesc() {
		return proddesc;
	}

	public void setProddesc(String proddesc) {
		this.proddesc = proddesc == null ? null : proddesc.trim();
	}

	public BigDecimal getProdprice() {
		return prodprice;
	}

	public void setProdprice(BigDecimal prodprice) {
		this.prodprice = prodprice;
	}

	public Integer getProdtype() {
		return prodtype;
	}

	public void setProdtype(Integer prodtype) {
		this.prodtype = prodtype;
	}

	public Integer getParenttype() {
		return parenttype;
	}

	public void setParenttype(Integer parenttype) {
		this.parenttype = parenttype;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	@Transient
	private BigDecimal price;
	@Transient
	private String model;
	@Transient
	private String color;
	@Transient
	private String colorname;
	@Transient
	private Integer stock;
	@Transient
	private String warning;

	@Transient
	private BigDecimal activityPrice;
	@Transient
	private String activityDesc;
	@Transient
	private Date beginTime;
	@Transient
	private Date endTime;
	@Transient
	private JsonObject showImages;
	@Transient
	private JsonObject buyImages;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
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

	public JsonObject getShowImages() {
		return showImages;
	}

	public void setShowImages(JsonObject showImages) {
		this.showImages = showImages;
	}

	public JsonObject getBuyImages() {
		return buyImages;
	}

	public void setBuyImages(JsonObject buyImages) {
		this.buyImages = buyImages;
	}

	public String getColorname() {
		return colorname;
	}

	public void setColorname(String colorname) {
		this.colorname = colorname;
	}
	

}