package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.OrderGoods;

public interface OrderGoodsMapper {

	int deleteByPrimaryKey(String ogdId);

	int insert(OrderGoods record);

	int insertSelective(OrderGoods record);

	OrderGoods selectByPrimaryKey(String ogdId);

	int updateByPrimaryKeySelective(OrderGoods record);

	int updateByPrimaryKey(OrderGoods record);

	List<OrderGoods> selectByOrderNo(@Param(value = "orderNo") String orderNo);

	int batchInsert(List<OrderGoods> orderGoodsList);

}