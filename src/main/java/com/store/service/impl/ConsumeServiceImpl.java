package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.ConsumeMapper;
import com.store.model.Consume;
import com.store.model.req.PageQuery;
import com.store.service.ConsumeService;

@Service
public class ConsumeServiceImpl implements ConsumeService {

	@Autowired
	private ConsumeMapper consumeMapper;

	@Override
	public List<Consume> selectByUid(String uid) {
		return consumeMapper.selectByUid(uid);
	}

	@Override
	public int insertSelective(Consume consume) {
		return consumeMapper.insertSelective(consume);
	}

	@Override
	public PageInfo<Consume> page(PageQuery pageQuery, Consume consume) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(consumeMapper.selectAll(consume), 10);
	}

}