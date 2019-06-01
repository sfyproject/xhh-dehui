package com.store.back.apicontroller.spike;

import java.util.ArrayList;
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
import com.store.back.apicontroller.undercarriage.BackUndercarriageController;
import com.store.model.Goods;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.GoodsService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/spike")
public class BackSplikeController {
	Logger log = LoggerFactory.getLogger(BackUndercarriageController.class);

	@Autowired
	private GoodsService goodsService;

	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:spike:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.info("/back/api/spike/delete.json : \n");
		log.info("id = " + id + "\n");
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
	@RequiresPermissions("back:api:spike:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.info("/back/api/spike/batchRemove.json : \n");
		log.info("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
				System.out.println("数组====="+id);
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
	@RequiresPermissions("back:api:spike:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Goods goods, HttpServletResponse resp) {
		log.info("/back/api/spike/update.json : \n");
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
		log.info("/back/api/spike/page.json : \n");
		log.info("params = " + params + "\n");
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
