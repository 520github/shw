package com.babeeta.appstore.manager.notification;

public class Message {
	public static String getAppUpdateMessage() {
		return "{\"code\":0,\"msg\":\"应用有更新\",\"action\":\"/device/app/laste\"}";
	}

	public static String getBrandUpdateMessage() {
		return "{\"code\":2,\"msg\":\"品牌有更新\",\"action\":\"coral-bay://view/brand/m.shanhubay.com/android/client/brand/mybrand.html\"}";
	}
}
