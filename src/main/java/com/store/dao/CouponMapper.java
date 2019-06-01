package com.store.dao;

import java.util.List;

import com.store.model.Coupon;

public interface CouponMapper {

	int deleteByPrimaryKey(String couponId);

	int insert(Coupon record);

	int insertSelective(Coupon record);

	Coupon selectByPrimaryKey(String couponId);

	int updateByPrimaryKeySelective(Coupon record);

	int updateByPrimaryKey(Coupon record);

	List<Coupon> selectByUid(String couponUid);

}