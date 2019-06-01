package com.store.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.store.model.Goods;
import com.store.model.GoodsTree;

public interface GoodsMapper {

	int deleteByPrimaryKey(String goodsId);

	int insert(Goods record);

	int insertSelective(Goods record);

	//通过goodsId查询商品信息
	Goods selectByPrimaryKey(String goodsId);

	int updateByPrimaryKeySelective(Goods record);

	int updateByPrimaryKey(Goods record);

	List<String> selectGoodsTypes();

	List<Goods> selectByType(String goodsType);

	List<Goods> selectHighestSales();

	List<Goods> selectNewArrival();

	List<Goods> selectAll(Goods goods);

	List<Goods> selectByLabel(String goodsLabel);

	List<Goods> selectByGids(@Param(value = "ids") List<String> ids);
	//批量修改
	Integer updateBatch(List<Goods> list);
	//批量删除
	Integer batchRemove(@Param(value = "list") List<String> list);
	
	List<Goods> getSellWellgoods(Goods record);
	
	int deleteByGoodsId(String goodsId);

	//wxy查询所有
	List<Goods> selectAllbyEntity(Goods goods);

	//批量插入
	int batchInsert(List<Goods> list);

	//通过商品名称查询商品信息
	Goods selectByGoodsTitle(String goodsTitle);

	//wxy根据标签修改
	int updateByGoodsLabel(Goods record);
	
	//cj根据商品名称查询商品信息
	List<Goods> selectByTitle(String title);
	
	//cj 查询商品的信息
	Goods selectOneGoods(Goods goods);
	
	//wxy 商品树
		List<GoodsTree> selectTree();
}