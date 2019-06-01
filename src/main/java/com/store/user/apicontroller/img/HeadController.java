package com.store.user.apicontroller.img;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.resp.GatewayProtocol;
import com.store.service.ImageService;

@RestController
@RequestMapping("/user/api/img")
public class HeadController {

	@Autowired
	private ImageService imageService;

	/**
	 * 查询轮播图
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/headlines", method = RequestMethod.GET)
	public JSONObject headlines(HttpServletRequest req) {
		try {
			List<String> imageLink = imageService.selectImageLink();
			if (imageLink == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, imageLink, "SUCCESS");
			}
		} catch (Exception e) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
		}
	}

}