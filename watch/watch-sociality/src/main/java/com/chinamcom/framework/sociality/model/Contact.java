package com.chinamcom.framework.sociality.model;

import java.util.List;

public class Contact {
	private Integer id;
	private Integer uid;
	private String name;
	private String head;
	private Integer watchstat;
	private String address;
	private String remark;
	private List<ContactPhone> phones;

	private Integer icon;
	private String phone;

	public Integer getWatchstat() {
		return watchstat;
	}

	public void setWatchstat(Integer watchstat) {
		this.watchstat = watchstat;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIcon() {
		return icon;
	}

	public void setIcon(Integer icon) {
		this.icon = icon;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<ContactPhone> getPhones() {
		return phones;
	}

	public void setPhones(List<ContactPhone> phones) {
		this.phones = phones;
	}

}
