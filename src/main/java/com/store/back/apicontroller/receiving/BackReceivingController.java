package com.store.back.apicontroller.receiving;

import java.util.ArrayList;
import java.util.Date;
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
import com.store.model.Order;
import com.store.model.req.PageQuery;
import com.store.model.resp.GatewayProtocol;
import com.store.service.OrderService;
import com.store.utils.PageUtils;
import com.store.utils.Utils;

@RestController
@RequestMapping("/back/api/receiving")
public class BackReceivingController {
Logger log = LoggerFactory.getLogger(BackReceivingController.class);
	
	@Autowired
	private OrderService orderService;

	/**
	 * 查
	 * 
	 * @param PageQuery
	 * @param Vip
	 * @return
	 */
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/receiving/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		Order order= JSON.parseObject(JSON.toJSONString(params), Order.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Order> pageInfo = orderService.page(query, order);
		List<Order> orderList = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(orderList, total);
		return pageUtil;
	}
	/**
	 * 添加
	 * @param order
	 * @return
	 */
	@RequiresPermissions("back:api:receiving:save")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public JSONObject save(Order order) {
		log.info("/back/api/receiving/save.json : \n");
		log.info("Order = " + order + "\n");
		try {
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			order.setOrderNo(Utils.UUID());
			int status = orderService.insertSelective(order);
			if (status>0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "添加成功");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "未知异常");
	}
	/**
	 * 改
	 * 
	 * @param vip
	 * @param resp
	 * @return
	 */
	@RequiresPermissions("back:api:receiving:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JSONObject update(Order order, HttpServletResponse resp) {
		log.info("/back/api/receiving/update.json : \n");
		log.info("Order = " + order + "\n");
		try {
			order.setUpdateTime(new Date());
			int row = orderService.updateByPrimaryKeySelective(order);
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
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("back:api:receiving:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public JSONObject delete(String id) {
		log.info("/back/api/receiving/delete.json : \n");
		log.info("id = " + id + "\n");
		try {
			int status=orderService.deleteByPrimaryKey(id);
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
	@RequiresPermissions("back:api:receiving:batchRemove")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JSONObject batchRemove(@RequestParam("ids[]")String[] ids) {
		log.info("/back/api/receiving/batchRemove.json : \n");
		log.info("ids = " + ids + "\n");
		try {
			List<String> list= new ArrayList<>();
			for (String id : ids) {
				list.add(id);
			}
			int status=orderService.batchRemove(list);
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

}
