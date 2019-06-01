package com.store.user.apicontroller.coupon;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Coupon;
import com.store.model.resp.GatewayProtocol;
import com.store.service.CouponService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;

	/**
	 * 个人优惠券
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/selectByUid", method = RequestMethod.GET)
	public JSONObject selectByUid(HttpServletRequest req) {
		try {
			String authUserId = Utils.getAuthUserId(req);
			List<Coupon> list = couponService.selectByUid(authUserId);
			if (list == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}