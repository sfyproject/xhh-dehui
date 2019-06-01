package com.store.module.session;

import javax.servlet.http.HttpServletRequest;

public class Session {

	public static void setSession(HttpServletRequest req, String key, Object val) {
		req.getSession().setAttribute(key, val);
	}

	public static Object getSession(HttpServletRequest req, String key) {
		return req.getSession().getAttribute(key);
	}

	public static void clearSession(HttpServletRequest req, String key) {
		req.getSession().removeAttribute(key);
	}

	public static void clearAllSession(HttpServletRequest req) {
		req.getSession().invalidate();
	}
}