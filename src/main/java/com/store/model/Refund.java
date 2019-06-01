package com.store.model;

import java.util.Date;

public class Refund {

	public static final String TYPE_WECHAT = "0";
	public static final String TYPE_BALANCE = "1";

	private String refundId;
	private String refundCoin;
	private String refundUid;
	private String refundType;
	private Date createTime;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId == null ? null : refundId.trim();
	}

	public String getRefundCoin() {
		return refundCoin;
	}

	public void setRefundCoin(String refundCoin) {
		this.refundCoin = refundCoin == null ? null : refundCoin.trim();
	}

	public String getRefundUid() {
		return refundUid;
	}

	public void setRefundUid(String refundUid) {
		this.refundUid = refundUid == null ? null : refundUid.trim();
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}