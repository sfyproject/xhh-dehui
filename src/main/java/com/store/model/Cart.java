package com.store.model;

import java.util.Date;

public class Cart {

	private String cartId;
	private String cartUid;
	private String cartGid;
	private Integer cartNum;
	private Date createTime;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId == null ? null : cartId.trim();
	}

	public String getCartUid() {
		return cartUid;
	}

	public void setCartUid(String cartUid) {
		this.cartUid = cartUid == null ? null : cartUid.trim();
	}

	public String getCartGid() {
		return cartGid;
	}

	public void setCartGid(String cartGid) {
		this.cartGid = cartGid == null ? null : cartGid.trim();
	}

	public Integer getCartNum() {
		return cartNum;
	}

	public void setCartNum(Integer cartNum) {
		this.cartNum = cartNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}