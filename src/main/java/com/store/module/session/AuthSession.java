package com.store.module.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;

import com.store.module.sso.AuthUser;

public class AuthSession extends Session {

	private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";

	public static void setSecurityContext(HttpServletRequest req, SecurityContext val) {
		setSession(req, SPRING_SECURITY_CONTEXT, val);
	}

	public static SecurityContext getSecurityContext(HttpServletRequest req) {
		return (SecurityContext) getSession(req, SPRING_SECURITY_CONTEXT);
	}

	public static void clearSecurityContext(HttpServletRequest req) {
		clearSession(req, SPRING_SECURITY_CONTEXT);
	}

	public static AuthUser getAuthUser(HttpServletRequest req) {
		SecurityContext securityContext = getSecurityContext(req);
		AuthUser authUser = (AuthUser) securityContext.getAuthentication().getPrincipal();
		return authUser;
	}

}
