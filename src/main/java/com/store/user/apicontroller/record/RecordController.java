package com.store.user.apicontroller.record;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.Consume;
import com.store.model.Recharge;
import com.store.model.Refund;
import com.store.model.Share;
import com.store.model.resp.GatewayProtocol;
import com.store.service.ConsumeService;
import com.store.service.RechargeService;
import com.store.service.RefundService;
import com.store.service.ShareService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/record")
public class RecordController {

	@Autowired
	private RefundService refundService;

	@Autowired
	private ConsumeService consumeService;

	@Autowired
	private RechargeService rechargeService;
	
	@Autowired
	private ShareService shareService;

	/**
	 * 退款记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	public JSONObject refund(HttpServletRequest req) {
		try {
			List<Refund> list = refundService.selectByUid(Utils.getAuthUserId(req));
			if (list != null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 消费记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/consume", method = RequestMethod.GET)
	public JSONObject consume(HttpServletRequest req) {
		try {
			List<Consume> list = consumeService.selectByUid(Utils.getAuthUserId(req));
			if (list != null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 充值记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/recharge", method = RequestMethod.GET)
	public JSONObject recharge(HttpServletRequest req) {
		try {
			List<Recharge> list = rechargeService.selectByUid(Utils.getAuthUserId(req));
			if (list != null) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, list, "SUCCESS");
			} else {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "FAIL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}
	
	/**
	 * 分享记录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/share", method = RequestMethod.GET)
	public JSONObject share(HttpServletRequest req) {
		try {
			List<Share> list = shareService.selectByUid(Utils.getAuthUserId(req));
			if (list != null) {
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