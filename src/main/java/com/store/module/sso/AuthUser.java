package com.store.module.sso;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthUser implements UserDetails {

	private static final long serialVersionUID = -5365073915992397385L;

	private String userId;
	private String userName;
	private String userPassword;
	private Collection<? extends GrantedAuthority> authorities;

	public AuthUser(String string, String userName, String userPassword,
			Collection<? extends GrantedAuthority> authorities) {

		this.userId = string;
		this.userName = userName;
		this.userPassword = userPassword;
		this.authorities = authorities;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return userPassword;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}