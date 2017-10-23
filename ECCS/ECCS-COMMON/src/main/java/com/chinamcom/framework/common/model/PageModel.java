package com.chinamcom.framework.common.model;

import com.github.pagehelper.Page;

public class PageModel {

	private int pageNum;
	private int pageSize;
	private int startRow;
	private int endRow;
	private long total;
	private int pages;
	private Object data;

	public PageModel(int pageNum, int pageSize, int startRow, int endRow, long total, int pages, Object data) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.startRow = startRow;
		this.endRow = endRow;
		this.total = total;
		this.pages = pages;
		this.data = data;
	}

	public PageModel(Page<Object> page, Object data) {
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.startRow = page.getStartRow();
		this.endRow = page.getEndRow();
		this.total = page.getTotal();
		this.pages = page.getPages();
		this.data = data;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
