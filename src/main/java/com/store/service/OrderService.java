package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Order;
import com.store.model.req.PageQuery;
import com.store.model.req.PageReq;

public interface OrderService {

	int insert(Order order);

	List<Order> selectByUidAndStatus(String uid, String status, String orderGroup);

	Order selectByPrimaryKey(String orderNo);

	int updateByPrimaryKeySelective(Order order);

	PageInfo<Order> page(PageReq pageReq, Order order);

	Integer selectGroupCount(String goodsId);

	PageInfo<Order> page(PageQuery query, Order order);
	
	PageInfo<Order> orderPage(PageQuery query, Order order);

	int insertSelective(Order order);

	int deleteByPrimaryKey(String orderNo);

	int batchRemove(List<String> list);

}