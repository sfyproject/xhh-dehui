package com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.ExpressMapper;
import com.store.model.Express;
import com.store.model.req.PageReq;
import com.store.service.ExpressService;

@Service
public class ExpressServiceImpl implements ExpressService {

	@Autowired
	private ExpressMapper expressMapper;

	@Override
	public int insert(Express express) {
		return expressMapper.insert(express);
	}

	@Override
	public Express selectByPrimaryKey(String expressId) {
		return expressMapper.selectByPrimaryKey(expressId);
	}

	@Override
	public int deleteByPrimaryKey(String expressId) {
		return expressMapper.deleteByPrimaryKey(expressId);
	}

	@Override
	public PageInfo<Express> page(PageReq pageReq, Express express) {
		PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
		return new PageInfo<>(expressMapper.selectAll(express), 10);
	}

	@Override
	public int updateByPrimaryKeySelective(Express express) {
		return expressMapper.updateByPrimaryKeySelective(express);
	}

}