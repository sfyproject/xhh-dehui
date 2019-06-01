package com.store.module.sso;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdminUser implements UserDetails {

	private static final long serialVersionUID = -5365073915992397385L;

	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public AdminUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {

		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public String getUserName() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUsername() {
		return username;
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