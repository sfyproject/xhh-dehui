package com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.store.dao.GoodsMapper;
import com.store.dao.SortMapper;
import com.store.model.Goods;
import com.store.model.Sort;
import com.store.service.SortService;
@Service
public class SortServiceImpl implements SortService {
	@Autowired 
	SortMapper sortMapper;
	@Autowired
	GoodsMapper goodsMapper;

	public List<Sort> page(Sort sort) {
		return sortMapper.selectAll(sort);
	}

	public Sort selectByPrimaryKey(String sortId) {
		return sortMapper.selectByPrimaryKey(sortId);
	}

	public int save(Sort sort) {
		return sortMapper.insertSelective(sort);
	}

	public int update(Sort sort) {
		return sortMapper.updateByPrimaryKeySelective(sort);
	}
	@Transactional
	public int delete(String sortId) {
		Goods goods = new Goods();
		goods.setGoodsLabel("1");
		goods.setGoodsType("5");
		goods.setGoodsLabelt(sortId);
		goodsMapper.updateByGoodsLabel(goods);
		return sortMapper.deleteByPrimaryKey(sortId);
	}


	@Override

	public List<Sort> existEditSortName(Sort sort) {
		// TODO Auto-generated method stub
		return sortMapper.existEditSortName(sort);
	}


	@Override
	public List<Sort> selectSortName() {
		return sortMapper.selectSortName();
	}
	public List<Sort> selectSortName(String grade) {
		return sortMapper.selectSortName(grade);

	}

	@Override
	public String selectBySortNameAndParentId(Sort sort) {
		return sortMapper.selectBySortNameAndParentId(sort);
	}


}
