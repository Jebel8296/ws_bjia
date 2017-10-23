package com.chinamcom.framework.device.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/8.
 */
public class GroupUser {
    private Integer id;
    private Integer uid;
    private String name;
    private String head;
    private Date create_time;
    private Integer create_uid;
    private Integer status;
    private String description;
    private String alias;
    private Integer screenstat;
    private Integer topStatus;
    private Integer watchstat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getCreate_uid() {
        return create_uid;
    }

    public void setCreate_uid(Integer create_uid) {
        this.create_uid = create_uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "GroupUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", head='" + head + '\'' +
                ", create_time=" + create_time +
                ", create_uid=" + create_uid +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }

	public Integer getScreenstat() {
		return screenstat;
	}

	public void setScreenstat(Integer screenstat) {
		this.screenstat = screenstat;
	}

	public Integer getTopStatus() {
		return topStatus;
	}

	public void setTopStatus(Integer topStatus) {
		this.topStatus = topStatus;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getWatchstat() {
		return watchstat;
	}

	public void setWatchstat(Integer watchstat) {
		this.watchstat = watchstat;
	}
}
