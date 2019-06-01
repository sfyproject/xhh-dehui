package com.store.model.req;

import org.hibernate.validator.constraints.NotBlank;

public class ForgetPasswordReq {
	
	@NotBlank
	private String code;
	@NotBlank
	private String password;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}