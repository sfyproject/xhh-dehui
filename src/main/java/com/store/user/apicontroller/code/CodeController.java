package com.store.user.apicontroller.code;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.alibaba.fastjson.JSONObject;
import com.store.model.User;
import com.store.model.resp.GatewayProtocol;
import com.store.module.session.Session;
import com.store.module.session.SessionConstants;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/code")
public class CodeController {

	/**
	 * 获取短信验证码
	 * 
	 * @param user
	 * @param req
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.POST)
	public JSONObject getCode(HttpServletRequest req, @RequestBody User user) throws AuthenticationException {
		try {
			String code = Utils.getSmsCode();
			String sessionId = req.getSession().getId();
			System.out.println("code="+sessionId);
			SendSmsResponse smsResponse = Utils.sendSms(user.getUserPhone(), "SMS_143275222",
					"{\"code\":" + code + "}");
			if (smsResponse == null || !"OK".equals(smsResponse.getCode())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, "", "FAIL");
			} else {
				Session.setSession(req, SessionConstants.SESSION_LOGIN_SMSCODE, code);
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, code, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

}