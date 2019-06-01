package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Order;

public interface OrderMapper {

	int deleteByPrimaryKey(String orderNo);

	int insert(Order record);

	int insertSelective(Order record);

	Order selectByPrimaryKey(String orderNo);

	int updateByPrimaryKeySelective(Order record);

	int updateByPrimaryKey(Order record);

	List<Order> selectAll(Order order);
	
	Integer selectGroupCount(String gid);
	
	List<Order> selectByUidAndStatus(@Param(value = "uid") String uid, @Param(value = "status") String status, @Param(value = "orderGroup") String orderGroup);
	
	List<Order> selectReceivingAll(Order order);

	//批量删除
	Integer batchRemove(@Param(value = "list") List<String> list);

}