package com.store.module.wechat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.store.dao.CartMapper;
import com.store.dao.ConsumeMapper;
import com.store.dao.CouponMapper;
import com.store.dao.GoodsMapper;
import com.store.dao.OrderGoodsMapper;
import com.store.dao.OrderMapper;
import com.store.dao.RechargeMapper;
import com.store.dao.RefundMapper;
import com.store.dao.UserMapper;
import com.store.dao.VipMapper;
import com.store.model.Consume;
import com.store.model.Coupon;
import com.store.model.Goods;
import com.store.model.Order;
import com.store.model.OrderGoods;
import com.store.model.Recharge;
import com.store.model.Refund;
import com.store.model.User;
import com.store.model.Vip;
import com.store.model.req.VipPaymentReq;
import com.store.model.req.WeChatPaymentReq;
import com.store.model.req.WxRefundReq;
import com.store.model.resp.CartResp;
import com.store.model.resp.GatewayProtocol;
import com.store.utils.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressWarnings("deprecation")
@Service
public class WxSnsServiceImpl extends WxBaseInfo implements WxSnsService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private VipMapper vipMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	@Autowired
	private RechargeMapper rechargeMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private ConsumeMapper consumeMapper;
	
	@Autowired
	private RefundMapper refundMapper;

	public WxLoginResp wxLogin(String code) throws IOException {
		String url = JSCODE2SESSIONURL.replace("APPID", APPID).replace("SECRET", SECRET).replace("JSCODE", code);
		String wxResp = this.runGet(url);
		WxLoginResp wxLoginResp = JSONObject.parseObject(wxResp, WxLoginResp.class);
		return wxLoginResp;
	}

	@Override
	public String getToke() throws IOException {
		String url = TOKENURL.replace("APPID", APPID).replace("SECRET", SECRET);
		String wxResp = runGet(url);
		return wxResp;
	}

	private String runGet(String url) throws IOException {
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url).build();
		Response response = okHttpClient.newCall(request).execute();
		return response.body().string();
	}

	@Override
	public JSONObject payment(HttpServletRequest request, WeChatPaymentReq weChatPayment) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Order order = weChatPayment.getOrder();
		String orderNo = Utils.getOrderNo();
		double totalFee = Double.parseDouble(weChatPayment.getTotalFee());
		totalFee = totalFee * 100;
		// 微信的应用ID
		map.put("appid", APPID);
		// 微信支付商户信息号
		map.put("mch_id", MCH_ID);
		// 随机字符串，不长于32位
		map.put("nonce_str", Utils.UUID());
		// 商品描述
		map.put("body", "body");
		// 商户系统内部的订单号，32个字符内、可包含字母
		map.put("out_trade_no", orderNo);
		// 订单总金额，单位为分
		map.put("total_fee", (int) totalFee + "");
		// IP地址
		map.put("spbill_create_ip", Utils.getIpAddr(request));
		// 接收微信支付异步通知回调地址，通知URL必须为直接可访问的URL，不能携带参数
		map.put("notify_url", weChatPayment.getNotifyUrl());
		// 支付类型
		map.put("trade_type", "JSAPI");
		// 微信端下的唯一用户标识
		map.put("openid", weChatPayment.getOpenId());
		// 调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
		String stringA = Utils.formatUrlMap(map, false, false);
		String sign = Utils.MD5(stringA + "&key=" + KEY).toUpperCase();
		// sign签名，第一次随机签名
		map.put("sign", sign);
		// 将参数 编写XML格式 打包要发送的XML
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("<xml>");
		paramBuffer.append("<appid>" + map.get("appid") + "</appid>");
		paramBuffer.append("<mch_id>" + map.get("mch_id") + "</mch_id>");
		paramBuffer.append("<nonce_str>" + map.get("nonce_str") + "</nonce_str>");
		paramBuffer.append("<sign>" + sign + "</sign>");
		paramBuffer.append("<body>" + map.get("body") + "</body>");
		paramBuffer.append("<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>");
		paramBuffer.append("<total_fee>" + map.get("total_fee") + "</total_fee>");
		paramBuffer.append("<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>");
		paramBuffer.append("<notify_url>" + map.get("notify_url") + "</notify_url>");
		paramBuffer.append("<trade_type>" + map.get("trade_type") + "</trade_type>");
		paramBuffer.append("<openid>" + map.get("openid") + "</openid>");
		paramBuffer.append("</xml>");
		// 发送POST请求，获得数据包ID（这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码）
		Map<String, String> reqMap = Utils.doXMLParse(Utils.getRemotePortData(UNIFIEDORDER, paramBuffer.toString()));
		System.out.println(reqMap);
		// 返回给小程序端需要的参数
		Map<String, String> callbackMap = new HashMap<String, String>();
		if ("SUCCESS".equals(reqMap.get("result_code")) && "SUCCESS".equals(reqMap.get("return_code"))) {
			// 返回的预付单信息
			String prepay_id = (String) reqMap.get("prepay_id");
			Long timeStamp = System.currentTimeMillis() / 1000;
			callbackMap.put("appId", APPID);
			callbackMap.put("timeStamp", timeStamp + "");
			callbackMap.put("nonceStr", weChatPayment.getNotifyUrl());
			callbackMap.put("signType", "MD5");
			callbackMap.put("package", "prepay_id=" + prepay_id);
			String stringB = Utils.formatUrlMap(callbackMap, false, false);
			String signB = Utils.MD5(stringB + "&key=" + KEY).toUpperCase();
			// sign签名，第二次随机签名
			callbackMap.put("sign", signB);

			Boolean isC8Order = (Boolean) request.getSession().getAttribute("isC8Order");

			if (isC8Order != null && isC8Order) {
				return GatewayProtocol.protocolBody(callbackMap);
			}
			
			List<String> ids = order.getIds();
			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("name");
			String phone = (String) session.getAttribute("phone");
			String address = (String) session.getAttribute("address");
			List<CartResp> cartGoods = cartMapper.selectByUidAndGid(Utils.getAuthUserId(request), ids);
			BigDecimal orderPrice = new BigDecimal(0);
			ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
			if (cartGoods != null) {
				for (CartResp cartGood : cartGoods) {
					String cartGid = cartGood.getCartGid();
					String price = cartGood.getGoodsPrice();
					Integer num = cartGood.getCartNum();
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdId(Utils.UUID());
					orderGoods.setOgdGnum(num);
					orderGoods.setOgdGid(cartGid);
					orderGoods.setOgdNo(orderNo);
					orderGoodsList.add(orderGoods);
					BigDecimal goodPrice = new BigDecimal(price).multiply(new BigDecimal(num));
					orderPrice = orderPrice.add(goodPrice);
				}
			} else {
				List<Goods> goodsList = goodsMapper.selectByGids(ids);
				for (Goods goods : goodsList) {
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdId(Utils.UUID());
					orderGoods.setOgdGnum(order.getNum());
					orderGoods.setOgdGid(goods.getGoodsId());
					orderGoods.setOgdNo(orderNo);
					orderGoodsList.add(orderGoods);
					BigDecimal goodPrice = new BigDecimal(goods.getGoodsPrice())
							.multiply(new BigDecimal(order.getNum()));
					orderPrice = orderPrice.add(goodPrice);
				}
			}
			if (new BigDecimal(order.getOrderPrice()).compareTo(orderPrice) != 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "价格不正确");
			}
			int i = orderGoodsMapper.batchInsert(orderGoodsList);
			if (i != orderGoodsList.size()) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单商品插入失败");
			}
			if (orderPrice.doubleValue() == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单价格为零");
			}
			BigDecimal totalPrice = orderPrice.add(new BigDecimal(order.getOrderFare()));
			order.setOrderNo(orderNo);
			order.setOrderName(name);
			order.setOrderPhone(phone);
			order.setOrderAddress(address);
			order.setOrderPrice(totalPrice.toString());
			order.setOrderStatus(Order.STATUS_UNPAID);
			order.setUpdateTime(new Date());
			order.setCreateTime(new Date());
			order.setOrderUid(Utils.getAuthUserId(request));
			int row = orderMapper.insert(order);
			if (row == 1) {
				if (cartGoods != null) {
					if (cartMapper.deleteBatch(ids) != ids.size()) {
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "购物车删除失败");
					}
				}
				return GatewayProtocol.protocolBody(callbackMap);
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		}
		return null;
	}

	@Override
	public JSONObject paymentRecharge(HttpServletRequest request, WeChatPaymentReq weChatPayment) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String orderNo = Utils.getOrderNo();
		double totalFee = Double.parseDouble(weChatPayment.getTotalFee());
		totalFee = totalFee * 100;
		// 微信的应用ID
		map.put("appid", APPID);
		// 微信支付商户信息号
		map.put("mch_id", MCH_ID);
		// 随机字符串，不长于32位
		map.put("nonce_str", Utils.UUID());
		// 商品描述
		map.put("body", "body");
		// 商户系统内部的订单号，32个字符内、可包含字母
		map.put("out_trade_no", orderNo);
		// 订单总金额，单位为分
		map.put("total_fee", (int) totalFee + "");
		// IP地址
		map.put("spbill_create_ip", Utils.getIpAddr(request));
		// 接收微信支付异步通知回调地址，通知URL必须为直接可访问的URL，不能携带参数
		map.put("notify_url", weChatPayment.getNotifyUrl());
		// 支付类型
		map.put("trade_type", "JSAPI");
		// 微信端下的唯一用户标识
		map.put("openid", weChatPayment.getOpenId());
		// 调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
		String stringA = Utils.formatUrlMap(map, false, false);
		String sign = Utils.MD5(stringA + "&key=" + KEY).toUpperCase();
		// sign签名，第一次随机签名
		map.put("sign", sign);
		// 将参数 编写XML格式 打包要发送的XML
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("<xml>");
		paramBuffer.append("<appid>" + map.get("appid") + "</appid>");
		paramBuffer.append("<mch_id>" + map.get("mch_id") + "</mch_id>");
		paramBuffer.append("<nonce_str>" + map.get("nonce_str") + "</nonce_str>");
		paramBuffer.append("<sign>" + sign + "</sign>");
		paramBuffer.append("<body>" + map.get("body") + "</body>");
		paramBuffer.append("<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>");
		paramBuffer.append("<total_fee>" + map.get("total_fee") + "</total_fee>");
		paramBuffer.append("<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>");
		paramBuffer.append("<notify_url>" + map.get("notify_url") + "</notify_url>");
		paramBuffer.append("<trade_type>" + map.get("trade_type") + "</trade_type>");
		paramBuffer.append("<openid>" + map.get("openid") + "</openid>");
		paramBuffer.append("</xml>");
		// 发送POST请求，获得数据包ID（这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码）
		Map<String, String> reqMap = Utils.doXMLParse(Utils.getRemotePortData(UNIFIEDORDER, paramBuffer.toString()));
		System.out.println(reqMap);
		// 返回给小程序端需要的参数
		Map<String, String> callbackMap = new HashMap<String, String>();
		if ("SUCCESS".equals(reqMap.get("result_code")) && "SUCCESS".equals(reqMap.get("return_code"))) {
			// 返回的预付单信息
			String prepay_id = (String) reqMap.get("prepay_id");
			Long timeStamp = System.currentTimeMillis() / 1000;
			callbackMap.put("appId", APPID);
			callbackMap.put("timeStamp", timeStamp + "");
			callbackMap.put("nonceStr", weChatPayment.getNotifyUrl());
			callbackMap.put("signType", "MD5");
			callbackMap.put("package", "prepay_id=" + prepay_id);
			String stringB = Utils.formatUrlMap(callbackMap, false, false);
			String signB = Utils.MD5(stringB + "&key=" + KEY).toUpperCase();
			// sign签名，第二次随机签名
			callbackMap.put("sign", signB);

			// 创建充值订单
			Recharge recharge = new Recharge();
			recharge.setRechargeId(orderNo);
			recharge.setRechargeCoin(weChatPayment.getTotalFee());
			recharge.setRechargeType(Recharge.TYPE_WECHAT);
			recharge.setRechargeStatus(Recharge.STATUS_UNPAID);
			recharge.setCreateTime(new Date());
			recharge.setRechargeUid(Utils.getAuthUserId(request));
			rechargeMapper.insertSelective(recharge);

			return GatewayProtocol.protocolBody(callbackMap);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean rechargeCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String inputLine = "";
		String notityXml = "";
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		Map<String, String> map = Utils.doXMLParse(notityXml);
		if ("SUCCESS".equals(map.get("result_code"))) {
			String outTradeNo = map.get("out_trade_no");
			Recharge recharge = rechargeMapper.selectByPrimaryKey(outTradeNo);
			if (Recharge.STATUS_UNPAID.equals(recharge.getRechargeStatus())) {
				// 更新充值记录状态为已支付
				recharge.setRechargeStatus(Recharge.STATUS_PAID);
				int i = rechargeMapper.updateByPrimaryKeySelective(recharge);
				if (i == 1) {
					User user = userMapper.selectByPrimaryKey(recharge.getRechargeUid());
					Double balance = Double.valueOf(user.getUserWallet());
					Double rechargeCoin = Double.valueOf(recharge.getRechargeCoin());
					if (balance == null) {
						balance = 0.00;
					}
					if (rechargeCoin == null) {
						rechargeCoin = 0.00;
					}
					Double newBalance = balance + rechargeCoin;
					user.setUserId(recharge.getRechargeUid());
					user.setUserWallet(newBalance.toString());
					userMapper.updateByPrimaryKeySelective(user);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String inputLine = "";
		String notityXml = "";
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		Map<String, String> map = Utils.doXMLParse(notityXml);
		if ("SUCCESS".equals(map.get("result_code"))) {
			
			String outTradeNo = map.get("out_trade_no");
			Order order = orderMapper.selectByPrimaryKey(outTradeNo);
			User user = null;
			if (order != null) {
				user = userMapper.selectByPrimaryKey(order.getOrderUid());
			}
			String userId = (String) request.getSession().getAttribute("userId");
			if (!Utils.isNullStr(userId)) {
				user = userMapper.selectByPrimaryKey(userId);
			}
			if (user == null) {
				return false;
			}
			Boolean isC8Order = (Boolean) request.getSession().getAttribute("isC8Order");
			String orderPrice = (String) request.getSession().getAttribute("orderPrice");
			String goodsNumber = (String) request.getSession().getAttribute("goodsNumber");
			String goodsWeight = (String) request.getSession().getAttribute("goodsWeight");
			
			if (isC8Order != null && isC8Order) {
				String C8URL = (String) request.getSession().getAttribute("C8URL");
				String host = C8URL;
				String path = "/dehui/api/order/add";
				String method = "POST";
				HashMap<String, String> headers = new HashMap<>();
				headers.put("Access-Control-Allow-Origin", "*");
				JSONObject json = new JSONObject();
				json.put("orderNo", outTradeNo);
				json.put("freightTypeId", "1");
				json.put("carTypeId", "1");
				json.put("externalCommodityPrices", orderPrice);
				json.put("shippingAddress", "长治东山国际");
				json.put("shippingDetailAddress", "长治东山国际");
				json.put("startDetailedAddress", "长治东山国际");
				json.put("consigneeAddress", user.getUserAddress());
				json.put("consigneeDetailAddress", user.getUserAddress());
				json.put("endDetailedAddress", user.getUserAddress());
				json.put("consigneeName", user.getUserName());
				json.put("consigneePhone", user.getUserPhone());
				json.put("payType", "微信支付");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String format = simpleDateFormat.format(new Date());
				json.put("payTime", format);
				json.put("addtime", format);
				json.put("goodsNumber", goodsNumber);
				json.put("goodsWeight", goodsWeight);
				json.put("goodsName", "菜多多货物");
				json.put("userName", "德汇易购");
				json.put("userPhone", "22222222222");
				Map<String, String> querys = new HashMap<>();
				querys.put("dehuiOrder", json.toString());
				HttpResponse doPost = Utils.doPost(host, path, method, headers, querys, new HashMap<>());
				HttpEntity entity = doPost.getEntity();
				String string = EntityUtils.toString(entity);
				if (!Utils.isNullStr(string)) {
					JSONObject jsonObject = JSONObject.parseObject(string);
					String ret = (String) jsonObject.get("ret");
					if (GatewayProtocol.RET_SUCCESS.equals(ret)) {
						return true;
					} else {
						return false;
					}
				}
				return false;
			}
			
			if (Order.STATUS_UNPAID.equals(order.getOrderStatus())) {
				// 更新订单状态为已支付
				order.setUpdateTime(new Date());
				order.setOrderStatus(Order.STATUS_PAID);
				int i = orderMapper.updateByPrimaryKeySelective(order);
				if (i == 1) {
					List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByOrderNo(outTradeNo);
					for (OrderGoods orderGoods : orderGoodsList) {
						String gid = orderGoods.getOgdGid();
						Integer gnum = orderGoods.getOgdGnum();
						Goods goods = goodsMapper.selectByPrimaryKey(gid);
						// 销量增加
						Integer sales = goods.getGoodsSales();
						goods.setGoodsSales(sales + gnum);
						// 库存减少
						Integer inventory = goods.getGoodsInventory();
						goods.setGoodsInventory(inventory - gnum);
						goodsMapper.updateByPrimaryKeySelective(goods);
					}
					Coupon coupon = couponMapper.selectByPrimaryKey(order.getCouponId());
					if (coupon != null) {
						coupon.setCouponStatus(Coupon.STATUS_USED);
						couponMapper.updateByPrimaryKeySelective(coupon);
					}
					Consume consume = new Consume();
					consume.setConsumeId(Utils.UUID());
					consume.setConsumeUid(order.getOrderUid());
					consume.setConsumeNo(order.getOrderNo());
					consume.setConsumeCoin(order.getOrderPrice());
					consume.setCreateTime(new Date());
					consumeMapper.insertSelective(consume);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public JSONObject refund(WxRefundReq wxRefund) throws Exception {
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		// 微信的应用ID
		packageParams.put("appid", APPID);
		// 微信支付商户信息号
		packageParams.put("mch_id", MCH_ID);
		// 随机字符串，不长于32位
		packageParams.put("nonce_str", Utils.UUID());
		// 订单号
		packageParams.put("out_trade_no", wxRefund.getOrderNo());
		// 退款单号
		packageParams.put("out_refund_no", Utils.getOrderNo());
		// 订单总金额
		packageParams.put("total_fee", wxRefund.getTotalFee());
		// 退款总金额
		packageParams.put("refund_fee", wxRefund.getRefundFee());
		// 调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
		String stringA = Utils.formatUrlMap(packageParams, false, false);
		// sign签名
		String sign = Utils.MD5(stringA + "&key=" + KEY).toUpperCase();
		packageParams.put("sign", sign);
		// 将参数 编写XML格式打包要发送的XML
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("<xml>");
		paramBuffer.append("<appid>" + packageParams.get("appid") + "</appid>");
		paramBuffer.append("<mch_id>" + packageParams.get("mch_id") + "</mch_id>");
		paramBuffer.append("<nonce_str>" + packageParams.get("nonce_str") + "</nonce_str>");
		paramBuffer.append("<sign>" + sign + "</sign>");
		paramBuffer.append("<out_trade_no>" + packageParams.get("out_trade_no") + "</out_trade_no>");
		paramBuffer.append("<out_refund_no>" + packageParams.get("out_refund_no") + "</out_refund_no>");
		paramBuffer.append("<total_fee>" + packageParams.get("total_fee") + "</total_fee>");
		paramBuffer.append("<refund_fee>" + packageParams.get("refund_fee") + "</refund_fee>");
		paramBuffer.append("<transaction_id></transaction_id>");
		paramBuffer.append("</xml>");
		String ret = doRefund(REFUND, paramBuffer.toString());
		if (!StringUtils.isEmpty(ret)) {
			Map<?, ?> map = Utils.parseXmlToMap(ret);
			for (Object str : map.keySet()) {
				String string = (String) map.get(str);
				System.out.println(string);
				if ("FAIL".equals(string)) {
					return GatewayProtocol.protocolBody(ret, string, string);
				}
			}
			String orderNo = (String) map.get("out_trade_no");
			Order order = orderMapper.selectByPrimaryKey(orderNo);
			order.setUpdateTime(new Date());
			order.setOrderStatus(Order.STATUS_REFUNDED);
			int i = orderMapper.updateByPrimaryKeySelective(order);
			if (i != 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, "FAIL", "订单状态改变失败");
			} else {
				Refund refund = new Refund();
				refund.setRefundId(Utils.UUID());
				refund.setRefundCoin(wxRefund.getRefundFee());
				refund.setRefundType(Refund.TYPE_WECHAT);
				refund.setRefundUid(order.getOrderUid());
				refund.setCreateTime(new Date());
				refundMapper.insertSelective(refund);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, "SUCCESS", "退款完成");
			}
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, "FAIL", "RET IS EMPTY");
	}

	private static String doRefund(String url, String data) throws Exception {
		KeyStore keystore = KeyStore.getInstance("PKCS12");
		FileInputStream is = new FileInputStream(new File("D://apiclient_cert/apiclient_cert.p12"));
		try {
			// 这里写密码，默认是你的MCHID
			keystore.load(is, "1511963621".toCharArray());
		} finally {
			is.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keystore, "1511963621".toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url);
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	@Override
	public JSONObject paymentVip(HttpServletRequest request, VipPaymentReq vipPayment) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String vipNo = Utils.getVipNo();
		double totalFee = Double.parseDouble(vipPayment.getTotalFee());
		totalFee = totalFee * 100;
		// 微信的应用ID
		map.put("appid", APPID);
		// 微信支付商户信息号
		map.put("mch_id", MCH_ID);
		// 随机字符串，不长于32位
		map.put("nonce_str", Utils.UUID());
		// 商品描述
		map.put("body", "body");
		// 商户系统内部的订单号，32个字符内、可包含字母
		map.put("out_trade_no", vipNo);
		// 订单总金额，单位为分
		map.put("total_fee", (int) totalFee + "");
		// IP地址
		map.put("spbill_create_ip", Utils.getIpAddr(request));
		// 接收微信支付异步通知回调地址，通知URL必须为直接可访问的URL，不能携带参数
		map.put("notify_url", vipPayment.getNotifyUrl());
		// 支付类型
		map.put("trade_type", "JSAPI");
		// 微信端下的唯一用户标识
		map.put("openid", vipPayment.getOpenId());
		// 调用逻辑传入参数按照字段名的 ASCII 码从小到大排序（字典序）
		String stringA = Utils.formatUrlMap(map, false, false);
		String sign = Utils.MD5(stringA + "&key=" + KEY).toUpperCase();
		// sign签名，第一次随机签名
		map.put("sign", sign);
		// 将参数 编写XML格式 打包要发送的XML
		StringBuffer paramBuffer = new StringBuffer();
		paramBuffer.append("<xml>");
		paramBuffer.append("<appid>" + map.get("appid") + "</appid>");
		paramBuffer.append("<mch_id>" + map.get("mch_id") + "</mch_id>");
		paramBuffer.append("<nonce_str>" + map.get("nonce_str") + "</nonce_str>");
		paramBuffer.append("<sign>" + sign + "</sign>");
		paramBuffer.append("<body>" + map.get("body") + "</body>");
		paramBuffer.append("<out_trade_no>" + map.get("out_trade_no") + "</out_trade_no>");
		paramBuffer.append("<total_fee>" + map.get("total_fee") + "</total_fee>");
		paramBuffer.append("<spbill_create_ip>" + map.get("spbill_create_ip") + "</spbill_create_ip>");
		paramBuffer.append("<notify_url>" + map.get("notify_url") + "</notify_url>");
		paramBuffer.append("<trade_type>" + map.get("trade_type") + "</trade_type>");
		paramBuffer.append("<openid>" + map.get("openid") + "</openid>");
		paramBuffer.append("</xml>");
		// 发送POST请求，获得数据包ID（这有个注意的地方 如果不转码成ISO8859-1则会告诉你body不是UTF8编码）
		Map<String, String> reqMap = Utils.doXMLParse(Utils.getRemotePortData(UNIFIEDORDER, paramBuffer.toString()));
		System.out.println(reqMap);
		// 返回给小程序端需要的参数
		Map<String, String> callbackMap = new HashMap<String, String>();
		if ("SUCCESS".equals(reqMap.get("result_code")) && "SUCCESS".equals(reqMap.get("return_code"))) {
			// 返回的预付单信息
			String prepay_id = (String) reqMap.get("prepay_id");
			Long timeStamp = System.currentTimeMillis() / 1000;
			callbackMap.put("appId", APPID);
			callbackMap.put("timeStamp", timeStamp + "");
			callbackMap.put("nonceStr", vipPayment.getNotifyUrl());
			callbackMap.put("signType", "MD5");
			callbackMap.put("package", "prepay_id=" + prepay_id);
			String stringB = Utils.formatUrlMap(callbackMap, false, false);
			String signB = Utils.MD5(stringB + "&key=" + KEY).toUpperCase();
			// sign签名，第二次随机签名
			callbackMap.put("sign", signB);

			// 查询用户当前是否已经是会员
			if (vipMapper.selectByUid(Utils.getAuthUserId(request)) != null) {
				JSONObject json = new JSONObject();
				json.put("result", "您已是VIP");
				return json;
			}

			// 创建VIP订单
			Vip vip = new Vip();
			vip.setVipUid(Utils.getAuthUserId(request));
			vip.setVipId(vipNo);
			vip.setVipType(vipPayment.getVipType());
			vip.setVipDiscount(vipPayment.getVipDiscount());
			vip.setVipPrice(vipPayment.getTotalFee());
			vip.setVipStart(new Date());
			vip.setVipEnd(vip.getVipEnd());
			vip.setCreateTime(new Date());
			vipMapper.insertSelective(vip);

			return GatewayProtocol.protocolBody(callbackMap);
		}
		return null;
	}

	@Override
	public boolean callBackVip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String inputLine = "";
		String notityXml = "";
		while ((inputLine = request.getReader().readLine()) != null) {
			notityXml += inputLine;
		}
		request.getReader().close();
		Map<String, String> map = Utils.doXMLParse(notityXml);
		if ("SUCCESS".equals(map.get("result_code"))) {
			String outTradeNo = map.get("out_trade_no");
			Vip vip = vipMapper.selectByPrimaryKey(outTradeNo);
			if (Vip.STATUS_UNPAID.equals(vip.getVipStatus())) {
				// 更新订单状态为已支付
				vip.setVipStatus(Vip.STATUS_PAID);
				int i = vipMapper.updateByPrimaryKeySelective(vip);
				if (i == 1) {
					return true;
				}
			}
		}
		return false;
	}

}