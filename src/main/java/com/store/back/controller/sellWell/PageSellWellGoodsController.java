package com.store.back.controller.sellWell;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Goods;
import com.store.model.Sort;
import com.store.service.GoodsService;
import com.store.service.SortService;


@Controller
@RequestMapping("/back/sellWell")
public class PageSellWellGoodsController {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	SortService sortService;

	
	@RequiresPermissions("back:sellWell:list")
	@RequestMapping("/list")
	public String list() {
		return "goods/sellwell/list";
	}

	@RequiresPermissions("back:sellWell:edit")
	@RequestMapping("/edit")
	public String edit(Model model,String storeGoodsId) {

		Sort entity = new Sort();
		entity.setGrade("1");
		List<Sort> list = sortService.page(entity);
		
		Goods goods = goodsService.selectByPrimaryKey(storeGoodsId);
		model.addAttribute("list", list);
		model.addAttribute("goods", goods);
		return "goods/sellwell/edit";
	}


}
