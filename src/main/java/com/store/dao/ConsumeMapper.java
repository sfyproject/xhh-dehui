package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Consume;

public interface ConsumeMapper {

	int deleteByPrimaryKey(String consumeId);

	int insert(Consume record);

	int insertSelective(Consume record);

	Consume selectByPrimaryKey(String consumeId);

	int updateByPrimaryKeySelective(Consume record);

	int updateByPrimaryKey(Consume record);

	List<Consume> selectByUid(@Param(value = "uid") String uid);

	List<Consume> selectAll(Consume consume);

}