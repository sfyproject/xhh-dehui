package com.store.back.controller.sysRole;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.SysRole;
import com.store.service.SysRoleService;


@Controller
@RequestMapping("/back/sysRole")
public class PageSysRoleController {
	@Autowired
	SysRoleService sysRoleService;
	
	@RequiresPermissions("back:sysRole:list")
	@RequestMapping("/list")
	public String list() {
		return "sys/sysRole/list";
	}
	
	@RequiresPermissions("back:sysRole:add")
	@RequestMapping("/add")
	String add() {
		return "sys/sysRole/add";
	}
	
	@RequiresPermissions("back:sysRole:edit")
	@GetMapping("/edit")
	String edit(Long id, Model model) {
		SysRole roleDO = sysRoleService.selectByPrimaryKey(id);
		model.addAttribute("role", roleDO);
		return "sys/sysRole/edit";
	}



}
