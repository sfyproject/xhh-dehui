package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.LowerLimitMapper;
import com.store.model.LowerLimit;
import com.store.model.req.PageQuery;
import com.store.service.LowerLimitService;
@Service
public class LowerLimitServiceImpl implements LowerLimitService {
	@Autowired
	LowerLimitMapper lowerLimitMapper;

	@Override
	public List<LowerLimit> page() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfo<LowerLimit> page(PageQuery pageQuery, LowerLimit lowerLimit) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(lowerLimitMapper.getLowerLimit(lowerLimit), 10);
	}

	@Override
	public int save(LowerLimit lowerLimit) {
		// TODO Auto-generated method stub
		return lowerLimitMapper.insertSelective(lowerLimit);
	}

	@Override
	public int batchInsert(List<LowerLimit> list) {
		// TODO Auto-generated method stub
		return lowerLimitMapper.batchInsert(list);
	}

}
