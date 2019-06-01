package com.store.model;

public class OrderGoods extends Goods {

	private String ogdId;
	private String ogdGid;
	private Integer ogdGnum;
	private String ogdNo;

	public String getOgdId() {
		return ogdId;
	}

	public void setOgdId(String ogdId) {
		this.ogdId = ogdId == null ? null : ogdId.trim();
	}

	public String getOgdGid() {
		return ogdGid;
	}

	public void setOgdGid(String ogdGid) {
		this.ogdGid = ogdGid == null ? null : ogdGid.trim();
	}

	public Integer getOgdGnum() {
		return ogdGnum;
	}

	public void setOgdGnum(Integer ogdGnum) {
		this.ogdGnum = ogdGnum;
	}

	public String getOgdNo() {
		return ogdNo;
	}

	public void setOgdNo(String ogdNo) {
		this.ogdNo = ogdNo == null ? null : ogdNo.trim();
	}

}