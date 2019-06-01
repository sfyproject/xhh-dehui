package com.store.user.apicontroller.cart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Cart;
import com.store.model.Goods;
import com.store.model.req.IdsReq;
import com.store.model.resp.CartResp;
import com.store.model.resp.GatewayProtocol;
import com.store.service.CartService;
import com.store.service.GimgService;
import com.store.service.GoodsService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private GimgService gimgService;

	/**
	 * 加入购物车
	 * 
	 * @param req
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONObject add(HttpServletRequest req, @RequestBody Cart cart) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			String gid = cart.getCartGid();
			if (Utils.isNullStr(gid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "商品ID为空");
			}
			Goods goods = goodsService.selectByPrimaryKey(gid);
			if (goods == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "没有该商品");
			}
			ArrayList<String> ids = new ArrayList<>();
			ids.add(gid);
			List<CartResp> cartGoods = cartService.selectByUidAndGid(uid, ids);
			if (!Utils.isNullList(cartGoods)) {
				for (CartResp cartGood : cartGoods) {
					cart.setCartId(cartGood.getCartId());
					cart.setCartNum(cartGood.getCartNum() + cart.getCartNum());
					int row = cartService.updateByPrimaryKeySelective(cart);
					if (row == 1) {
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
					} else {
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
					}
				}
			}
			cart.setCartId(Utils.UUID());
			cart.setCartGid(gid);
			cart.setCartUid(uid);
			cart.setCreateTime(new Date());
			int row = cartService.insertSelective(cart);
			if (row == 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 编辑购物车
	 * 
	 * @param req
	 * @param cart
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public JSONObject edit(HttpServletRequest req, @RequestBody Cart cart) {
		try {
			if (Utils.isNullStr(cart.getCartId())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "主键为空");
			}
			if (cart.getCartNum() == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "数量为空");
			}
			int i = cartService.updateByPrimaryKeySelective(cart);
			if (i == 1) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 删除购物车
	 * 
	 * @param req
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public JSONObject del(HttpServletRequest req, @RequestBody IdsReq<String> idsReq) {
		try {
			List<String> ids = idsReq.getIds();
			if (Utils.isNullList(ids)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "主键为空");
			}
			int row = cartService.deleteBatch(ids);
			if (row == ids.size()) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 清空购物车
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public JSONObject clear(HttpServletRequest req) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			int count = cartService.selectGoodsCount(uid);
			int row = cartService.deleteByUid(uid);
			if (count == row) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 查看购物车
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public JSONObject show(HttpServletRequest req) {
		try {
			String uid = Utils.getAuthUserId(req);
			if (Utils.isNullStr(uid)) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户ID为空");
			}
			List<CartResp> list = cartService.selectByUidAndGid(uid, null);
			if (!Utils.isNullList(list)) {
				for (CartResp cartResp : list) {
					List<String> imgs = gimgService.selectByGid(cartResp.getCartGid());
					if (!Utils.isNullList(imgs)) {
						cartResp.setGoodsImg(imgs.get(0));
					}
				}
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}