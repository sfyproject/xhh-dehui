package com.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.GimgMapper;
import com.store.dao.GoodsMapper;
import com.store.dao.OperationRecordMapper;
import com.store.model.Admin;
import com.store.model.Goods;
import com.store.model.GoodsTree;
import com.store.model.OperationRecord;
import com.store.model.req.PageQuery;
import com.store.service.GoodsService;
import com.store.utils.Utils;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private GimgMapper gimgMapper;
	
	@Autowired
	private OperationRecordMapper operationRecordMapper;
	
	@Value("${img.path.prefix}")
	private String imgPathPrefix;

	@Value("${url.img.path.prefix}")
	private String urlImgPathPrefix;


	@Override
	public List<String> selectGoodsTypes() {
		return goodsMapper.selectGoodsTypes();
	}

	@Override
	public List<Goods> selectByType(String goodsType) {
		return goodsMapper.selectByType(goodsType);
	}

	@Override
	public Goods selectByPrimaryKey(String goodsId) {
		return goodsMapper.selectByPrimaryKey(goodsId);
	}

	@Override
	public List<Goods> selectHighestSales() {
		return goodsMapper.selectHighestSales();
	}

	@Override
	public List<Goods> selectNewArrival() {
		return goodsMapper.selectNewArrival();
	}

	@Override
	public int insert(Goods goods) {
		return goodsMapper.insert(goods);
	}

	@Override
	public int deleteByPrimaryKey(String goodsId) {
		return goodsMapper.deleteByPrimaryKey(goodsId);
	}

	@Override
	public int updateByPrimaryKeySelective(Goods goods) {
		return goodsMapper.updateByPrimaryKeySelective(goods);
	}

	@Override
	public PageInfo<Goods> page(PageQuery pageQuery, Goods goods) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(goodsMapper.selectAllbyEntity(goods), 10);
	}

	@Override
	public List<Goods> selectByLabel(String goodsLabel) {
		return goodsMapper.selectByLabel(goodsLabel);
	}

	@Override
	public List<Goods> selectByGids(List<String> ids) {
		return goodsMapper.selectByGids(ids);
	}
	@Transactional
	@Override
	public int batchRemove(List<String> ids) {

		List<String> urls = gimgMapper.batchSelect(ids);
		Integer status = goodsMapper.batchRemove(ids);
		
		if(urls != null) {
			for (String url : urls) {
				String path = imgPathPrefix;
				String PathPrefix=urlImgPathPrefix;
				int indexOf = url.indexOf(PathPrefix);
				if(indexOf!=-1) {
					String str=path+url.split(PathPrefix)[1];
					Utils.DeleteFolder(str);
				}
				
			}
		}
		return status;
	}

	@Override
	public PageInfo<Goods> getSellWellgoods(PageQuery pageQuery, Goods record) {
		PageHelper.startPage(pageQuery.getPage(), pageQuery.getLimit());
		return new PageInfo<>(goodsMapper.getSellWellgoods(record), 20);
	}
	@Transactional
	@Override
	public int deleteByGoodsId(String goodsId) {
		int status =0;
		List<String> list = gimgMapper.selectByGid(goodsId);
		 status = goodsMapper.deleteByGoodsId(goodsId);
		 if(list!=null)
			{
				for (String url : list) {
					String path = imgPathPrefix;
					String PathPrefix=urlImgPathPrefix;
					int indexOf = url.indexOf(PathPrefix);
					if(indexOf!=-1) {
						String str=path+url.split(PathPrefix)[1];
						Utils.DeleteFolder(str);
					}
				}
			}
		 return status;
	}
	
	@Override
	public int insertSelective(Goods goods) {
		return goodsMapper.insertSelective(goods);
	}

	/**
	 * 商品入库
	 */
	
	@Override
	@Transactional
	public boolean save(List<Goods> list,Admin admin) {
		List<Goods> list1 = new ArrayList<>();
		Date date= new Date();
		int row1 = 0;
		int row2 = 0;
		Goods goodsInfo = new Goods();
		for (Goods goods : list) {
			goodsInfo = goodsMapper.selectByGoodsTitle(goods.getGoodsTitle());
			if(goodsInfo!= null) {//判断商品信息是否存在，如果存在只将商品的库存修改
				goodsInfo.setGoodsInventory(goods.getGoodsInventory() + goodsInfo.getGoodsInventory());
				row1 = goodsMapper.updateByPrimaryKeySelective(goodsInfo);
				OperationRecord operationRecord = new OperationRecord();
				String operationRecordName = admin.getAdminUsername();
				operationRecord.setOperationRecordId(Utils.UUID());
				operationRecord.setOperationRecordAid(admin.getAdminId());
				operationRecord.setOperationRecordName(operationRecordName);
				operationRecord.setOperationRecordType("2");
				operationRecord.setOperationRecordGid(goodsInfo.getGoodsId());
				operationRecord.setOperationRecordGoodsnum(goods.getGoodsInventory());
				operationRecord.setCreateTime(date);
				operationRecordMapper.insertSelective(operationRecord);
			}else {//如果商品信息不存在，将新的商品插入都商品信息表中
				String goodsId = Utils.UUID();
				goods.setGoodsId(goodsId);
				goods.setCreateTime(date);
				goods.setGoodsImage("ImageUrl");
				goods.setGoodsSales(0);
				list1.add(goods);
				row2 = goodsMapper.insertSelective(goods);
				OperationRecord operationRecord = new OperationRecord();
				String operationRecordName = admin.getAdminUsername();
				operationRecord.setOperationRecordId(Utils.UUID());
				operationRecord.setOperationRecordAid(admin.getAdminId());
				operationRecord.setOperationRecordType("2");
				operationRecord.setOperationRecordGid(goodsId);
				operationRecord.setOperationRecordName(operationRecordName);
				operationRecord.setOperationRecordGoodsnum(goods.getGoodsInventory());
				operationRecord.setCreateTime(date);
				operationRecordMapper.insertSelective(operationRecord);
			}
		}
		//int insert = goodsMapper.batchInsert(list1);
		if (row1 == 0 && goodsInfo !=null) {
			return false;
		}else if(row2 == 0 && goodsInfo ==null) {
			return false;
		}else if(row1 == 0 && row2 == 0) {
			return false;
		}
			return true;
	}
	/**
	 * 商品出库
	 */
	@Override
	@Transactional
	public boolean del(List<Goods> list,Admin admin) {
		Date date= new Date();
		int row = 0;
		Goods goodsInfo = new Goods();
		for (Goods goods : list) {
			goodsInfo = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
			if(goodsInfo!= null) {//判断商品信息是否存在，如果存在只将商品的库存修改
				if(goods.getGoodsInventory()<= goodsInfo.getGoodsInventory()) {
					goodsInfo.setGoodsInventory( goodsInfo.getGoodsInventory() - goods.getGoodsInventory());
					goodsInfo.setGoodsSales(goodsInfo.getGoodsSales() + goods.getGoodsInventory());
					row = goodsMapper.updateByPrimaryKeySelective(goodsInfo);
					OperationRecord operationRecord = new OperationRecord();
					String operationRecordName = admin.getAdminUsername();
					operationRecord.setOperationRecordId(Utils.UUID());
					operationRecord.setOperationRecordAid(admin.getAdminId());
					operationRecord.setOperationRecordName(operationRecordName);
					operationRecord.setOperationRecordType("1");
					operationRecord.setOperationRecordGid(goodsInfo.getGoodsId());
					operationRecord.setOperationRecordGoodsnum(goods.getGoodsInventory());
					operationRecord.setCreateTime(date);
					operationRecordMapper.insertSelective(operationRecord);
				}else {
					return false;
				}
			}
		}
		if (row == 0 || goodsInfo == null) {
			return false;
		}
			return true;
	}
	
	@Override
	public List<Goods> selectByTitle(String title) {
		return goodsMapper.selectByTitle(title);
	}

	@Override
	public Goods selectByGoodsTitle(String title) {
		return goodsMapper.selectByGoodsTitle(title);
	}

	@Override
	public List<Goods> selectAllGoods(Goods goods) {
		
		return goodsMapper.selectAll(goods);
	}

	@Override
	public Goods selectOneGoods(Goods goods) {
		
		return goodsMapper.selectOneGoods(goods);
	}

	@Override
	public List<GoodsTree> selectTree() {
		return goodsMapper.selectTree();
	}

}