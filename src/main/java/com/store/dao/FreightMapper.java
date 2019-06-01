package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Freight;

public interface FreightMapper {

	int deleteByPrimaryKey(String freightId);

	int insert(Freight record);

	int insertSelective(Freight record);

	Freight selectByPrimaryKey(String freightId);

	int updateByPrimaryKeySelective(Freight record);

	int updateByPrimaryKey(Freight record);

	List<String> selectAll();

	Freight selectByTypeAndCity(@Param(value = "type") String type, @Param(value = "city") String city);

}