package com.chinamcom.framework.user.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppUserInfo {
	private Integer id;
	private String nickname;
	private Integer sex;
	private Date birthday;
	private Integer height;
	private Integer weight;
	private String phonenum;
	private String email;
	private String headimage;
	private Integer uid;
	private Date updatetime;
	private Integer target;
	private Integer watchHand;
	private String watchPwd;
	private Integer firstLogin;//第一次登陆标识：（0：否，1：是）
	private Date createTime;
	private Integer status;//0 申请好友 1 同意好友申请 成为好友；2 删除好友；-1 拒绝好友申请 不为好友；-2 删除好友申请 不为好友
	private String alias0;
	private Integer uid0;
	private Integer watchstat;//是否需要在手表上显示(0：否，1:是)
	private Integer uid1;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUid1() {
		return uid1;
	}
	public void setUid1(Integer uid1) {
		this.uid1 = uid1;
	}
	public Integer getWatchstat() {
		return watchstat;
	}
	public void setWatchstat(Integer watchstat) {
		this.watchstat = watchstat;
	}
	public Integer getUid0() {
		return uid0;
	}
	public void setUid0(Integer uid0) {
		this.uid0 = uid0;
	}
	public String getAlias0() {
		return alias0;
	}
	public void setAlias0(String alias0) {
		this.alias0 = alias0;
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
	public Integer getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Integer firstLogin) {
		this.firstLogin = firstLogin;
	}
	public Integer getWatchHand() {
		return watchHand;
	}
	public void setWatchHand(Integer watchHand) {
		this.watchHand = watchHand;
	}
	public String getWatchPwd() {
		return watchPwd;
	}
	public void setWatchPwd(String watchPwd) {
		this.watchPwd = watchPwd;
	}
	private List<Map<String, Object>> tags;
 	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadimage() {
		return headimage;
	}
	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public List<Map<String, Object>> getTags() {
		return tags;
	}
	public void setTags(List<Map<String, Object>> tags) {
		this.tags = tags;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
}
