package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.InfoMapper;
import com.store.model.Info;
import com.store.model.req.PageReq;
import com.store.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;

	@Override
	public PageInfo<Info> page(PageReq pageReq) {
		PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
		List<Info> list = infoMapper.selectAll();
		return new PageInfo<>(list, 10);
	}

	@Override
	public int insert(Info info) {
		return infoMapper.insert(info);
	}

	@Override
	public int updateByPrimaryKeySelective(Info info) {
		return infoMapper.updateByPrimaryKeySelective(info);
	}

	@Override
	public int deleteByPrimaryKey(String storeInfoId) {
		return infoMapper.deleteByPrimaryKey(storeInfoId);
	}

	@Override
	public String selectCity(String storeInfoId) {
		return infoMapper.selectCity(storeInfoId);
	}

}