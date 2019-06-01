package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.OrderMapper;
import com.store.model.Order;
import com.store.model.req.PageQuery;
import com.store.model.req.PageReq;
import com.store.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Override
	public int insert(Order order) {
		return orderMapper.insert(order);
	}

	@Override
	public List<Order> selectByUidAndStatus(String uid, String status, String orderGroup) {
		return orderMapper.selectByUidAndStatus(uid, status, orderGroup);
	}

	@Override
	public Order selectByPrimaryKey(String orderNo) {
		return orderMapper.selectByPrimaryKey(orderNo);
	}

	@Override
	public int updateByPrimaryKeySelective(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public PageInfo<Order> page(PageReq pageReq, Order order) {
		PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
		return new PageInfo<>(orderMapper.selectAll(order), 10);
	}

	@Override
	public Integer selectGroupCount(String goodsId) {
		return orderMapper.selectGroupCount(goodsId);
	}

	@Override
	public PageInfo<Order> page(PageQuery query, Order order) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(orderMapper.selectReceivingAll(order), 10);
	}

	@Override
	public int insertSelective(Order order) {
		return orderMapper.insertSelective(order);
	}

	@Override
	public int deleteByPrimaryKey(String orderNo) {
		return orderMapper.deleteByPrimaryKey(orderNo);
	}

	@Override
	public int batchRemove(List<String> list) {
		return orderMapper.batchRemove(list);
	}

	@Override
	public PageInfo<Order> orderPage(PageQuery query, Order order) {
		PageHelper.startPage(query.getPage(), query.getLimit());
		return new PageInfo<>(orderMapper.selectAll(order), 10);
	}

}