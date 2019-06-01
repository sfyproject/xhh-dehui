package com.store.back.controller.notice;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.store.model.Notice;
import com.store.service.NoticeService;

@Controller
@RequestMapping("/back/notice")
public class PageNoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequiresPermissions("back:notice:list")
	@RequestMapping("/list")
	public String list() {
		return "notice/list";
	}
	@RequiresPermissions("back:notice:add")
	@RequestMapping("/add")
	public String add() {
		return "notice/add";
	}
	
	@RequiresPermissions("back:notice:edit")
	@RequestMapping("/edit")
	public ModelAndView toEdit(String noticeId) {
		Notice notice = noticeService.selectByNoticeId(noticeId);
		return new ModelAndView("notice/edit", "notice", notice);
	}
}
