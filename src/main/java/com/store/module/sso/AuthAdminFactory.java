package com.store.module.sso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.store.model.Admin;

@Service
public class AuthAdminFactory {

	public static AdminUser create(Admin admin) {
		return new AdminUser(admin.getAdminUsername(), admin.getAdminPassword(),
				mapToGrantedAuthorities(getUserRoles()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private static List<String> getUserRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add("ADMIN");
		return roles;
	}

}