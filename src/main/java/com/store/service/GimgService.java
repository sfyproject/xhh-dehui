package com.store.service;

import java.util.List;

import com.store.model.Gimg;

public interface GimgService {

	List<String> selectByGid(String goodsId);
	
	List<Gimg> selectGimgListByGid(String goodsId);
	
	int insertSelective(Gimg record);
	
	int deleteByPrimaryKey(String gimgId);

}