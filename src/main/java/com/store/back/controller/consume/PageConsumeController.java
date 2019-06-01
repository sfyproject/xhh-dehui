package com.store.back.controller.consume;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/consume")
public class PageConsumeController {

	@RequestMapping("/list")
	public String list() {
		return "consume/list";
	}

}