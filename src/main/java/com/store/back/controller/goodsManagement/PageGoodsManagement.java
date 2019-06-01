package com.store.back.controller.goodsManagement;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.Goods;
import com.store.model.Sort;
import com.store.service.GoodsService;
import com.store.service.SortService;

@Controller
@RequestMapping("/back/goodsManagement")
public class PageGoodsManagement {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private SortService  sortService;
	
	
	@RequiresPermissions("back:goodsManagement:list")
	@RequestMapping("/list")
	public String list() {
		return "/goods/goodsManagement/list";
	}
	
	@RequiresPermissions("back:goodsManagement:add")
	@RequestMapping("/add")
	public String add(){
		
		return "/goods/goodsManagement/add";
	}
	@RequiresPermissions("back:goodsManagement:del")
	@RequestMapping("/del")
	public String del(){
		return "/goods/goodsManagement/del";
	}
	
	@RequiresPermissions("back:goodsManagement:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(Model model,String goodsId) {
		Sort entity = new Sort();
		entity.setGrade("1");
		List<Sort> list = sortService.page(entity);
		Goods goods = goodsService.selectByPrimaryKey(goodsId);
		model.addAttribute("list", list);
		model.addAttribute("goods", goods);
		return new ModelAndView("/goods/goodsManagement/edit", "goods", goods);
	}
	@RequiresPermissions("back:goodsManagement:positioning")
	@RequestMapping("/positioning")
	public String positioning(){
		return "/goods/goodsManagement/positioning";
	}
}	
