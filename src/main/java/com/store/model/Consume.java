package com.store.model;

import java.util.Date;

public class Consume {

	private String consumeId;
	private String consumeNo;
	private String consumeCoin;
	private String consumeUid;
	private Date createTime;

	// Non-database fields
	private String userName;
	private String userPhone;
	private String startDate;
	private String endDate;
	public String getConsumeId() {
		return consumeId;
	}

	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId == null ? null : consumeId.trim();
	}

	public String getConsumeNo() {
		return consumeNo;
	}

	public void setConsumeNo(String consumeNo) {
		this.consumeNo = consumeNo == null ? null : consumeNo.trim();
	}

	public String getConsumeCoin() {
		return consumeCoin;
	}

	public void setConsumeCoin(String consumeCoin) {
		this.consumeCoin = consumeCoin == null ? null : consumeCoin.trim();
	}

	public String getConsumeUid() {
		return consumeUid;
	}

	public void setConsumeUid(String consumeUid) {
		this.consumeUid = consumeUid == null ? null : consumeUid.trim();
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}