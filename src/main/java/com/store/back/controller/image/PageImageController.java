package com.store.back.controller.image;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/image")
public class PageImageController {

	@RequiresPermissions("back:image:list")
	@RequestMapping("/list")
	public String list() {
		return "/goods/image/image";
	}

}
