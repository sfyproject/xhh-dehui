package com.store.model;

import java.util.Date;

public class Gimg {

	private String gimgId;
	private String gimgUrl;
	private String gimgGid;
	private Date createTime;

	public String getGimgId() {
		return gimgId;
	}

	public void setGimgId(String gimgId) {
		this.gimgId = gimgId == null ? null : gimgId.trim();
	}

	public String getGimgUrl() {
		return gimgUrl;
	}

	public void setGimgUrl(String gimgUrl) {
		this.gimgUrl = gimgUrl == null ? null : gimgUrl.trim();
	}

	public String getGimgGid() {
		return gimgGid;
	}

	public void setGimgGid(String gimgGid) {
		this.gimgGid = gimgGid == null ? null : gimgGid.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}