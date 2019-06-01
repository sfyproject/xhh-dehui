package com.store.model;

public class Image {

	private String imageId;
	private String imageLink;

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId == null ? null : imageId.trim();
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink == null ? null : imageLink.trim();
	}

}