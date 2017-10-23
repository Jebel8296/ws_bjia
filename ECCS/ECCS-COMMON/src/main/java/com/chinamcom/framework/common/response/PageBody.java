package com.chinamcom.framework.common.response;

public class PageBody extends RespBody {

	private Object pageData;

	public PageBody(String sn) {
		super(sn);
	}

	public Object getPageData() {
		return pageData;
	}

	public void setPageData(Object pageData) {
		this.pageData = pageData;
	}

}
