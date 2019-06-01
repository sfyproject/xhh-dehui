package com.store.back.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.Order;
import com.store.service.OrderService;

@Controller
@RequestMapping("/back/order")
public class PageOrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping("/list")
	public String list() {
		return "order/list";
	}

	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)
	public ModelAndView toEdit(String storeOrderNo) {
		Order order = orderService.selectByPrimaryKey(storeOrderNo);
		return new ModelAndView("order/edit", "order", order);
	}

}