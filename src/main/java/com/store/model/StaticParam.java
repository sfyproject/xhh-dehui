package com.store.model;

import java.util.Date;

public class StaticParam {

	private String staticParamName;
	private String staticParamValue;
	private String staticParamValueDesc;
	private Integer staticParamValueOrder;
	private String userId;
	private Date createDate;

	public String getStaticParamName() {
		return staticParamName;
	}

	public void setStaticParamName(String staticParamName) {
		this.staticParamName = staticParamName;
	}

	public String getStaticParamValue() {
		return staticParamValue;
	}

	public void setStaticParamValue(String staticParamValue) {
		this.staticParamValue = staticParamValue;
	}

	public String getStaticParamValueDesc() {
		return staticParamValueDesc;
	}

	public void setStaticParamValueDesc(String staticParamValueDesc) {
		this.staticParamValueDesc = staticParamValueDesc;
	}

	public Integer getStaticParamValueOrder() {
		return staticParamValueOrder;
	}

	public void setStaticParamValueOrder(Integer staticParamValueOrder) {
		this.staticParamValueOrder = staticParamValueOrder;
	}



	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
