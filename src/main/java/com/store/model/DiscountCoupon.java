package com.store.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DiscountCoupon {

	private Long couponId;

	private String couponName;

	private Long facePrice;

	private Long payPrice;

	private String source;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;

	private Byte couponStatus;

	private Date addtime;

	private Byte couponType;

	private String swit;
	
	private String userId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public String getSwit() {
		return swit;
	}

	public void setSwit(String swit) {
		this.swit = swit;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName == null ? null : couponName.trim();
	}

	public Long getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(Long facePrice) {
		this.facePrice = facePrice;
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Long payPrice) {
		this.payPrice = payPrice;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Byte getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Byte couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Byte getCouponType() {
		return couponType;
	}

	public void setCouponType(Byte couponType) {
		this.couponType = couponType;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "DiscountCoupon [couponId=" + couponId + ", couponName=" + couponName + ", facePrice=" + facePrice
				+ ", payPrice=" + payPrice + ", source=" + source + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", couponStatus=" + couponStatus + ", addtime=" + addtime + ", couponType=" + couponType + "]";
	}
	
	
}