package com.store.back.controller.recharge;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/recharge")
public class PageRechargeController {

	@RequiresPermissions("back:recharge:list")
	@RequestMapping("/list")
	public String list() {
		return "recharge/list";
	}

}