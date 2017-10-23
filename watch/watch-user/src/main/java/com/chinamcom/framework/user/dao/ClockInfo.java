package com.chinamcom.framework.user.dao;

import java.util.Date;
import java.util.List;

import com.chinamcom.framework.user.push.RepeatDateUtil;

	
public class ClockInfo {
	private Integer id;
	private String did;
	private Integer modelType;
	private Integer type;
	private String title;
	private String time;
	private String repeat;
	private String repeatDate;//转换后的重复日期
	private Integer remind;
	private String remainVoiceId;
	private Integer ring;
	private Integer status;
	private Integer vol;
	private Date createTime;
	private String remindInputContent;
	private List<RepeatDateUtil> repeatList;
	/*早上  4：00-9：59
	白天  10：00-16：59
	夜晚  17：00-3：59*/
	private Integer picType;//闹钟图片分类：0，早上、1:，中午，2：晚上，
	private Integer uid;
	private String voiceBody;
	private Integer voice;//用于闹钟推送，辨识语音录制的字段
	private Integer volsize;//录制语音时长
	
	public Integer getVolsize() {
		return volsize;
	}
	public void setVolsize(Integer volsize) {
		this.volsize = volsize;
	}
	public Integer getVoice() {
		return voice;
	}
	public void setVoice(Integer voice) {
		this.voice = voice;
	}
	public String getVoiceBody() {
		return voiceBody;
	}
	public void setVoiceBody(String voiceBody) {
		this.voiceBody = voiceBody;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getRepeatDate() {
		return repeatDate;
	}
	public void setRepeatDate(String repeatDate) {
		this.repeatDate = repeatDate;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public Integer getRemind() {
		return remind;
	}
	public void setRemind(Integer remind) {
		this.remind = remind;
	}
	public Integer getRing() {
		return ring;
	}
	public void setRing(Integer ring) {
		this.ring = ring;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemindInputContent() {
		return remindInputContent;
	}
	public void setRemindInputContent(String remindInputContent) {
		this.remindInputContent = remindInputContent;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public Integer getModelType() {
		return modelType;
	}
	public void setModelType(Integer modelType) {
		this.modelType = modelType;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemainVoiceId() {
		return remainVoiceId;
	}
	public void setRemainVoiceId(String remainVoiceId) {
		this.remainVoiceId = remainVoiceId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getVol() {
		return vol;
	}
	public void setVol(Integer vol) {
		this.vol = vol;
	}
	public Integer getPicType() {
		return picType;
	}
	public void setPicType(Integer picType) {
		this.picType = picType;
	}
	public List<RepeatDateUtil> getRepeatList() {
		return repeatList;
	}
	public void setRepeatList(List<RepeatDateUtil> repeatList) {
		this.repeatList = repeatList;
	}
}
