package com.store.model.req;

import org.hibernate.validator.constraints.NotBlank;

public class LoginReq {

	private String userPhone;
	private String code;

	@NotBlank
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

}