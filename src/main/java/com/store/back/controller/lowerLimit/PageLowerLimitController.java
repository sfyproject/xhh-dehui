package com.store.back.controller.lowerLimit;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.service.SortService;
@Controller
@RequestMapping("/back/lowerLimit")
public class PageLowerLimitController {
	@Autowired
	SortService sortService;
	
	@RequiresPermissions("back:lowerLimit:list")
	@RequestMapping("/list")
	public String list() {
		return "goods/lowerLimit/list";
	}
	
	@RequiresPermissions("back:lowerLimit:add")
	@RequestMapping("/add")
	String add(Model model,String sortId) {
		model.addAttribute("parentId", sortId);
		
		if ("0".equals(sortId)) {
			model.addAttribute("parentSortName", "根目录");
		} else {
			model.addAttribute("parentSortName", sortService.selectByPrimaryKey(sortId).getSortName());
		}
		return "goods/lowerLimit/add";
	}
	
}
