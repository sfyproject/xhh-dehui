package com.store.back.controller.receiving;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Order;
import com.store.model.User;
import com.store.service.OrderService;
import com.store.service.UserService;

@Controller
@RequestMapping("/back/receiving")
public class PageReceivingController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	@RequiresPermissions("back:receiving:list")
	@RequestMapping("/list")
	public String list() {
		return "goods/receiving/list";
	}
	
	@RequiresPermissions("back:receiving:add")
	@RequestMapping("/add")
	String add(Model model) {
		List<User> list = userService.selectAll(null);
		model.addAttribute("list", list);
		return "goods/receiving/add";
	}
	
	@RequiresPermissions("back:receiving:edit")
	@RequestMapping("/edit")
	String edit(String id, Model model) {
		Order order = orderService.selectByPrimaryKey(id);
		model.addAttribute("order", order);
		return "goods/receiving/edit";
	}

}
