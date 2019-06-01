package com.store.module.sso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.model.Admin;
import com.store.model.User;
import com.store.service.AdminService;
import com.store.service.UserService;
import com.store.utils.Utils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (Utils.isNullStr(username)) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}

		if (username.startsWith("U")) {
			username = username.substring(1, username.length());
			User user = userService.loadUserByUsername(username);
			if (user == null) {
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			}
			return AuthUserFactory.create(user);
		}

		if (username.startsWith("A")) {
			username = username.substring(1, username.length());
			Admin admin = adminService.selectByPrimaryKey(username);
			if (admin == null) {
				throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
			}
			return AuthAdminFactory.create(admin);
		}

		return null;
	}

}