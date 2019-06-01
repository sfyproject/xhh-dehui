package com.store.service;

import java.util.List;

import com.store.model.Coupon;

public interface CouponService {

	List<Coupon> selectByUid(String authUserId);
	
	Coupon selectByPrimaryKey(String couponId);

	int updateByPrimaryKeySelective(Coupon coupon);

}