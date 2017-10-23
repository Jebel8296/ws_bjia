package com.chinamcom.framework.device.model;

public class ChatMessageInfo {
	private Integer id;
	private Integer uid;
	private String type;
	private String body;
	private Integer from;
	private Integer fromg;
	private Long create;
	private String to;
	private Integer size;
	private Integer duration;
	private String nickName;
	private String headImg;
	
	public ChatMessageInfo(){}
	
	public ChatMessageInfo(Integer id, Integer uid, String type, String body,
			Integer from, Integer fromg, Long create,Integer duration,String nickName,String headImg) {
		super();
		this.id = id;
		this.uid = uid;
		this.type = type;
		this.body = body;
		this.from = from;
		this.fromg = fromg;
		this.create = create;
		this.duration = duration;
		this.nickName = nickName;
		this.headImg = headImg;
	}
	
	public ChatMessageInfo(Integer id, Integer uid, String type, String body,
			Integer from, Integer fromg, String to, Long create, Integer size) {
		super();
		this.id = id;
		this.uid = uid;
		this.type = type;
		this.body = body;
		this.from = from;
		this.fromg = fromg;
		this.to = to;
		this.create = create;
		this.size = size;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Integer getFrom() {
		return from;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public Integer getFromg() {
		return fromg;
	}

	public void setFromg(Integer fromg) {
		this.fromg = fromg;
	}

	public Long getCreate() {
		return create;
	}

	public void setCreate(Long create) {
		this.create = create;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
}
