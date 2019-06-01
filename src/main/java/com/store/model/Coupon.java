package com.store.model;

import java.util.Date;

public class Coupon {
	
	public static final String STATUS_UNUSED = "0";
	public static final String STATUS_USED = "1";
	
	private String couponId;
	private String couponUid;
	private String couponCoin;
	private String couponStatus;
	private Date couponStart;
	private Date couponEnd;
	private Date updateTime;
	private Date createTime;

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId == null ? null : couponId.trim();
	}

	public String getCouponUid() {
		return couponUid;
	}

	public void setCouponUid(String couponUid) {
		this.couponUid = couponUid == null ? null : couponUid.trim();
	}

	public String getCouponCoin() {
		return couponCoin;
	}

	public void setCouponCoin(String couponCoin) {
		this.couponCoin = couponCoin == null ? null : couponCoin.trim();
	}

	public String getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(String couponStatus) {
		this.couponStatus = couponStatus == null ? null : couponStatus.trim();
	}

	public Date getCouponStart() {
		return couponStart;
	}

	public void setCouponStart(Date couponStart) {
		this.couponStart = couponStart;
	}

	public Date getCouponEnd() {
		return couponEnd;
	}

	public void setCouponEnd(Date couponEnd) {
		this.couponEnd = couponEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}