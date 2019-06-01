package com.store.back.apicontroller.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Admin;
import com.store.model.resp.GatewayProtocol;
import com.store.service.AdminService;

@RestController
@RequestMapping("/back/api/sso")
public class BackSsoController {
	Logger log = LoggerFactory.getLogger(BackSsoController.class);

	@Autowired
	private AdminService adminService;

	// 登录接口
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(Admin getAdmin, HttpServletRequest req,HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");

		log.info("/back/api/sso/login.json : \n");
		log.info("map = " + getAdmin + "\n");

		try {
			String adminUsername = getAdmin.getAdminUsername();
			String adminPassword = getAdmin.getAdminPassword();

			if (!adminService.existUserName(adminUsername)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户名不存在，请注册后登录。");
			}
			       /* Admin admin = adminService.loadAdminByUsername(adminUsername);*/
					// 把用户名和密码封装为 UsernamePasswordToken 对象  
		            UsernamePasswordToken token = new UsernamePasswordToken(adminUsername, adminPassword, "login");
		         // 获取当前的 Subject. 调用 SecurityUtils.getSubject();
		            Subject currentUser = SecurityUtils.getSubject();
		            try {
		            	 // 执行登录.
		                currentUser.login( token );
		                //验证是否登录成功
		                if (currentUser.isAuthenticated()) {
		                    System.out.println("用户[" + adminUsername + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
		                    return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, "/back/index", "登录成功");
		                } else {
		                    token.clear();
		                    System.out.println("用户[" + adminUsername + "]登录认证失败,重新登陆");
		                    return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, "/back/sso/login", "用户名或密码错误，请查证后重试。");

		                }
		            } catch ( Exception uae ) {
		            	uae.printStackTrace();
		            }
		            return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, "/back/sso/login", "用户名或密码错误，请查证后重试。");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户名或密码错误，请查证后重试。");

	}
	// 退出系统
		@RequestMapping(value = "/logout", method = RequestMethod.POST)
		public JSONObject logout(HttpServletRequest req) throws Exception {
			try {
				SecurityUtils.getSubject().logout();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, "", "");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
		}

}


	
