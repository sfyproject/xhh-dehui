package com.store.model;

import java.util.Date;

public class Notice {
	private String noticeId;

	private String noticeTitle;

	private String noticeEnding;

	private String noticeImage;

	private String noticeStatus;

	private Date createTime;

	private String noticeContent;

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId == null ? null : noticeId.trim();
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
	}

	public String getNoticeEnding() {
		return noticeEnding;
	}

	public void setNoticeEnding(String noticeEnding) {
		this.noticeEnding = noticeEnding == null ? null : noticeEnding.trim();
	}

	public String getNoticeImage() {
		return noticeImage;
	}

	public void setNoticeImage(String noticeImage) {
		this.noticeImage = noticeImage == null ? null : noticeImage.trim();
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus == null ? null : noticeStatus.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent == null ? null : noticeContent.trim();
	}
}