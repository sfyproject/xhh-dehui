package com.store.model.req;

import org.hibernate.validator.constraints.NotBlank;

public class WxLoginReq {

	@NotBlank
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}