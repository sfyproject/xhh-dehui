package com.store.back.controller.sysMenu;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.dao.SysMenuMapper;
import com.store.model.SysMenu;

@Controller
@RequestMapping("/back/sysMenu")
public class PageSysMenuController {
	@Autowired
	SysMenuMapper sysMenuMapper;
	
	@RequiresPermissions("back:sysMenu:list")
	@RequestMapping("/list")
	public String list() {
		return "sys/sysMenu/list";
	}
	
	@RequiresPermissions("back:sysMenu:add")
	@RequestMapping("/add")
	String add(Model model,Long pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", sysMenuMapper.selectByPrimaryKey(pId).getName());
		}
		return "sys/sysMenu/add";
	}
	
	@RequiresPermissions("back:menu:edit")
	@RequestMapping("/edit")
	String edit(Model model,Long id) {
		SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(id);
		if(sysMenu != null) {
			model.addAttribute("menu", sysMenu);
			Long parentId = sysMenu.getParentId();
			if(parentId != null) {
				model.addAttribute("pId", sysMenu.getParentId());
				if (parentId == 0) {
					model.addAttribute("pName", "根目录");
				} else {
					model.addAttribute("pName", sysMenuMapper.selectByPrimaryKey(parentId).getName());
				}
			}
			
		}
		
		return "sys/sysMenu/edit";
	}


}
