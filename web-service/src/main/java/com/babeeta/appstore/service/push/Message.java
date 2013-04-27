package com.babeeta.appstore.service.push;

public class Message {
	public static String getAppDownloadUrl(String appId) {
		return "{\"code\":1,\"msg\":\"\",\"action\":\"" + appId
				+ "\"}";
	}
}
