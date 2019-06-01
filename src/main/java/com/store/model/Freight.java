package com.store.model;

public class Freight {

	private String freightId;
	private String freightType;
	private String freightCity;
	private String freightSame;
	private String freightGeneral;
	private String freightRemote;

	public static final String FREIGHT_SAME = "FREIGHT_SAME";
	public static final String FREIGHT_GENERAL = "FREIGHT_GENERAL";
	public static final String FREIGHT_REMOTE = "FREIGHT_REMOTE";

	public String getFreightId() {
		return freightId;
	}

	public void setFreightId(String freightId) {
		this.freightId = freightId == null ? null : freightId.trim();
	}

	public String getFreightType() {
		return freightType;
	}

	public void setFreightType(String freightType) {
		this.freightType = freightType == null ? null : freightType.trim();
	}

	public String getFreightCity() {
		return freightCity;
	}

	public void setFreightCity(String freightCity) {
		this.freightCity = freightCity == null ? null : freightCity.trim();
	}

	public String getFreightSame() {
		return freightSame;
	}

	public void setFreightSame(String freightSame) {
		this.freightSame = freightSame == null ? null : freightSame.trim();
	}

	public String getFreightGeneral() {
		return freightGeneral;
	}

	public void setFreightGeneral(String freightGeneral) {
		this.freightGeneral = freightGeneral == null ? null : freightGeneral.trim();
	}

	public String getFreightRemote() {
		return freightRemote;
	}

	public void setFreightRemote(String freightRemote) {
		this.freightRemote = freightRemote == null ? null : freightRemote.trim();
	}

	// Non-database fields
	private String end;

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}