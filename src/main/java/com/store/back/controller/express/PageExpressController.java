package com.store.back.controller.express;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.Express;
import com.store.service.ExpressService;

@Controller
@RequestMapping("/back/express")
public class PageExpressController {

	@Autowired
	private ExpressService expressService;

	@RequestMapping("/list")
	public String list() {
		return "express/list";
	}

	@RequestMapping("/toAdd")
	public String add() {
		return "express/add";
	}

	@RequestMapping("/toEdit")
	public ModelAndView toEdit(String storeExpressNo) {
		Express express = expressService.selectByPrimaryKey(storeExpressNo);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		express.setFormatDate(simpleDateFormat.format(express.getCreateTime()));
		return new ModelAndView("express/edit", "express", express);
	}

}