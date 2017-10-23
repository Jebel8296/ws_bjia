package com.chinamcom.framework.upload.model;

import java.util.Date;

public class UploadFile {
	public static enum RightType {
		PUBLIC("公开"),
		PROTECTED("受保护");

		private String label;

		private RightType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public String getCode() {
			return name();
		}
	}
    private Integer id;
    private Integer uid;
    private String uri;
    private String mime;
    private String name;
    private Long size;
    private String type;
    private String website;
    private RightType rightType;
    private Date createdAt = new Date();
    private Date updatedAt = new Date();
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public RightType getRightType() {
		return rightType;
	}
	public void setRightType(RightType rightType) {
		this.rightType = rightType;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}
