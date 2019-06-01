package com.store.model.req;

import java.util.List;

public class IdsReq<T> {

	private List<T> ids;

	public List<T> getIds() {
		return ids;
	}

	public void setIds(List<T> ids) {
		this.ids = ids;
	}

}