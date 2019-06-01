package com.store.model;

import java.util.Date;

public class Info {

	private String infoId;
	private String infoCity;
	private String infoName;
	private String infoAddress;
	private Date createTime;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId == null ? null : infoId.trim();
	}

	public String getInfoCity() {
		return infoCity;
	}

	public void setInfoCity(String infoCity) {
		this.infoCity = infoCity == null ? null : infoCity.trim();
	}

	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String infoName) {
		this.infoName = infoName == null ? null : infoName.trim();
	}

	public String getInfoAddress() {
		return infoAddress;
	}

	public void setInfoAddress(String infoAddress) {
		this.infoAddress = infoAddress == null ? null : infoAddress.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}