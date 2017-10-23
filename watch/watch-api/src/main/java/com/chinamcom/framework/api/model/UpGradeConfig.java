package com.chinamcom.framework.api.model;

import java.util.Date;

public class UpGradeConfig {
 	private String upChannel;
 	
 	private String upVersion;

    private String upServer;

    private Integer isUpdate;

    private Date upStartTime;

    private Date upEndTime;

    private String noticePage;

    private String noticeInfo;

    private String remark;

	public String getUpChannel() {
		return upChannel;
	}

	public void setUpChannel(String upChannel) {
		this.upChannel = upChannel;
	}

	public String getUpVersion() {
		return upVersion;
	}

	public void setUpVersion(String upVersion) {
		this.upVersion = upVersion;
	}

	public String getUpServer() {
		return upServer;
	}

	public void setUpServer(String upServer) {
		this.upServer = upServer;
	}

	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Date getUpStartTime() {
		return upStartTime;
	}

	public void setUpStartTime(Date upStartTime) {
		this.upStartTime = upStartTime;
	}

	public Date getUpEndTime() {
		return upEndTime;
	}

	public void setUpEndTime(Date upEndTime) {
		this.upEndTime = upEndTime;
	}

	public String getNoticePage() {
		return noticePage;
	}

	public void setNoticePage(String noticePage) {
		this.noticePage = noticePage;
	}

	public String getNoticeInfo() {
		return noticeInfo;
	}

	public void setNoticeInfo(String noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
