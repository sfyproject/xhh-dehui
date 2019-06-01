package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Refund;

public interface RefundMapper {

	int deleteByPrimaryKey(String refundId);

	int insert(Refund record);

	int insertSelective(Refund record);

	Refund selectByPrimaryKey(String refundId);

	int updateByPrimaryKeySelective(Refund record);

	int updateByPrimaryKey(Refund record);

	List<Refund> selectByUid(@Param(value = "uid") String uid);

}