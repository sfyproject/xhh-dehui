package com.store.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.utils.Utils;

/**
 * 用户实体类
 *
 * @sfy
 */
public class User {

	private String userId;
	private String userOpenid;
	private String userSessionkey;
	private String userName;
	private String userPhone;
	private String userWallet;
	private String userAddress;
	private String userPassword;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String startDate;
	private String endDate;
	
	private String rechargeMoney;

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(String userWallet) {
		this.userWallet = userWallet;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getUserOpenid() {
		return userOpenid;
	}

	public void setUserOpenid(String userOpenid) {
		this.userOpenid = userOpenid == null ? null : userOpenid.trim();
	}

	public String getUserSessionkey() {
		return userSessionkey;
	}

	public void setUserSessionkey(String userSessionkey) {
		this.userSessionkey = userSessionkey == null ? null : userSessionkey.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress == null ? null : userAddress.trim();
	}

	public String getStartDate() {
		if (Utils.isNullStr(startDate)) {
			return startDate;
		}
		return startDate + " 00:00:00";
	}

	public String getEndDate() {
		if (Utils.isNullStr(endDate)) {
			return endDate;
		}
		return endDate + " 23:59:59";
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	
}