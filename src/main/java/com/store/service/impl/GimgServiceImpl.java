package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.GimgMapper;
import com.store.model.Gimg;
import com.store.service.GimgService;

@Service
public class GimgServiceImpl implements GimgService {

	@Autowired
	private GimgMapper gimgMapper;

	@Override
	public List<String> selectByGid(String goodsId) {
		return gimgMapper.selectByGid(goodsId);
	}

	@Override
	public List<Gimg> selectGimgListByGid(String goodsId) {
	
		return gimgMapper.selectGimgListByGid(goodsId);
	}

	@Override
	public int insertSelective(Gimg record) {
		// TODO Auto-generated method stub
		return gimgMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String gimgId) {
		// TODO Auto-generated method stub
		return gimgMapper.deleteByPrimaryKey(gimgId);
	}

}