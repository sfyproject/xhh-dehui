package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.RechargeMapper;
import com.store.model.Recharge;
import com.store.model.req.PageQuery;
import com.store.service.RechargeService;

@Service
public class RechargeServiceImpl implements RechargeService {

	@Autowired
	private RechargeMapper rechargeMapper;

	@Override
	public List<Recharge> selectByUid(String uid) {
		return rechargeMapper.selectByUid(uid);
	}

	@Override
	public int insertSelective(Recharge recharge) {
		return rechargeMapper.insertSelective(recharge);
	}

	@Override
	public PageInfo<Recharge> page(PageQuery pageQuery, Recharge recharge) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(rechargeMapper.selectAll(recharge), 10);
	}

}