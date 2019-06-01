package com.store.user.apicontroller.card;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Card;
import com.store.model.Recharge;
import com.store.model.User;
import com.store.model.resp.GatewayProtocol;
import com.store.module.session.Session;
import com.store.module.session.SessionConstants;
import com.store.service.CardService;
import com.store.service.RechargeService;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/card")
public class CardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private UserService userService;

	@Autowired
	private RechargeService rechargeService;

	/**
	 * 绑定卡片
	 * 
	 * @param req
	 * @param card
	 * @return
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public JSONObject bind(HttpServletRequest req, @RequestBody Card card) {
		try {
			if (Utils.isNullStr(card.getCardId())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "卡号为空");
			}
			Card card2 = cardService.selectByPrimaryKey(card.getCardId());
			if (card2 == null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "卡号错误");
			}
			if (!"-1".equals(card2.getCardUid())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "该卡已绑定");
			}
			String code = (String) Session.getSession(req, SessionConstants.SESSION_LOGIN_SMSCODE);
			if (Utils.isNullStr(code) || !code.equals(card.getCode())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "短信验证码错误");
			}
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			Double balance = Double.parseDouble(user.getUserWallet());
			Double coin = Double.parseDouble(card2.getCardCoin());
			Double newBalance = balance + coin;
			user.setUserWallet(newBalance.toString());
			card2.setCardStatus(Card.STATUS_ALREADYUSED);
			card2.setCardUid(Utils.getAuthUserId(req));
			card2.setCreateTime(new Date());
			card2.setCardCoin("0");
			int e = userService.updateByPrimaryKeySelective(user);
			int i = cardService.updateByPrimaryKeySelective(card2);
			if (i == 1 && e == 1) {
				Recharge recharge = new Recharge();
				recharge.setRechargeId(Utils.UUID());
				recharge.setRechargeCoin(coin.toString());
				recharge.setRechargeType(Recharge.TYPE_CARD);
				recharge.setRechargeStatus(Recharge.STATUS_PAID);
				recharge.setRechargeUid(Utils.getAuthUserId(req));
				recharge.setCreateTime(new Date());
				rechargeService.insertSelective(recharge);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, null, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}