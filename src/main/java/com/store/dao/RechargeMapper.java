package com.store.dao;

import java.util.List;

import com.store.model.Recharge;

public interface RechargeMapper {

	int deleteByPrimaryKey(String rechargeId);

	int insert(Recharge record);

	int insertSelective(Recharge record);

	Recharge selectByPrimaryKey(String rechargeId);

	int updateByPrimaryKeySelective(Recharge record);

	int updateByPrimaryKey(Recharge record);

	List<Recharge> selectByUid(String uid);

	List<Recharge> selectAll(Recharge recharge);

}