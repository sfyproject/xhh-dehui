package com.store.model.req;

public class PageReq {

	private Integer page;
	private Integer size;

	public Integer getPage() {
		if (null == page) {
			return 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		if (null == size) {
			return 20;
		}
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}