package com.store.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.store.utils.Utils;

public class Vip {

	public static final String STATUS_UNPAID = "0";
	public static final String STATUS_PAID = "1";

	private String vipId;
	private String vipUid;
	private String vipPrice;
	private Double vipDiscount;
	private String vipType;
	private String vipStatus;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date vipStart;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date vipEnd;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId == null ? null : vipId.trim();
	}

	public String getVipUid() {
		return vipUid;
	}

	public void setVipUid(String vipUid) {
		this.vipUid = vipUid == null ? null : vipUid.trim();
	}

	public String getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(String vipPrice) {
		this.vipPrice = vipPrice == null ? null : vipPrice.trim();
	}

	public Double getVipDiscount() {
		return vipDiscount;
	}

	public void setVipDiscount(Double vipDiscount) {
		this.vipDiscount = vipDiscount;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType == null ? null : vipType.trim();
	}

	public String getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}

	public Date getVipStart() {
		return vipStart;
	}

	public void setVipStart(Date vipStart) {
		this.vipStart = vipStart;
	}

	public Date getVipEnd() {
		return vipEnd;
	}

	public void setVipEnd(Date vipEnd) {
		this.vipEnd = vipEnd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	// Non-database fields
	private String remind;
	private String userName;
	private String userPhone;
	private String term;
	private List<User> list;
	private String startDate;
	private String endDate;

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPhone() {
		return userPhone;
	}
	
	public String getTerm() {
		return term;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
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
	

}