package com.store.service;

import java.util.List;

import com.store.model.OrderGoods;

public interface OrderGoodsService {

	List<OrderGoods> selectByOrderNo(String orderNo);

	int batchInsert(List<OrderGoods> orderGoodsList);

}