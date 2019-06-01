package com.store.back.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.store.model.Admin;
import com.store.model.SysMenu;
import com.store.model.Tree;
import com.store.service.SysMenuService;

@Controller
@RequestMapping("/back")
public class PageIndexController {
    @Autowired
    private SysMenuService sysMenuService;
    
	@RequestMapping("/index")
	public String index(HttpServletRequest req,HttpServletResponse response,Model model) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		HttpSession session = req.getSession();
		Admin admin = (Admin) session.getAttribute("login");
		model.addAttribute("admin", admin);
		List<Tree<SysMenu>> listMenuTree = sysMenuService.listMenuTree(admin.getAdminId());
		model.addAttribute("menus", listMenuTree);
		return "index";
	}
	
}