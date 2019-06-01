package com.store.model;

import java.util.Date;

public class Recharge {

	private String rechargeId;
	private String rechargeCoin;
	private String rechargeType;
	private String rechargeUid;
	private String rechargeStatus;
	private Date createTime;
	
	// Non-database fields
	private String userName;
	private String userPhone;
	private String startDate;
	private String endDate;

	

	public static final String STATUS_UNPAID = "0";
	public static final String STATUS_PAID = "1";

	public static final String TYPE_WECHAT = "1";
	public static final String TYPE_CARD = "2";

	public String getRechargeId() {
		return rechargeId;
	}

	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId == null ? null : rechargeId.trim();
	}

	public String getRechargeCoin() {
		return rechargeCoin;
	}

	public void setRechargeCoin(String rechargeCoin) {
		this.rechargeCoin = rechargeCoin == null ? null : rechargeCoin.trim();
	}

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public String getRechargeUid() {
		return rechargeUid;
	}

	public void setRechargeUid(String rechargeUid) {
		this.rechargeUid = rechargeUid == null ? null : rechargeUid.trim();
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus == null ? null : rechargeStatus.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}