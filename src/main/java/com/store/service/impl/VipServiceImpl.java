package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.VipMapper;
import com.store.model.Vip;
import com.store.model.req.PageQuery;
import com.store.service.VipService;

@Service
public class VipServiceImpl implements VipService {

	@Autowired
	private VipMapper vipMapper;

	@Override
	public Vip selectByPrimaryKey(String vipId) {
		return vipMapper.selectByPrimaryKey(vipId);
	}

	@Override
	public Vip selectByUid(String authUserId) {
		return vipMapper.selectByUid(authUserId);
	}

	@Override
	public PageInfo<Vip> page(PageQuery query, Vip vip) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(vipMapper.selectAll(vip), 10);
	}

	@Override
	public int insertSelective(Vip record) {
		// TODO Auto-generated method stub
		return vipMapper .insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Vip record) {
		// TODO Auto-generated method stub
		return vipMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return vipMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int batchRemove(List<String> list) {
		// TODO Auto-generated method stub
		return vipMapper.batchRemove(list);
	}

}