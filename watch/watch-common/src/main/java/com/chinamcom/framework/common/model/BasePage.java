package com.chinamcom.framework.common.model;

/**
 * 分页基类
 * @author fattiger.xiaoyang
 * @date 2016/08/02
 */
public class BasePage {
	
	private Integer pageNo;
	private Integer pageSize;
	
	public Integer getBeforeCount() {
		return (pageNo - 1) * pageSize;
	}
	public void setBeforeCount(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
