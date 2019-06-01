package com.store.user.apicontroller.share;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Share;
import com.store.model.User;
import com.store.model.resp.GatewayProtocol;
import com.store.service.ShareService;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/share")
public class ShareController {

	@Autowired
	private ShareService shareService;

	@Autowired
	private UserService userService;

	/**
	 * 用户分享
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/go", method = RequestMethod.GET)
	public JSONObject go(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String authUserId = Utils.getAuthUserId(req);
			User user = userService.selectByPrimaryKey(authUserId);

			int min = 1;
			int max = 5;
			Random random = new Random();
			BigDecimal coin = new BigDecimal(random.nextInt(max) % (max - min + 1) + min).divide(new BigDecimal(100));

			String wallet = user.getUserWallet();
			BigDecimal newWallet = new BigDecimal(wallet).add(coin);
			user.setUserWallet(newWallet.toString());

			int i = userService.updateByPrimaryKeySelective(user);

			if (i == 1) {
				Share share = new Share();
				share.setShareId(Utils.UUID());
				share.setCreateTime(new Date());
				share.setShareUid(authUserId);
				share.setShareCoin(coin.toString());
				share.setShareStatus(Share.STATUS_SUCCESS);
				share.setShareType(Share.TYPE_GENERAL);
				shareService.insertSelective(share);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, coin, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}