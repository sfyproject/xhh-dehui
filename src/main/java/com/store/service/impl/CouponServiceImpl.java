package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.CouponMapper;
import com.store.model.Coupon;
import com.store.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponMapper couponMapper;

	@Override
	public List<Coupon> selectByUid(String authUserId) {
		return couponMapper.selectByUid(authUserId);
	}

	@Override
	public int updateByPrimaryKeySelective(Coupon coupon) {
		return couponMapper.updateByPrimaryKeySelective(coupon);
	}

	@Override
	public Coupon selectByPrimaryKey(String couponId) {
		return couponMapper.selectByPrimaryKey(couponId);
	}

}