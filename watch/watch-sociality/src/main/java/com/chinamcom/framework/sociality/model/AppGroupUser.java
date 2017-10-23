package com.chinamcom.framework.sociality.model;

public class AppGroupUser {
	private Integer id;// 编号
	private Integer gid;// 群组编号
	private Integer uid;// 用户编号
	private Integer status;// 用户状态
	private Integer watchstat;// 手表是否接收群消息
	private Integer screenstat;// 是否屏蔽群消息
//	private String groupAlias;// 用户在该组昵称
	private String headimage;// 头像
	private String alias;// 用户在该组昵称/本人对好友的备注/个人昵称
	
//	public String getGroupAlias() {
//		return groupAlias;
//	}
//
//	public void setGroupAlias(String groupAlias) {
//		this.groupAlias = groupAlias;
//	}

	public AppGroupUser() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHeadimage() {
		return headimage;
	}

	public void setHeadimage(String headimage) {
		this.headimage = headimage;
	}

	public Integer getWatchstat() {
		return watchstat;
	}

	public void setWatchstat(Integer watchstat) {
		this.watchstat = watchstat;
	}

	public Integer getScreenstat() {
		return screenstat;
	}

	public void setScreenstat(Integer screenstat) {
		this.screenstat = screenstat;
	}
	

}
