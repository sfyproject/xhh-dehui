package com.store.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Express {

	public static final String HOST = "https://wuliu.market.alicloudapi.com";
	public static final String PATH = "/kdi";
	public static final String APPCODE = "833394c9dab74ec9ba1044532426d718";

	private String expressId;
	private String expressNo;
	private String expressOno;
	private String expressType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createTime;

	public String getExpressId() {
		return expressId;
	}

	public void setExpressId(String expressId) {
		this.expressId = expressId == null ? null : expressId.trim();
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo == null ? null : expressNo.trim();
	}

	public String getExpressOno() {
		return expressOno;
	}

	public void setExpressOno(String expressOno) {
		this.expressOno = expressOno == null ? null : expressOno.trim();
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType == null ? null : expressType.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// Non-database fields
	private String startDate;
	private String endDate;
	private String formatDate;
	private List<String> orderNoList;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

}