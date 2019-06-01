package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.DiscountCouponMapper;
import com.store.model.DiscountCoupon;
import com.store.model.req.PageQuery;
import com.store.service.DiscountCouponService;

@Service
public class DiscountCouponServiceImpl implements DiscountCouponService {

	@Autowired
	private DiscountCouponMapper discountCouponMapper;


	@Override
	public PageInfo<DiscountCoupon> page(PageQuery pageQuery, DiscountCoupon discountCoupon) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(discountCouponMapper.selectAll(discountCoupon), 10);
	}

	@Override
	public int updateByPrimaryKeySelective(DiscountCoupon discountCoupon) {
		return discountCouponMapper.updateByPrimaryKeySelective(discountCoupon);
	}


	@Override
	public DiscountCoupon selectByPrimaryKey(Long couponId) {
		return discountCouponMapper.selectByPrimaryKey(couponId);
	}

	@Override
	public int deleteByCouponId(Long couponId) {
		// TODO Auto-generated method stub
		return discountCouponMapper.deleteByPrimaryKey(couponId);
	}

	@Override
	public boolean saveCouponType(DiscountCoupon discountCoupon) {
		
		return discountCouponMapper.insertCouponType(discountCoupon);
	}

	@Override
	public List<DiscountCoupon> getAllCouponType() {
		// TODO Auto-generated method stub
		return discountCouponMapper.selectAllCouponType();
	}

	@Override
	public int save(DiscountCoupon discountCoupon) {
		return discountCouponMapper.insertSelective(discountCoupon);
	}

}
