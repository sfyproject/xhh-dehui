package com.store.user.apicontroller.c8order;

import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Consume;
import com.store.model.Order;
import com.store.model.Refund;
import com.store.model.User;
import com.store.model.req.WeChatPaymentReq;
import com.store.model.resp.GatewayProtocol;
import com.store.module.wechat.WxSnsService;
import com.store.service.ConsumeService;
import com.store.service.OrderService;
import com.store.service.RefundService;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/c8order")
public class C8OrderController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private WxSnsService wxSnsService;

	@Autowired
	private RefundService refundService;

	@Autowired
	private ConsumeService consumeService;
	
	@Autowired
	private OrderService orderService;

	@Value("${url.c8.path}")
	private String C8URL;

	/**
	 * C8余额支付订单
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/balancePayment", method = RequestMethod.POST)
	public JSONObject balancePayment(HttpServletRequest req, @RequestBody Order order) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			User user = userService.selectByPrimaryKey(uid);
			if (user != null && !user.getUserPassword().equals(order.getPassword())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "支付密码不正确");
			}
			double wallet = Double.parseDouble(user.getUserWallet());
			double orderPrice = Double.parseDouble(order.getOrderPrice());
			if (orderPrice > wallet) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "余额不足");
			}
			String host = C8URL;
			String path = "/dehui/api/order/balancePayment";
			String method = "POST";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Access-Control-Allow-Origin", "*");
			Map<String, String> querys = new HashMap<>();
			ArrayList<String> list = new ArrayList<>();
			list.add("https://c8t.sxxzy.top/dehui/DEHUI_MAIN/1.png");
			String string = list.toString().substring(1, list.toString().length() - 1);
			querys.put("orderGoodImages", string);
			com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
			json.put("goodsWeight", order.getGoodsWeight());
			json.put("num", order.getNum());
			json.put("consigneeDetailAddress", user.getUserAddress());
			json.put("consigneeName", user.getUserName());
			json.put("consigneePhone", user.getUserPhone());
			json.put("shippingDetailAddress", "长治东山国际");
			json.put("userName", "德汇易购");
			json.put("userPhone", "22222222222");
			querys.put("dehuiOrder", json.toString());
			JSONObject jsonObject = new JSONObject();
			HttpResponse doPost = Utils.doPost(host, path, method, headers, querys, new HashMap<>());
			HttpEntity entity = doPost.getEntity();
			JSONObject data = JSONObject.parseObject(EntityUtils.toString(entity));
			HashMap<String, String> map = (HashMap<String, String>) data.get("data");
			if (Utils.isNullStr(map.get("orderNo").toString()) || Utils.isNullStr(map.get("shippingPrice"))) {
				jsonObject.put("ret", GatewayProtocol.RET_FAIL);
				jsonObject.put("data", null);
				jsonObject.put("msg", (String) data.get("msg"));
			} else {
				double shippingPrice = Double.parseDouble((String) map.get("shippingPrice"));
				// 更新余额
				double balance = wallet - orderPrice - shippingPrice;
				user.setUserWallet(balance + "");
				int i = userService.updateByPrimaryKeySelective(user);
				if (i == 1) {
					// 消费记录
					Consume consume = new Consume();
					consume.setConsumeId(Utils.UUID());
					consume.setConsumeCoin(orderPrice + "");
					consume.setConsumeUid(uid);
					consume.setCreateTime(new Date());
					consumeService.insertSelective(consume);
				}
				jsonObject.put("ret", GatewayProtocol.RET_SUCCESS);
				jsonObject.put("data", map.get("orderNo"));
				jsonObject.put("msg", "SUCCESS");
			}
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * C8分页查询
	 * 
	 * @param multiStatus
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/pageQuery", method = RequestMethod.GET)
	public JSONObject pageQuery(@RequestParam(value = "multiStatus") List<Long> multiStatus, HttpServletRequest req) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			User user = userService.selectByPrimaryKey(uid);
			if (user == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有该用户");
			}
			String host = C8URL;
			String path = "/dehui/api/order/pageQuery";
			String method = "GET";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Access-Control-Allow-Origin", "*");
			Map<String, String> querys = new HashMap<>();
			querys.put("multiStatus", multiStatus.toString());
			querys.put("phone", user.getUserPhone());
			HttpResponse doGet = Utils.doGet(host, path, method, headers, querys);
			HttpEntity entity = doGet.getEntity();
			String string = EntityUtils.toString(entity);
			if (!Utils.isNullStr(string)) {
				return JSONObject.parseObject(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * C8取消订单，余额
	 * 
	 * @param order
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cancelBalance", method = RequestMethod.POST)
	public JSONObject cancelBalance(@RequestBody Order order, HttpServletRequest req) {
		try {
			String host = C8URL;
			String path = "/dehui/api/order/cancelBalance";
			String method = "POST";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Access-Control-Allow-Origin", "*");
			Map<String, String> querys = new HashMap<>();
			querys.put("orderNo", order.getOrderNo());
			querys.put("remark", order.getRemark());
			HttpResponse doPost = Utils.doPost(host, path, method, headers, querys, new HashMap<>());
			HttpEntity entity = doPost.getEntity();
			String string = EntityUtils.toString(entity);
			if (!Utils.isNullStr(string)) {
				JSONObject json = JSONObject.parseObject(string);
				if ("0".equals(json.get("ret"))) {
					User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
					double wallet = Double.parseDouble(user.getUserWallet());
					BigDecimal orderPrice = (BigDecimal) json.get("data");
					double balance = wallet + orderPrice.doubleValue();
					user.setUserWallet(balance + "");
					int i = userService.updateByPrimaryKeySelective(user);
					if (i == 1) {
						Refund refund = new Refund();
						refund.setRefundId(Utils.UUID());
						refund.setRefundCoin(orderPrice + "");
						refund.setRefundUid(Utils.getAuthUserId(req));
						refund.setCreateTime(new Date());
						refundService.insertSelective(refund);
					}
				}
				return JSONObject.parseObject(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}
	
	/**
	 * C8取消订单，微信
	 * 
	 * @param order
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cancelWeChat", method = RequestMethod.POST)
	public JSONObject cancelWeChat(@RequestBody Order order, HttpServletRequest req) {
		try {
			String host = C8URL;
			String path = "/dehui/api/order/cancelWeChat";
			String method = "POST";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Access-Control-Allow-Origin", "*");
			Map<String, String> querys = new HashMap<>();
			querys.put("orderNo", order.getOrderNo());
			querys.put("remark", order.getRemark());
			HttpResponse doPost = Utils.doPost(host, path, method, headers, querys, new HashMap<>());
			HttpEntity entity = doPost.getEntity();
			String string = EntityUtils.toString(entity);
			order = orderService.selectByPrimaryKey(order.getOrderNo());
			if (!Utils.isNullStr(string)) {
				JSONObject json = JSONObject.parseObject(string);
				if ("0".equals(json.get("ret"))) {
					Refund refund = new Refund();
					refund.setRefundId(Utils.UUID());
					refund.setRefundCoin(order.getOrderPrice());
					refund.setRefundUid(Utils.getAuthUserId(req));
					refund.setCreateTime(new Date());
					refundService.insertSelective(refund);
				}
				return JSONObject.parseObject(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * C8订单详情
	 * 
	 * @param order
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public JSONObject detail(String orderNo, HttpServletRequest req) {
		try {
			String host = C8URL;
			String path = "/dehui/api/order/detail";
			String method = "GET";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("Access-Control-Allow-Origin", "*");
			Map<String, String> querys = new HashMap<>();
			querys.put("orderNo", orderNo);
			HttpResponse doGet = Utils.doGet(host, path, method, headers, querys);
			HttpEntity entity = doGet.getEntity();
			String string = EntityUtils.toString(entity);
			if (!Utils.isNullStr(string)) {
				return JSONObject.parseObject(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}
	
	/**
	 * C8订单微信支付
	 * 
	 * @param request
	 * @param weChatPayment
	 * @return
	 */
	@RequestMapping(value = "/weChatPayment", method = RequestMethod.POST)
	public JSONObject weChatPayment(HttpServletRequest req, WeChatPaymentReq weChatPayment) {
		try {
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			weChatPayment.setOpenId(user.getUserOpenid());
			weChatPayment.setNotifyUrl("http://localhost:8081/user/api/c8order/callBack");
			req.getSession().setAttribute("isC8Order", true);
			req.getSession().setAttribute("userId", user.getUserId());
			req.getSession().setAttribute("orderPrice", weChatPayment.getTotalFee());
			req.getSession().setAttribute("goodsNumber", weChatPayment.getGoodsNumber());
			req.getSession().setAttribute("goodsWeight", weChatPayment.getGoodsWeight());
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
			req.getSession().setAttribute("isC8Order", true);
			req.getSession().setAttribute("C8URL", C8URL);
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