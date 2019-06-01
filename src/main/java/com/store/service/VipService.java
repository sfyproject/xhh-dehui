package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Vip;
import com.store.model.req.PageQuery;

public interface VipService {

	Vip selectByPrimaryKey(String vipId);

	Vip selectByUid(String authUserId);

	PageInfo<Vip> page(PageQuery query, Vip vip);
	
	int insertSelective(Vip record);
	
	int updateByPrimaryKeySelective(Vip record);

	int deleteByPrimaryKey(String id);

	int batchRemove(List<String> list);

}