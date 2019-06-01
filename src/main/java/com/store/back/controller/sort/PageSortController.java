package com.store.back.controller.sort;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Sort;
import com.store.service.SortService;

@Controller
@RequestMapping("/back/sort")
public class PageSortController {
	@Autowired
	SortService sortService;
	
	@RequiresPermissions("back:sort:list")
	@RequestMapping("/list")
	public String list() {
		return "goods/sort/list";
	}
	
	@RequiresPermissions("back:sort:add")
	@RequestMapping("/add")
	String add(Model model,String sortId) {
		model.addAttribute("parentId", sortId);
		
		if ("0".equals(sortId)) {
			model.addAttribute("parentSortName", "根目录");
		} else {
			model.addAttribute("parentSortName", sortService.selectByPrimaryKey(sortId).getSortName());
		}
		return "goods/sort/add";
	}
	
	@RequiresPermissions("back:sort:edit")
	@RequestMapping("/edit")
	String edit(Model model,String id) {
		
		Sort sort = sortService.selectByPrimaryKey(id);

		Sort entity = new Sort();
		entity.setGrade("1");
		List<Sort> list = sortService.page(entity);
		model.addAttribute("sort", sort);
		model.addAttribute("list", list);
		return "goods/sort/edit";
	}

}
