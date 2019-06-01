package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Vip;

public interface VipMapper {
	
	int deleteByPrimaryKey(String vipId);

	int insert(Vip record);

	int insertSelective(Vip record);

	Vip selectByPrimaryKey(String vipId);

	int updateByPrimaryKeySelective(Vip record);

	int updateByPrimaryKey(Vip record);

	Vip selectByUid(String vipUid);
	
	List<Vip> selectAll(Vip vip);

		//批量删除
	Integer batchRemove(@Param(value = "list") List<String> list);
	
}