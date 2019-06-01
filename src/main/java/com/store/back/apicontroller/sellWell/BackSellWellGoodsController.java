package com.store.back.apicontroller.sellWell;

import java.util.List;
import java.util.Map;

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
import com.store.back.apicontroller.recommend.BackRecommendGoodsController;
import com.store.model.Goods;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GoodsService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/sellWell")
public class BackSellWellGoodsController {
	Logger log = LoggerFactory.getLogger(BackRecommendGoodsController.class);

	@Autowired
	private GoodsService goodsService;

	@RequiresPermissions("back:api:sellWell:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Goods goods, HttpServletResponse resp) {
		log.error("/back/api/sellWell/update.json : \n");
		log.error("Goods = " + goods + "\n");
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
		log.error("/back/api/sellWell/page.json : \n");
		log.error("params = " + params + "\n");
		// 查询列表数据
		Goods goods= JSON.parseObject(JSON.toJSONString(params), Goods.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Goods> pageInfo = goodsService.getSellWellgoods(query, goods);
		List<Goods> adminList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(adminList, total);
		return pageUtil;
	}

}
