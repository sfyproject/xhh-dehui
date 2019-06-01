package com.store.module.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

public class AuthSession extends Session {

	private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	public static void setSecurityContext(HttpServletRequest req, SecurityContext val) {
		setSession(req, SPRING_SECURITY_CONTEXT, val);
	}

	public static SecurityContext getSecurityContext(HttpServletRequest req) {
		return (SecurityContext) getSession(req, SPRING_SECURITY_CONTEXT);
	}
}