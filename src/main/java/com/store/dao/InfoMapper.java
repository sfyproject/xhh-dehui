package com.store.dao;

import java.util.List;

import com.store.model.Info;

public interface InfoMapper {

	int deleteByPrimaryKey(String infoId);

	int insert(Info record);

	int insertSelective(Info record);

	Info selectByPrimaryKey(String infoId);

	int updateByPrimaryKeySelective(Info record);

	int updateByPrimaryKey(Info record);

	String selectCity(String infoId);

	List<Info> selectAll();

}