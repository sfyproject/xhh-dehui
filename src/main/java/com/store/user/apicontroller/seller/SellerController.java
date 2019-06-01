package com.store.user.apicontroller.seller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.resp.GatewayProtocol;
import com.store.service.InfoService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/seller")
public class SellerController {

	@Autowired
	private InfoService infoService;

	/**
	 * 获取起点城市
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/city", method = RequestMethod.GET)
	public JSONObject city(HttpServletRequest req) {
		try {
			String city = infoService.selectCity("1");
			if (Utils.isNullStr(city)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, city, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}