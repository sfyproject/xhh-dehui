package com.store.model;

import java.util.Date;

public class Share {

	public static final String STATUS_SUCCESS = "1";
	public static final String STATUS_FAIL = "0";

	public static final String TYPE_GENERAL = "0";

	private String shareId;
	private String shareCoin;
	private String shareStatus;
	private String shareType;
	private String shareXuid;
	private String shareUid;
	private Date createTime;

	public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId == null ? null : shareId.trim();
	}

	public String getShareCoin() {
		return shareCoin;
	}

	public void setShareCoin(String shareCoin) {
		this.shareCoin = shareCoin == null ? null : shareCoin.trim();
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus == null ? null : shareStatus.trim();
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType == null ? null : shareType.trim();
	}

	public String getShareXuid() {
		return shareXuid;
	}

	public void setShareXuid(String shareXuid) {
		this.shareXuid = shareXuid == null ? null : shareXuid.trim();
	}

	public String getShareUid() {
		return shareUid;
	}

	public void setShareUid(String shareUid) {
		this.shareUid = shareUid == null ? null : shareUid.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}