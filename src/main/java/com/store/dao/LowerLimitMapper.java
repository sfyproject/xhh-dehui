package com.store.dao;

import java.util.List;

import com.store.model.LowerLimit;

public interface LowerLimitMapper {
    int deleteByPrimaryKey(String id);

    int insert(LowerLimit record);

    int insertSelective(LowerLimit record);

    LowerLimit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LowerLimit record);

    int updateByPrimaryKey(LowerLimit record);

	List<LowerLimit> getLowerLimit(LowerLimit lowerLimit);
	
	int batchInsert(List<LowerLimit> list);
}