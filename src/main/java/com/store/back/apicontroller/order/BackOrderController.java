package com.store.back.apicontroller.order;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.store.back.apicontroller.receiving.BackReceivingController;
import com.store.model.Order;
import com.store.model.req.PageQuery;
import com.store.service.OrderService;
import com.store.utils.PageUtils;

@RestController
@RequestMapping("/back/api/order")
public class BackOrderController {
	Logger log = LoggerFactory.getLogger(BackReceivingController.class);
	
	@Autowired 
	private OrderService orderService;
	
	@GetMapping("/page")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		log.info("/back/api/order/page.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		Order order= JSON.parseObject(JSON.toJSONString(params), Order.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Order> pageInfo = orderService.page(query, order);
		List<Order> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(list, total);
		return pageUtil;
	}
	
	@GetMapping("/orderPage")
	@ResponseBody
	PageUtils orderList(@RequestParam Map<String, Object> params) {
		log.info("/back/api/order/orderPage.json : \n");
		log.info("params = " + params + "\n");
		// 查询列表数据
		Order order= JSON.parseObject(JSON.toJSONString(params), Order.class);
		PageQuery query= JSON.parseObject(JSON.toJSONString(params), PageQuery.class);
		PageInfo<Order> pageInfo = orderService.orderPage(query, order);
		List<Order> list = pageInfo.getList();
		long total = pageInfo.getTotal();
		PageUtils pageUtil = new PageUtils(list, total);
		return pageUtil;
	}
}
