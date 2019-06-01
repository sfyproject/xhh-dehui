package com.store.model;

import java.util.Date;

public class Account {

	private String acctId;

	private String userId;

	private String balance;

	private Date addtime;

	private String userName;

	private String userPhone;
	
	private Date createDate;
	private String startDate;
	
	private String endDate;
	
	private String rechargeMoney;


	
	public String getAcctId() {
		return acctId;
	}



	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getBalance() {
		return balance;
	}



	public void setBalance(String balance) {
		this.balance = balance;
	}



	public Date getAddtime() {
		return addtime;
	}



	public void setAddtime(Date addtime) {
		this.addtime = addtime;
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



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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



	public String getRechargeMoney() {
		return rechargeMoney;
	}



	public void setRechargeMoney(String rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}



	@Override
	public String toString() {
		return "Account [acctId=" + acctId + ", userId=" + userId + ", balance=" + balance + ", addtime=" + addtime
				+ ", userName=" + userName + ", userPhone=" + userPhone + "]";
	}
	
}