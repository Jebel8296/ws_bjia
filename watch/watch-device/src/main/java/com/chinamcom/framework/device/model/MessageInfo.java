package com.chinamcom.framework.device.model;

import java.util.Date;

public class MessageInfo {
	private Integer id;
	private Integer uid;
	private Integer type;
	private String  title;
	private String content;
	private Integer status;
	private Date createTime;
	private String remark;
	
	
	public MessageInfo() {
	}
	public MessageInfo( Integer uid, Integer type, String title,
			String content, Integer status) {
		super();
		this.uid = uid;
		this.type = type;
		this.title = title;
		this.content = content;
		this.status = status;
	}
	
	public MessageInfo( Integer uid, Integer type, String title,
			String content, Integer status, String remark) {
		super();
		this.uid = uid;
		this.type = type;
		this.title = title;
		this.content = content;
		this.status = status;
		this.remark = remark;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
