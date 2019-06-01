package com.store.back.apicontroller.recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import com.store.model.Goods;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GoodsService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/recommend")
public class BackRecommendGoodsController {
	Logger log = LoggerFactory.getLogger(BackRecommendGoodsController.class);

	@Autowired
	private GoodsService goodsService;
	
	

	/**
	 * 增
	 * 
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequiresPermissions("back:api:recommend:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(HttpServletRequest req,Goods goods) {
		try {
			goods.setGoodsId(Utils.UUID());
			int row = goodsService.insert(goods);
			if (row == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品添加失败");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "商品添加成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:recommend:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.error("/back/api/recommend/delete.json : \n");
		log.error("id = " + id + "\n");
		try {
			int status=goodsService.deleteByGoodsId(id);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	/**
	 * 批量删
	 * 
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("back:api:recommend:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.error("/back/api/recommend/batchRemove.json : \n");
		log.error("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
			}
			int status=goodsService.batchRemove(list);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "删除成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

	/**
	 * 改
	 * 
	 * @param goods
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:recommend:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Goods goods, HttpServletResponse resp) {
		log.info("/back/api/recommend/update.json : \n");
		log.info("Goods = " + goods + "\n");
		try {
			int row = goodsService.updateByPrimaryKeySelective(goods);
			if (row == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "修改失败");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}

	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param goods
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.error("/back/api/recommend/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		Goods goods= JSON.parseObject(JSON.toJSONString(params), Goods.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Goods> pageInfo = goodsService.page(query, goods);
		List<Goods> adminList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(adminList, total);
		return pageUtil;
	}
	
	
}