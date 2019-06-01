package com.store.back.controller.user;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.User;
import com.store.service.UserService;

@Controller
@RequestMapping("/back/user")
public class PageUserController {
	Logger log = LoggerFactory.getLogger(PageUserController.class);
	@Autowired
	private UserService userService;
	
	@RequiresPermissions("back:user:list")
	@RequestMapping("/list")
	public String list() {
		return "user/list";
	}
	
	
	@RequiresPermissions("back:user:edit")
	@RequestMapping("/edit")
	String edit(String userId,Model model) {
		System.out.println("userId:"+userId);
		User user = userService.selectByPrimaryKey(userId);
		model.addAttribute("user", user);
		return "user/edit";
		
	}
}