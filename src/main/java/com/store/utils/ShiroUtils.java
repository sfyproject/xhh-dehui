package com.store.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.store.model.Admin;


public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static Admin getUser() {
		return (Admin)getSubjct().getPrincipal();
	}
	public static String getUserId() {
		return getUser().getAdminId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}
