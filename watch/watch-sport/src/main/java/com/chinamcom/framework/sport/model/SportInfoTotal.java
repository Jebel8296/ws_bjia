package com.chinamcom.framework.sport.model;

import java.util.Date;
import java.util.List;

public class SportInfoTotal {
    private Integer id;

    private String imei;

    private Integer sportType;

    private Integer step;

    private Integer energy;

    private Integer heartRate;

    private Date uploadTime;

    private Integer distance;

    private Integer useTime;

    private Date createTime;
    
    private Integer hour;//一天24小时的时间点
    private Integer totalStep;//当日步数
    private Integer totalEnergy;//当日消耗能量
    private Integer totalDistance;//当日距离
    private Integer totalUseTime;//当日运动时间
    private Integer targetStep;//目标步数
    private long sysTime;
//	private List<SportInfo> list;
	private List<SportInfoTotal> sprotList;//每小时运动详情
	private List<SportSummary> heartRateList;//心率
	private Integer rank;//运动排名
	private Integer heartRateSum;//最新上传心率
	private Integer timeDimension;
	private Integer valDiemension;
	private Integer calorie;
	private Integer target;
	
	public Integer getHeartRateSum() {
		return heartRateSum;
	}

	public void setHeartRateSum(Integer heartRateSum) {
		this.heartRateSum = heartRateSum;
	}

	public List<SportInfoTotal> getSprotList() {
		return sprotList;
	}

	public void setSprotList(List<SportInfoTotal> sprotList) {
		this.sprotList = sprotList;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public List<SportSummary> getHeartRateList() {
		return heartRateList;
	}

	public void setHeartRateList(List<SportSummary> heartRateList) {
		this.heartRateList = heartRateList;
	}
	
	public void setSysTime(long sysTime) {
		this.sysTime = sysTime;
	}
	
	public Long getSysTime() {
		return sysTime;
	}

	public void setSysTime(Long sysTime) {
		this.sysTime = sysTime;
	}

	public Integer getTotalEnergy() {
		return totalEnergy;
	}

	public void setTotalEnergy(Integer totalEnergy) {
		this.totalEnergy = totalEnergy;
	}

	public Integer getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(Integer totalDistance) {
		this.totalDistance = totalDistance;
	}

	public Integer getTotalUseTime() {
		return totalUseTime;
	}

	public void setTotalUseTime(Integer totalUseTime) {
		this.totalUseTime = totalUseTime;
	}

	public Integer getTargetStep() {
		return targetStep;
	}

	public void setTargetStep(Integer targetStep) {
		this.targetStep = targetStep;
	}

    public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

//	public List<SportInfo> getList() {
//		return list;
//	}
//
//	public void setList(List<SportInfo> list) {
//		this.list = list;
//	}

	public Integer getTotalStep() {
		return totalStep;
	}

	public void setTotalStep(Integer totalStep) {
		this.totalStep = totalStep;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;  
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Integer getSportType() {
        return sportType;
    }

    public void setSportType(Integer sportType) {
        this.sportType = sportType;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getTimeDimension() {
		return timeDimension;
	}

	public void setTimeDimension(Integer timeDimension) {
		this.timeDimension = timeDimension;
	}

	public Integer getValDiemension() {
		return valDiemension;
	}

	public void setValDiemension(Integer valDiemension) {
		this.valDiemension = valDiemension;
	}

	public Integer getCalorie() {
		return calorie;
	}

	public void setCalorie(Integer calorie) {
		this.calorie = calorie;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}
    
}
