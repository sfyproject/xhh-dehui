package com.store.dao;

import java.util.List;

import com.store.model.DiscountCoupon;

public interface DiscountCouponMapper {

	List<DiscountCoupon> selectAll(DiscountCoupon discountCoupon);
	
	DiscountCoupon selectByPrimaryKey(Long couponId);

	int updateByPrimaryKeySelective(DiscountCoupon discountCoupon);
	
	int insert(DiscountCoupon record);

	int insertSelective(DiscountCoupon record);
	
	boolean insertCouponType(DiscountCoupon record);
	
	List<DiscountCoupon> selectAllCouponType();
	
	int deleteByPrimaryKey(Long couponId);



}