package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Recharge;
import com.store.model.req.PageQuery;

public interface RechargeService {

	List<Recharge> selectByUid(String uid);

	int insertSelective(Recharge recharge);

	PageInfo<Recharge> page(PageQuery pageQuery, Recharge recharge);

}