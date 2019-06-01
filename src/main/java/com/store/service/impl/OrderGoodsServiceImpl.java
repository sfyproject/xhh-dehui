package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store.dao.OrderGoodsMapper;
import com.store.model.OrderGoods;
import com.store.service.OrderGoodsService;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {

	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	@Override
	public List<OrderGoods> selectByOrderNo(String orderNo) {
		return orderGoodsMapper.selectByOrderNo(orderNo);
	}

	@Override
	public int batchInsert(List<OrderGoods> orderGoodsList) {
		return orderGoodsMapper.batchInsert(orderGoodsList);
	}

}