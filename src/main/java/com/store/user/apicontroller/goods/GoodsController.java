package com.store.user.apicontroller.goods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.model.Goods;
import com.store.model.Sort;
import com.store.model.req.PageReq;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GimgService;
import com.store.service.GoodsService;
import com.store.service.SortService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GimgService gimgService;

	@Autowired
	private SortService sortService;

	/**
	 * 全部商品类型
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public JSONObject types(HttpServletRequest req, String grade) {
		try {
			List<Sort> sort = sortService.selectSortName(grade);
			if (sort == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, sort, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 通过商品类型查询商品
	 * 
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/selectByType", method = RequestMethod.POST)
	public JSONObject selectByType(HttpServletRequest req, @RequestBody Sort sort) {
		try {
			PageReq pageReq = new PageReq();
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			if (Utils.isNullStr(sort.getSortName()) || Utils.isNullStr(sort.getParentId())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "类型为空");
			}
			String sortId = sortService.selectBySortNameAndParentId(sort);
			List<Goods> list = goodsService.selectByType(sortId);
			if (Utils.isNullList(list)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				for (Goods g : list) {
					g.setGoodsImage(gimgService.selectByGid(g.getGoodsId()).get(0));
				}
				PageInfo<Goods> pageInfo = new PageInfo<>(list, 10);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 通过商品标题查询商品
	 * 
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/selectByTitle", method = RequestMethod.POST)
	public JSONObject selectByTitle(HttpServletRequest req, @RequestBody Goods goods) {
		try {
			PageReq pageReq = new PageReq();
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			String title = goods.getGoodsTitle();
			if (Utils.isNullStr(title)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "类型为空");
			}
			List<Goods> list = goodsService.selectByTitle(title);
			if (Utils.isNullList(list)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				for (Goods g : list) {
					g.setGoodsImage(gimgService.selectByGid(g.getGoodsId()).get(0));
				}
				PageInfo<Goods> pageInfo = new PageInfo<>(list, 10);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 通过商品标签查询商品
	 * 
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/selectByLabel", method = RequestMethod.POST)
	public JSONObject selectByLabel(HttpServletRequest req, @RequestBody Sort sort) {
		try {
			PageReq pageReq = new PageReq();
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			if (Utils.isNullStr(sort.getSortName()) || Utils.isNullStr(sort.getParentId())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "标签为空");
			}
			String sortId = sortService.selectBySortNameAndParentId(sort);
			List<Goods> list = goodsService.selectByLabel(sortId);
			if (list == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				for (Goods g : list) {
					g.setGimgs(gimgService.selectByGid(g.getGoodsId()));
				}
				PageInfo<Goods> pageInfo = new PageInfo<>(list, 10);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 商品详情
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public JSONObject detail(HttpServletRequest req, @RequestBody Goods goods) {
		try {
			Goods detail = goodsService.selectByPrimaryKey(goods.getGoodsId());
			List<String> imgs = gimgService.selectByGid(goods.getGoodsId());
			if (detail == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				detail.setGimgs(imgs);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, detail, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 销量前十商品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/highestSales", method = RequestMethod.GET)
	public JSONObject highestSales(HttpServletRequest req, HttpServletResponse resp) {
		try {
			List<Goods> list = goodsService.selectHighestSales();
			for (Goods goods : list) {
				List<String> images = gimgService.selectByGid(goods.getGoodsId());
				if (!Utils.isNullList(images)) {
					goods.setGoodsImage(images.get(0));
				}
			}
			if (list == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 最新上架商品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/newArrival", method = RequestMethod.GET)
	public JSONObject newArrival(HttpServletRequest req, HttpServletResponse resp) {
		try {
			List<Goods> list = goodsService.selectNewArrival();
			for (Goods goods : list) {
				List<String> images = gimgService.selectByGid(goods.getGoodsId());
				if (!Utils.isNullList(images)) {
					goods.setGoodsImage(images.get(0));
				}
			}
			if (list == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}