package com.store.model.req;

import java.util.Date;

public class VipPaymentReq {

	private String openId;
	private String totalFee;
	private String notifyUrl;
	private Double vipDiscount;
	private String vipType;
	private Date vipEnd;

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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
		this.vipType = vipType;
	}

	public Date getVipEnd() {
		return vipEnd;
	}

	public void setVipEnd(Date vipEnd) {
		this.vipEnd = vipEnd;
	}

}