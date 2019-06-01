package com.store.module.session;

import javax.servlet.http.HttpServletRequest;

public class WxSession extends Session {

	public static void setWxOpenid(HttpServletRequest req, String openid) {
		setSession(req, SessionConstants.SESSION_KEY_OPENID, openid);
	}

	public static String getWxOpenid(HttpServletRequest req) {
		return (String) getSession(req, SessionConstants.SESSION_KEY_OPENID);
	}

	public static void setWxSessionKey(HttpServletRequest req, String session_key) {
		setSession(req, SessionConstants.SESSION_KEY_WXSESSIONKEY, session_key);
	}

	public static String getWxSessionKey(HttpServletRequest req) {
		return (String) getSession(req, SessionConstants.SESSION_KEY_WXSESSIONKEY);
	}

}