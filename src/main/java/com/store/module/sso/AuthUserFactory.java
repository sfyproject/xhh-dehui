package com.store.module.sso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.store.model.User;

@Service
public class AuthUserFactory {

	public static AuthUser create(User user) {

		String password = "$2a$10$TcRuCwoL/Y/XeRlJBCG4YOhiSTpPbiuRwjlIAZ42vuKZqWzas.de2";

		return new AuthUser(user.getUserId(), user.getUserPhone(), password, mapToGrantedAuthorities(getUserRoles()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private static List<String> getUserRoles() {
		List<String> roles = new ArrayList<String>();
		roles.add("USER");
		return roles;
	}

}