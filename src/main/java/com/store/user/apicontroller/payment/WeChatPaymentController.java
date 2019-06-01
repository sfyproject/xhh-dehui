package com.store.user.apicontroller.payment;

import java.io.BufferedOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.User;
import com.store.model.req.WeChatPaymentReq;
import com.store.model.resp.GatewayProtocol;
import com.store.module.wechat.WxSnsService;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/weChat")
public class WeChatPaymentController {

	@Autowired
	private WxSnsService wxSnsService;

	@Autowired
	private UserService userService;

	/**
	 * 商品订单微信支付
	 * 
	 * @param request
	 * @param weChatPayment
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public JSONObject payment(HttpServletRequest req, WeChatPaymentReq weChatPayment) {
		try {
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			weChatPayment.setOpenId(user.getUserOpenid());
			weChatPayment.setNotifyUrl("https://banjiuyingshi.top/user/api/weChat/callBack");
			JSONObject json = wxSnsService.payment(req, weChatPayment);
			if (json != null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, json, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 支付成功回调函数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/callBack", method = RequestMethod.POST)
	public JSONObject getWeChatPayReturn(HttpServletRequest req, HttpServletResponse resp) {
		try {
			boolean flag = wxSnsService.callBack(req, resp);
			if (flag) {
				String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
				BufferedOutputStream out = new BufferedOutputStream(resp.getOutputStream());
				out.write(resXml.getBytes());
				out.flush();
				out.close();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, flag, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}