package com.chinamcom.framework.user.dao;

import java.util.Date;

public class FeedBackInfo {
	private Integer id;
	private String userId;
	private String feedbackContent;//反馈内容
	private String feedbackContact;//联系方式
	private Date feedbackTime;
	private String feedbackStatus;//反馈状态
	private String processContent;//处理内容
	private Date processTime;//处理时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFeedbackContent() {
		return feedbackContent;
	}
	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
	}
	public String getFeedbackContact() {
		return feedbackContact;
	}
	public void setFeedbackContact(String feedbackContact) {
		this.feedbackContact = feedbackContact;
	}
	public Date getFeedbackTime() {
		return feedbackTime;
	}
	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	public String getFeedbackStatus() {
		return feedbackStatus;
	}
	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}
	public String getProcessContent() {
		return processContent;
	}
	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}
}
