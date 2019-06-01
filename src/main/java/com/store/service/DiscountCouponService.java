package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.DiscountCoupon;
import com.store.model.req.PageQuery;

public interface DiscountCouponService {

	/**
	 * 分页查询
	 * 
	 * @param pageReq
	 * @param account
	 * @return
	 */
	PageInfo<DiscountCoupon> page(PageQuery pageQuery, DiscountCoupon discountCoupon);

	/**
	 * 修改账户信息
	 * 
	 * @param acco
	 * @return
	 */
	int updateByPrimaryKeySelective(DiscountCoupon discountCoupon);



	/**
	 * 通过主键查询优惠券
	 * 
	 * @param userId
	 * @return
	 */
	DiscountCoupon selectByPrimaryKey(Long couponId);
	
	int deleteByCouponId(Long goodsId);

	boolean saveCouponType(DiscountCoupon discountCoupon);
	
	List<DiscountCoupon> getAllCouponType();
	
	int save(DiscountCoupon discountCoupon);

}