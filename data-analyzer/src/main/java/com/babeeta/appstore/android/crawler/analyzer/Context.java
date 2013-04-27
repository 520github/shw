package com.babeeta.appstore.android.crawler.analyzer;

public class Context {
	private String contentType;
	private String url;
	private String location;

	public String getContentType() {
		return contentType;
	}

	public String getUrl() {
		return url;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
