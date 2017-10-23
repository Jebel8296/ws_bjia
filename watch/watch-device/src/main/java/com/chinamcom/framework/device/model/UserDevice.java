package com.chinamcom.framework.device.model;

/**
 * Created by Administrator on 2016/8/11.
 */
public class UserDevice {
    private Integer id;
    private Integer uid;
    private String did;
    private Integer status;
    private String server;
    private String device_type;
    private String apple_id;
    private String env;

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

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getApple_id() {
        return apple_id;
    }

    public void setApple_id(String apple_id) {
        this.apple_id = apple_id;
    }

    public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	@Override
    public String toString() {
        return "UserDevice{" +
                "id=" + id +
                ", uid=" + uid +
                ", did='" + did + '\'' +
                ", status=" + status +
                ", server='" + server + '\'' +
                ", device_type='" + device_type + '\'' +
                ", apple_id='" + apple_id + '\'' +
                '}';
    }
}
