package com.store.model;

import java.util.Date;
import java.util.List;

public class Order {

	public static final String STATUS_REFUNDED = "0";
	public static final String STATUS_UNPAID = "1";
	public static final String STATUS_PAID = "2";
	public static final String STATUS_SHIPPED = "3";
	public static final String STATUS_COMPLETED = "4";
	
	public static final String NO_GROUP = "0";
	public static final String IS_GROUP = "1";
	
	public static final String GROUP_STATUS_NO = "0";
	public static final String GROUP_STATUS_OK = "1";

	private String orderNo;
	private String orderPrice;
	private String orderStatus;
	private String orderUid;
	private String orderName;
	private String orderPhone;
	private String orderAddress;
	private String orderFare;
	private String orderGroup;
	private String groupStatus;
	private String orderDelivery;
	private Date updateTime;
	private Date createTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice == null ? null : orderPrice.trim();
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus == null ? null : orderStatus.trim();
	}

	public String getOrderUid() {
		return orderUid;
	}

	public void setOrderUid(String orderUid) {
		this.orderUid = orderUid == null ? null : orderUid.trim();
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}

	public String getOrderFare() {
		return orderFare;
	}

	public void setOrderFare(String orderFare) {
		this.orderFare = orderFare;
	}

	public String getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(String orderGroup) {
		this.orderGroup = orderGroup;
	}

	public String getGroupStatus() {
		return groupStatus;
	}

	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}

	public String getOrderDelivery() {
		return orderDelivery;
	}

	public void setOrderDelivery(String orderDelivery) {
		this.orderDelivery = orderDelivery;
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

	// Non-database fields
	private List<OrderGoods> goodsList;
	private List<String> ids;
	private Integer goodsWeight;
	private Integer num;
	private String code;
	private String remark;
	private String password;
	private String startDate;
	private String endDate;
	private String userName;
	private String userPhone;
	private String couponId;
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public List<OrderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getGoodsWeight() {
		return goodsWeight;
	}

	public void setGoodsWeight(Integer goodsWeight) {
		this.goodsWeight = goodsWeight;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}