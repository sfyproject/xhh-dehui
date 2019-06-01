package com.store.user.apicontroller.info;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.User;
import com.store.model.resp.GatewayProtocol;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/info")
public class InfoController {

	@Autowired
	private UserService userService;

	/**
	 * 编辑用户信息
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
	public JSONObject editUserInfo(@RequestBody User user, HttpServletRequest req) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			user.setUserId(uid);
			String name = user.getUserName();
			String phone = user.getUserPhone();
			String address = user.getUserAddress();
			if (Utils.isNullStr(name) || Utils.isNullStr(address) || Utils.isNullStr(phone)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有要修改的用户参数");
			}
			if (!Utils.isNullStr(name) || !Utils.isNullStr(address) || !Utils.isNullStr(phone)) {
				int i = userService.updateByPrimaryKeySelective(user);
				if (i == 1) {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
				} else {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 查询用户信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public JSONObject userInfo(HttpServletRequest req) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			User user = userService.selectByPrimaryKey(uid);
			if (user != null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, user, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}