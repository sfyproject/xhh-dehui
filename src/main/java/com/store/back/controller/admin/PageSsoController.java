package com.store.back.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/back")
public class PageSsoController {

	@RequestMapping("/login")
	public String login() {
		return "sso/login";
	}
	//错误页面展示S
	@RequestMapping("/error/500")
    public String error403(){
        return "error/500";
    }
	//错误页面展示
	@RequestMapping("/error/403")
	public String error500(){
		return "error/403";
	}
	//错误页面展示
	@RequestMapping("/error/404")
	public String error404(){
		return "error/404";
	}
	//首页展示
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	

}