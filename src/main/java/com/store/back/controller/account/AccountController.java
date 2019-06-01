package com.store.back.controller.account;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.User;
import com.store.service.AccountService;


@Controller
@RequestMapping("/back/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	@RequiresPermissions("back:account:list")
	@RequestMapping("/list")
	public String list() {
		return "account/list";
	}
	
	
	@RequiresPermissions("back:account:toRecharge")
	@RequestMapping("/toRecharge")
	public ModelAndView toRecharge(String userId) {
		User user = accountService.selectByPrimaryKey(userId);
		return new ModelAndView("account/recharge", "user", user);
	}
	
	

}

