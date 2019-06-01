package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Gimg;

public interface GimgMapper {

	int deleteByPrimaryKey(String gimgId);

	int insert(Gimg record);

	int insertSelective(Gimg record);

	Gimg selectByPrimaryKey(String gimgId);

	int updateByPrimaryKeySelective(Gimg record);

	int updateByPrimaryKey(Gimg record);

	List<String> selectByGid(@Param(value = "goodsId") String goodsId);

	List<Gimg> selectGimgListByGid(String goodsId);
	
	List<String>  batchSelect(@Param(value = "list") List<String> list);

}