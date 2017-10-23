package com.chinamcom.framework.sport.model;

import java.util.Date;

public class SportHealthWeekly {

	private Integer id ;
	private String imei;
	private Integer step;
	private Integer calorie;
	//系统里一周是按照周日到周六算的
	private Date weekstarTime;//周开始时间
	private Date weekendTime;//周结束时间
	private Date createTime;
	private Integer rank;
	private Integer sexRank;
	private Integer uid;
	private String headImg;
	private String nickName;
	private Integer show; //排名显示标识 0后台填充的不展示数据
	private Integer sex;//性别
	
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getCalorie() {
		return calorie;
	}
	public void setCalorie(Integer calorie) {
		this.calorie = calorie;
	}
	public Date getWeekstarTime() {
		return weekstarTime;
	}
	public void setWeekstarTime(Date weekstarTime) {
		this.weekstarTime = weekstarTime;
	}
	public Date getWeekendTime() {
		return weekendTime;
	}
	public void setWeekendTime(Date weekendTime) {
		this.weekendTime = weekendTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getSexRank() {
		return sexRank;
	}
	public void setSexRank(Integer sexRank) {
		this.sexRank = sexRank;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getShow() {
		return show;
	}
	public void setShow(Integer show) {
		this.show = show;
	}
}
