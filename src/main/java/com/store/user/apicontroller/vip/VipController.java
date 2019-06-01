package com.store.user.apicontroller.vip;

import java.io.BufferedOutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.User;
import com.store.model.Vip;
import com.store.model.req.VipPaymentReq;
import com.store.model.resp.GatewayProtocol;
import com.store.module.wechat.WxSnsService;
import com.store.service.UserService;
import com.store.service.VipService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/vip")
public class VipController {

	@Autowired
	private UserService userService;

	@Autowired
	private VipService vipService;

	@Autowired
	private WxSnsService wxSnsService;

	/**
	 * 购买会员微信支付
	 * 
	 * @param request
	 * @param weChatPayment
	 * @return
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public JSONObject payment(HttpServletRequest req, VipPaymentReq vipPayment) {
		try {
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			vipPayment.setOpenId(user.getUserOpenid());
			vipPayment.setNotifyUrl("https://banjiuyingshi.top/user/api/vip/callBack");
			JSONObject json = wxSnsService.paymentVip(req, vipPayment);
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
	 * 购买会员回调函数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/callBack", method = RequestMethod.POST)
	public JSONObject callBack(HttpServletRequest request, HttpServletResponse response) {
		try {
			boolean flag = wxSnsService.callBackVip(request, response);
			if (flag) {
				String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
				out.write(resXml.getBytes());
				out.flush();
				out.close();
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, flag, "付款成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "结算失败");
	}

	/**
	 * 个人会员信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public JSONObject detail(HttpServletRequest req) {
		try {
			Vip vip = vipService.selectByUid(Utils.getAuthUserId(req));
			if (vip == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				long msec = new Date().getTime() - vip.getVipEnd().getTime();
				long day = msec / (1000 * 60 * 60 * 24);
				if (day < 3) {
					vip.setRemind("温馨提示：您的会员还有" + (int) day + "天到期！");
				}
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, vip, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}