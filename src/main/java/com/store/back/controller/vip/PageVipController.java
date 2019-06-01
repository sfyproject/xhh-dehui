package com.store.back.controller.vip;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.User;
import com.store.model.Vip;
import com.store.service.UserService;
import com.store.service.VipService;

@Controller
@RequestMapping("/back/vip")
public class PageVipController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private VipService vipService;
	
	@RequiresPermissions("back:vip:list")
	@RequestMapping("/list")
	public String list() {
		return "vip/list";
	}
	
	@RequiresPermissions("back:vip:add")
	@RequestMapping("/add")
	String add(Model model) {
		List<User> list = userService.selectAll(null);
		model.addAttribute("list", list);
		return "vip/add";
	}
	
	@RequiresPermissions("back:vip:edit")
	@RequestMapping("/edit")
	String edit(String id, Model model) {
		Vip vip = vipService.selectByPrimaryKey(id);
		model.addAttribute("vip", vip);
		return "vip/edit";
	}
}
