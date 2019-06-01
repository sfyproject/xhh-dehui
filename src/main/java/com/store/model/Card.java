package com.store.model;

import java.util.Date;

public class Card {

	private String cardId;
	private String cardStatus;
	private String cardCoin;
	private String cardUid;
	private Date createTime;

	public static final String STATUS_NOTUSED = "0";
	public static final String STATUS_ALREADYUSED = "1";

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId == null ? null : cardId.trim();
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus == null ? null : cardStatus.trim();
	}

	public String getCardCoin() {
		return cardCoin;
	}

	public void setCardCoin(String cardCoin) {
		this.cardCoin = cardCoin == null ? null : cardCoin.trim();
	}

	public String getCardUid() {
		return cardUid;
	}

	public void setCardUid(String cardUid) {
		this.cardUid = cardUid == null ? null : cardUid.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	// Non-database fields
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}