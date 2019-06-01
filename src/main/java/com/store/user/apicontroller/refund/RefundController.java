package com.store.user.apicontroller.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Order;
import com.store.model.req.WxRefundReq;
import com.store.model.resp.GatewayProtocol;
import com.store.module.wechat.WxSnsService;
import com.store.service.OrderService;

@RestController
@RequestMapping("/user/api/weChat")
public class RefundController {

	@Autowired
	private WxSnsService wxSnsService;

	@Autowired
	private OrderService orderService;

	/**
	 * 微信退款
	 * 
	 * @param wxRefund
	 * @return
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public JSONObject refund(WxRefundReq wxRefund) throws Exception {
		Order order = orderService.selectByPrimaryKey(wxRefund.getOrderNo());
		boolean shipped = Order.STATUS_SHIPPED.equals(order.getOrderStatus());
		boolean refunded = Order.STATUS_REFUNDED.equals(order.getOrderStatus());
		boolean completed = Order.STATUS_COMPLETED.equals(order.getOrderStatus());
		if (shipped || refunded || completed) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单已经不能退款");
		} else {
			return wxSnsService.refund(wxRefund);
		}
	}

}