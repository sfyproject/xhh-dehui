package com.store.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.store.model.Admin;
import com.store.model.Goods;
import com.store.model.GoodsTree;
import com.store.model.req.PageQuery;

public interface GoodsService {

	List<String> selectGoodsTypes();

	List<Goods> selectByType(String goodsType);

	Goods selectByPrimaryKey(String goodsId);

	List<Goods> selectHighestSales();

	List<Goods> selectNewArrival();

	int insert(Goods goods);

	int insertSelective(Goods goods);

	int deleteByPrimaryKey(String storeGoodsId);

	int updateByPrimaryKeySelective(Goods goods);

	PageInfo<Goods> page(PageQuery pageQuery, Goods goods);

	List<Goods> selectByLabel(String goodsLabel);

	List<Goods> selectByGids(List<String> ids);

	int batchRemove(List<String> ids);

	PageInfo<Goods> getSellWellgoods(PageQuery pageQuery, Goods record);

	int deleteByGoodsId(String goodsId);

	boolean save(List<Goods> list, Admin admin);
	
	boolean del(List<Goods> list, Admin admin);

	Goods selectByGoodsTitle(String title);

	List<Goods> selectByTitle(String title);

	List<Goods> selectAllGoods(Goods goods);
	
	Goods selectOneGoods(Goods goods);
	
	List<GoodsTree> selectTree();

}