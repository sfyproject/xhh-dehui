package com.store.back.controller.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Admin;
import com.store.model.SysRole;
import com.store.service.AdminService;
import com.store.service.SysRoleService;

@Controller
@RequestMapping("/back/admin")
public class PageAdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	SysRoleService sysRoleService;
	
	@RequiresPermissions("back:admin:list")
	@RequestMapping("/list")
	public String list() {
		return "sys/admin/list";
	}
	
	@RequiresPermissions("back:admin:add")
	@RequestMapping("/add")
	String add(Model model) {
		List<SysRole> roles = sysRoleService.page();
		model.addAttribute("roles", roles);
		return "sys/admin/add";
	}
	
	
	
	@RequiresPermissions("back:admin:edit")
	@RequestMapping("/edit")
	String edit(String id, Model model) {
		List<SysRole> roles = sysRoleService.getsysRoleServiceByAdminId(id);
		model.addAttribute("roles", roles);
		Admin admin = adminService.selectByPrimaryKey(id);
		model.addAttribute("admin", admin);
		return "sys/admin/edit";
	}
	@RequiresPermissions("back:admin:resetPwd")
	@GetMapping("/resetPwd")
	String resetPwd(String id, Model model) {

		Admin admin=new Admin();
		admin.setAdminId(id);
		model.addAttribute("admin", admin);
		return "sys/admin/reset_pwd";
	}
	
	

}
