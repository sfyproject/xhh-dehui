package com.store.user.apicontroller.sso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.store.model.User;
import com.store.model.req.ForgetPasswordReq;
import com.store.model.req.LoginReq;
import com.store.model.req.WxLoginReq;
import com.store.model.resp.GatewayProtocol;
import com.store.model.resp.LoginResp;
import com.store.module.session.AuthSession;
import com.store.module.session.Session;
import com.store.module.session.SessionConstants;
import com.store.module.session.WxSession;
import com.store.module.wechat.WxLoginResp;
import com.store.module.wechat.WxSnsService;
import com.store.service.UserService;
import com.store.utils.Utils;

@RestController
@RequestMapping("/user/api/sso")
public class SsoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private WxSnsService wxSnsService;

	@Value("${url.c8.path}")
	private String C8URL;

	/**
	 * 注册且登录
	 * 
	 * @param loginReq
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public JSONObject register(@RequestBody @Valid LoginReq loginReq, HttpServletRequest req) {
		try {
			
			String sessionId = req.getSession().getId();
			
			System.out.println("注册：" + sessionId);
			String userPhone = loginReq.getUserPhone();
			String wxOpenid = WxSession.getWxOpenid(req);
			String wxSessionKey = WxSession.getWxSessionKey(req);
			String code = (String) Session.getSession(req, SessionConstants.SESSION_LOGIN_SMSCODE);
			if (Utils.isNullStr(code) || !code.equals(loginReq.getCode())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "短信验证码错误");
			}
			User user = userService.loadUserByUsername(userPhone);
			if (null != user) {
				userService.updateWxOpenidAndwxSessionKey(user.getUserId(), wxOpenid, wxSessionKey);
			} else {
				user = new User();
				user.setUserId(Utils.UUID());
				user.setUserPhone(userPhone);
				user.setUserOpenid(wxOpenid);
				user.setUserSessionkey(wxSessionKey);
				user.setCreateTime(new Date());
				int i = userService.insertSelective(user);
				if (i == 1) {
					String host = C8URL;
					String path = "/dehui/api/order/reg";
					String method = "POST";
					HashMap<String, String> headers = new HashMap<>();
					headers.put("Access-Control-Allow-Origin", "*");
					Map<String, String> querys = new HashMap<>();
					querys.put("phone", userPhone);
					HttpResponse doPost = Utils.doPost(host, path, method, headers, querys, new HashMap<>());
					HttpEntity entity = doPost.getEntity();
					com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject
							.parseObject(EntityUtils.toString(entity));
					if ("1".equals(json.get("ret"))) {
						return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "C8货滴注册失败");
					}
				}
			}
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("U" + userPhone,
					"phw@112233@phw");
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			AuthSession.setSecurityContext(req, SecurityContextHolder.getContext());
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, "/", "登录成功");
		} catch (UsernameNotFoundException une) {
			return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户名不存在，请注册后登录。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "用户名或密码错误，请查证后重试。");
	}

	/**
	 * 登录
	 * 
	 * @param wxLoginReq
	 * @param req
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
	public JSONObject wxLogin(@RequestBody @Valid WxLoginReq wxLoginReq, HttpServletRequest req)
			throws AuthenticationException {
		try {
			String sessionId = req.getSession().getId();
			
			System.out.println("登录：" + sessionId);

			WxLoginResp wxLogin = wxSnsService.wxLogin(wxLoginReq.getCode());
			if (null == wxLogin || null == wxLogin.getOpenid() || null == wxLogin.getSession_key()) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "");
			}
			LoginResp loginResp = new LoginResp();
			User user = userService.loadUserByWxOpenid(wxLogin.getOpenid());
			if (null == user) {
				loginResp.setCode("0001");
				loginResp.setSessionId(sessionId);
				WxSession.setWxOpenid(req, wxLogin.getOpenid());
				WxSession.setWxSessionKey(req, wxLogin.getSession_key());
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, loginResp, "SUCCESS");
			} else {
				loginResp.setCode("0000");
				loginResp.setSessionId(sessionId);
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						"U" + user.getUserPhone(), "phw@112233@phw");
				Authentication authentication = authenticationManager.authenticate(authRequest);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				AuthSession.setSecurityContext(req, SecurityContextHolder.getContext());
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_SUCCESS, loginResp, "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "EXCEPTION");
	}

	/**
	 * 忘记密码
	 * 
	 * @param forgetPasswordReq
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public JSONObject forgetPassword(@RequestBody @Valid ForgetPasswordReq forgetPasswordReq, HttpServletRequest req) {
		try {
			String code = (String) Session.getSession(req, SessionConstants.SESSION_LOGIN_SMSCODE);
			if (Utils.isNullStr(code) || !code.equals(forgetPasswordReq.getCode())) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "短信验证码错误");
			}
			String password = forgetPasswordReq.getPassword();
			if (password.length() != 6) {
				return GatewayProtocol.protocolBody(GatewayProtocol.RET_FAIL, null, "密码必须为6位");
			}
			User user = userService.selectByPrimaryKey(Utils.getAuthUserId(req));
			if (user != null) {
				user.setUserPassword(password);
				int i = userService.updateByPrimaryKeySelective(user);
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

}