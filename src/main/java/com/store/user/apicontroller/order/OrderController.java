package com.store.user.apicontroller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.store.dao.RefundMapper;
import com.store.model.Consume;
import com.store.model.Coupon;
import com.store.model.Goods;
import com.store.model.Order;
import com.store.model.OrderGoods;
import com.store.model.Refund;
import com.store.model.User;
import com.store.model.Vip;
import com.store.model.req.IdsReq;
import com.store.model.req.OrderReq;
import com.store.model.req.PageReq;
import com.store.model.resp.CartResp;
import com.store.model.resp.ConfirmResp;
import com.store.model.resp.GatewayProtocol;
import com.store.module.session.Session;
import com.store.module.session.SessionConstants;
import com.store.service.CartService;
import com.store.service.ConsumeService;
import com.store.service.CouponService;
import com.store.service.GimgService;
import com.store.service.GoodsService;
import com.store.service.OrderGoodsService;
import com.store.service.OrderService;
import com.store.service.UserService;
import com.store.service.VipService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private VipService vipService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GimgService gimgService;

	@Autowired
	private ConsumeService consumeService;

	@Autowired
	private RefundMapper refundMapper;

	@Autowired
	private OrderGoodsService orderGoodsService;

	/**
	 * 构建订单
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	public JSONObject build(HttpServletRequest req, @RequestBody Order order) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			String orderNo = Utils.getOrderNo();
			List<String> ids = order.getIds();
			HttpSession session = req.getSession();
			List<CartResp> cartGoods = cartService.selectByUidAndGid(uid, ids);
			ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
			if (cartGoods != null) {
				for (CartResp cartGood : cartGoods) {
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdId(Utils.UUID());
					orderGoods.setOgdGnum(cartGood.getCartNum());
					orderGoods.setOgdGid(cartGood.getCartGid());
					orderGoods.setOgdNo(orderNo);
					orderGoodsList.add(orderGoods);
				}
			} else {
				List<Goods> goodsList = goodsService.selectByGids(ids);
				for (Goods goods : goodsList) {
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdId(Utils.UUID());
					orderGoods.setOgdGnum(order.getNum());
					orderGoods.setOgdGid(goods.getGoodsId());
					orderGoods.setOgdNo(orderNo);
					orderGoodsList.add(orderGoods);
				}
			}
			if (order.getOrderPrice() == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单价格为空");
			}
			order.setOrderUid(uid);
			order.setOrderNo(orderNo);
			order.setOrderName((String) session.getAttribute("name"));
			order.setOrderPhone((String) session.getAttribute("phone"));
			order.setOrderAddress((String) session.getAttribute("address"));
			order.setOrderGroup(Order.NO_GROUP);
			order.setOrderStatus(Order.STATUS_UNPAID);
			order.setUpdateTime(new Date());
			order.setCreateTime(new Date());
			int i = orderService.insert(order);
			if (i == 1) {
				i = orderGoodsService.batchInsert(orderGoodsList);
				if (i != orderGoodsList.size()) {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单商品插入失败");
				}
				if (cartGoods != null) {
					if (cartService.deleteBatch(ids) != ids.size()) {
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "购物车删除失败");
					}
				}
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, orderNo, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 确认订单
	 * 
	 * @param req
	 * @param idsReq
	 * @return
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public JSONObject confirm(HttpServletRequest req, @RequestBody IdsReq<String> idsReq) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			List<String> ids = idsReq.getIds();
			List<CartResp> cartGoods = cartService.selectByUidAndGid(uid, ids);
			BigDecimal totalPrice = new BigDecimal(0);
			ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
			if (!Utils.isNullList(cartGoods)) {
				for (CartResp cartGood : cartGoods) {
					Goods goods = goodsService.selectByPrimaryKey(cartGood.getCartGid());
					List<String> imgs = gimgService.selectByGid(cartGood.getCartGid());
					String img = imgs.get(0);
					String price = cartGood.getGoodsPrice();
					Integer num = cartGood.getCartNum();
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdGnum(num);
					orderGoods.setGoodsImage(img);
					orderGoods.setOgdGid(goods.getGoodsId());
					orderGoods.setGoodsTitle(goods.getGoodsTitle());
					orderGoods.setGoodsPrice(goods.getGoodsPrice());
					orderGoodsList.add(goodsPrice(goods, orderGoods));
					BigDecimal goodPrice = new BigDecimal(price).multiply(new BigDecimal(num));
					totalPrice = totalPrice.add(goodPrice);
				}
			} else {
				List<Goods> goodsList = goodsService.selectByGids(ids);
				for (Goods goods : goodsList) {
					List<String> imgs = gimgService.selectByGid(goods.getGoodsId());
					String img = imgs.get(0);
					OrderGoods orderGoods = new OrderGoods();
					orderGoods.setOgdGnum(1);
					orderGoods.setGoodsImage(img);
					orderGoods.setOgdGid(goods.getGoodsId());
					orderGoods.setGoodsTitle(goods.getGoodsTitle());
					orderGoodsList.add(goodsPrice(goods, orderGoods));
					totalPrice = totalPrice.add(new BigDecimal(goods.getGoodsPrice()));
				}
			}
			if (totalPrice.doubleValue() == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "价格为零");
			}
			Vip vip = vipService.selectByUid(uid);
			if (vip != null) {
				totalPrice = totalPrice.multiply(new BigDecimal(vip.getVipDiscount()));
			}
			User user = userService.selectByPrimaryKey(uid);
			req.getSession().setAttribute("name", user.getUserName());
			req.getSession().setAttribute("phone", user.getUserPhone());
			req.getSession().setAttribute("address", user.getUserAddress());
			ConfirmResp confirmResp = new ConfirmResp();
			confirmResp.setName(user.getUserName());
			confirmResp.setPhone(user.getUserPhone());
			confirmResp.setAddress(user.getUserAddress());
			confirmResp.setOrderGoodsList(orderGoodsList);
			confirmResp.setTotalPrice(totalPrice);
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, confirmResp, "SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 商品价格
	 * 
	 * @param goods
	 * @param orderGoods
	 * @return
	 */
	private OrderGoods goodsPrice(Goods goods, OrderGoods orderGoods) {
		if (Utils.isNullStr(goods.getGoodsLabel())) {
			orderGoods.setGoodsPrice(goods.getGoodsPrice());
			// 3：秒杀
		} else if ("3".equals(goods.getGoodsLabel())) {
			orderGoods.setGoodsPrice(goods.getGoodsSecond());
			// 2：拼团
		} else if ("2".equals(goods.getGoodsLabel())) {
			orderGoods.setGoodsPrice(goods.getGoodsGroup());
		}
		return orderGoods;
	}

	/**
	 * 用户详细订单列表
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public JSONObject list(HttpServletRequest req, @RequestBody Order order) {
		try {
			PageReq pageReq = new PageReq();
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			String status = order.getOrderStatus();
			List<Order> orderList = orderService.selectByUidAndStatus(uid, status, Order.NO_GROUP);
			for (Order o : orderList) {
				String orderNo = o.getOrderNo();
				List<OrderGoods> orderGoods = orderGoodsService.selectByOrderNo(orderNo);
				for (OrderGoods goods : orderGoods) {
					List<String> images = gimgService.selectByGid(goods.getGoodsId());
					if (!Utils.isNullList(images)) {
						o.setImage(images.get(0));
					}
				}
				o.setGoodsList(orderGoods);
			}
			if (orderList == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				PageInfo<Order> pageInfo = new PageInfo<>(orderList, 10);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 拼团订单
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/groupBuying", method = RequestMethod.POST)
	public JSONObject groupBuying(HttpServletRequest req, @RequestBody Order order) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			List<String> ids = order.getIds();
			if (Utils.isNullList(ids)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品ID为空");
			}
			Goods goods = goodsService.selectByPrimaryKey(ids.get(0));
			if (goods == null || goods.getGoodsInventory() < 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品库存不足");
			}
			HttpSession session = req.getSession();
			String orderNo = Utils.getOrderNo();
			ArrayList<OrderGoods> orderGoodsList = new ArrayList<>();
			OrderGoods orderGoods = new OrderGoods();
			orderGoods.setOgdId(Utils.UUID());
			orderGoods.setOgdGnum(1);
			orderGoods.setOgdGid(ids.get(0));
			orderGoods.setOgdNo(orderNo);
			orderGoodsList.add(orderGoods);
			int i = orderGoodsService.batchInsert(orderGoodsList);
			if (i != orderGoodsList.size()) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单商品插入失败");
			}
			order.setOrderUid(uid);
			order.setOrderNo(orderNo);
			order.setOrderGroup(Order.IS_GROUP);
			order.setOrderName((String) session.getAttribute("name"));
			order.setOrderPhone((String) session.getAttribute("phone"));
			order.setOrderAddress((String) session.getAttribute("address"));
			order.setOrderStatus(Order.STATUS_UNPAID);
			order.setGroupStatus(Order.GROUP_STATUS_NO);
			order.setUpdateTime(new Date());
			order.setCreateTime(new Date());
			i = orderService.insert(order);
			if (i == 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, order, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 用户详细拼团订单列表
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/groupOrders", method = RequestMethod.POST)
	public JSONObject groupOrders(HttpServletRequest req, @RequestBody Order order) {
		try {
			PageReq pageReq = new PageReq();
			PageHelper.startPage(pageReq.getPage(), pageReq.getSize());
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			String status = order.getOrderStatus();
			List<Order> orderList = orderService.selectByUidAndStatus(uid, status, Order.IS_GROUP);
			for (Order o : orderList) {
				String orderNo = o.getOrderNo();
				List<OrderGoods> orderGoods = orderGoodsService.selectByOrderNo(orderNo);
				o.setGoodsList(orderGoods);
			}
			if (orderList == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				PageInfo<Order> pageInfo = new PageInfo<>(orderList, 10);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, pageInfo, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 统计参与拼团人数
	 * 
	 * @param req
	 * @param goods
	 * @return
	 */
	@RequestMapping(value = "/groupCount", method = RequestMethod.POST)
	public JSONObject groupCount(HttpServletRequest req, @RequestBody Goods goods) {
		try {
			Integer count = orderService.selectGroupCount(goods.getGoodsId());
			if (count == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, count, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 删除订单
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(HttpServletRequest req, @RequestBody Order order) {
		try {
			String orderNo = order.getOrderNo();
			order = orderService.selectByPrimaryKey(orderNo);
			if (order == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有该订单");
			}
			if (Order.STATUS_PAID.equals(order.getOrderStatus())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单已支付");
			}
			if (Order.STATUS_SHIPPED.equals(order.getOrderStatus())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单已发货");
			}
			int i = orderService.deleteByPrimaryKey(order.getOrderNo());
			if (i == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 余额退款
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	public JSONObject refund(HttpServletRequest req, @RequestBody Order order) {
		try {
			String orderNo = order.getOrderNo();
			order = orderService.selectByPrimaryKey(orderNo);
			if (order == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有该订单");
			}
			if (Order.STATUS_SHIPPED.equals(order.getOrderStatus())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单已发货");
			}
			if (Order.STATUS_COMPLETED.equals(order.getOrderStatus())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "订单已完成");
			}
			String price = order.getOrderPrice();
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			String wallet = user.getUserWallet();
			Double newWallet = Double.parseDouble(wallet) + Double.parseDouble(price);
			user.setUserWallet(newWallet.toString());
			int i = userService.updateByPrimaryKeySelective(user);
			if (i == 0) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			} else {
				order.setOrderStatus(Order.STATUS_REFUNDED);
				orderService.updateByPrimaryKeySelective(order);
				Refund refund = new Refund();
				refund.setRefundCoin(price);
				refund.setCreateTime(new Date());
				refund.setRefundId(Utils.UUID());
				refund.setRefundUid(order.getOrderUid());
				refund.setRefundType(Refund.TYPE_BALANCE);
				refundMapper.insertSelective(refund);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 拼团成功
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/groupSuccess", method = RequestMethod.POST)
	public JSONObject groupSuccess(HttpServletRequest req, @RequestBody Order order) {
		try {
			order = orderService.selectByPrimaryKey(order.getOrderNo());
			if (order == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "THE ORDER WAS NOT FOUND.");
			} else {
				order.setGroupStatus(Order.GROUP_STATUS_OK);
				int i = orderService.updateByPrimaryKeySelective(order);
				if (i == 0) {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
				} else {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 确认收货
	 * 
	 * @param req
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public JSONObject complete(HttpServletRequest req, @RequestBody Order order) {
		try {
			String code = (String) Session.getSession(req, SessionConstants.SESSION_LOGIN_SMSCODE);
			if (Utils.isNullStr(code) || !code.equals(order.getCode())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "短信验证码错误");
			}
			order = orderService.selectByPrimaryKey(order.getOrderNo());
			if (order == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "THE ORDER WAS NOT FOUND.");
			} else {
				order.setOrderStatus(Order.STATUS_COMPLETED);
				int i = orderService.updateByPrimaryKeySelective(order);
				if (i == 0) {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
				} else {
					return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 余额支付
	 * 
	 * @param orderReq
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/balancePayment", method = RequestMethod.POST)
	public JSONObject balancePayment(@RequestBody OrderReq orderReq, HttpServletRequest req) {
		try {
			String authUserId = Utils.getAuthUserId(req);
			User user = userService.selectByPrimaryKey(authUserId);
			if (user == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "THE USER WAS NOT FOUND");
			}
			Double price = Double.parseDouble(orderReq.getPrice());
			if (!orderReq.getPassword().equals(user.getUserPassword())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "PASSWORD ERROR, PLEASE RE-ENTER");
			} else if (Double.parseDouble(user.getUserWallet()) < price) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null,
						"SORRY, YOUR CREDIT IS RUNNING LOW");
			} else {
				String orderNo = orderReq.getOrderNo();
				Order order = orderService.selectByPrimaryKey(orderReq.getOrderNo());
				if (order != null) {
					order.setOrderPrice(price.toString());
					order.setOrderStatus(Order.STATUS_PAID);
					int i = orderService.updateByPrimaryKeySelective(order);
					if (i == 1) {
						List<OrderGoods> orderGoodsList = orderGoodsService.selectByOrderNo(orderNo);
						for (OrderGoods orderGoods : orderGoodsList) {
							String gid = orderGoods.getOgdGid();
							Integer gnum = orderGoods.getOgdGnum();
							Goods goods = goodsService.selectByPrimaryKey(gid);
							// 销量增加
							Integer sales = goods.getGoodsSales();
							goods.setGoodsSales(sales + gnum);
							// 库存减少
							Integer inventory = goods.getGoodsInventory();
							goods.setGoodsInventory(inventory - gnum);
							goodsService.updateByPrimaryKeySelective(goods);
						}
						Double wallet = Double.parseDouble(user.getUserWallet());
						user.setUserWallet(String.format("%.2f", wallet - price));
						userService.updateByPrimaryKeySelective(user);
						Coupon coupon = couponService.selectByPrimaryKey(orderReq.getCouponId());
						if (coupon != null) {
							coupon.setCouponStatus(Coupon.STATUS_USED);
							couponService.updateByPrimaryKeySelective(coupon);
						}
						Consume consume = new Consume();
						consume.setConsumeId(Utils.UUID());
						consume.setConsumeUid(authUserId);
						consume.setConsumeNo(orderNo);
						consume.setConsumeCoin(orderReq.getPrice());
						consume.setCreateTime(new Date());
						consumeService.insertSelective(consume);
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}