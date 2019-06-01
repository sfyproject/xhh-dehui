package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.LowerLimit;
import com.store.model.req.PageQuery;

public interface LowerLimitService {
	List<LowerLimit> page();
	PageInfo<LowerLimit> page(PageQuery pageQuery, LowerLimit lowerLimit);
	
	int save(LowerLimit lowerLimit);
	
	int batchInsert(List<LowerLimit> list);
}
