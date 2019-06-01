package com.store.user.apicontroller.wallet;

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
@RequestMapping("/user/api/wallet")
public class WalletController {

	@Autowired
	private WxSnsService wxSnsService;

	@Autowired
	private UserService userService;

	/**
	 * 微信充值
	 * 
	 * @param req
	 * @param weChatPayment
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public JSONObject recharge(HttpServletRequest req, WeChatPaymentReq weChatPayment) {
		try {
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			weChatPayment.setOpenId(user.getUserOpenid());
			weChatPayment.setNotifyUrl("http://localhost:8081/user/api/wallet/callBack");
			JSONObject json = wxSnsService.paymentRecharge(req, weChatPayment);
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
	 * 充值成功回调函数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/callBack", method = RequestMethod.POST)
	public JSONObject getWeChatPayReturn(HttpServletRequest req, HttpServletResponse resp) {
		try {
			boolean flag = wxSnsService.rechargeCallBack(req, resp);
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

	/**
	 * 用户余额
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/balance", method = RequestMethod.GET)
	public JSONObject balance(HttpServletRequest req, HttpServletResponse resp) {
		try {
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			if (user != null) {
				String wallet = user.getUserWallet();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, wallet, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}