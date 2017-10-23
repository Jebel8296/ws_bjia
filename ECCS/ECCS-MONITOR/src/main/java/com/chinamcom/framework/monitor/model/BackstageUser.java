package com.chinamcom.framework.monitor.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator] Table: backstage_user
 * 
 * @mbggenerated do_not_delete_during_merge 2017-02-06 17:46:14
 */
public class BackstageUser {
	/**
	 * Column: backstage_user.id
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private Integer id;

	/**
	 * �û����� Column: backstage_user.username
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String username;

	/**
	 * �û����� Column: backstage_user.email
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String email;

	/**
	 * ��ϵ��ʽ Column: backstage_user.phone
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String phone;

	/**
	 * ���� Column: backstage_user.password
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String password;

	/**
	 * �ǳ� Column: backstage_user.nickname
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String nickname;

	/**
	 * ״̬ 1������ 0������ Column: backstage_user.status
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private Integer status;

	/**
	 * ͷ�� Column: backstage_user.head_portrait
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private String headPortrait;

	/**
	 * ����ʱ�� Column: backstage_user.create_time
	 * 
	 * @mbggenerated 2017-02-06 17:46:14
	 */
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait == null ? null : headPortrait.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}