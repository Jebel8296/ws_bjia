package com.chinamcom.framework.user.dao;

public class FriendLikesInfo implements Cloneable  {
	private Integer uid;
	private String nickName;
	private String headImg;
	private Integer step;
	private Integer likesNum;
	private Integer status;
	private Integer rank;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
	public Integer getLikesNum() {
		return likesNum;
	}
	public void setLikesNum(Integer likesNum) {
		this.likesNum = likesNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }  
}
