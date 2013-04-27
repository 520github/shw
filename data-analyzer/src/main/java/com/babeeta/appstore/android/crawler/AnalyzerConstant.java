/**
 * 
 */
package com.babeeta.appstore.android.crawler;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import com.babeeta.appstore.android.crawler.analyzer.Context;

/**
 * 数据分析常量类
 * @author xuehui.miao
 *
 */
public class AnalyzerConstant {
	public static String urlGFanApk = "gFanApk";
	public static String urlGFanApplicationDetail = "gFanApplicationDetail";
	public static String urlGFanRedirection = "gFanRedirection";
	
	public static String urlEoeApk = "eoeApk";
	public static String urlEoeApplicationDetail = "eoeApplicationDetail";
	public static String urlEoeRedirection = "eoeRedirection";
	
	public static String urlGooglePlay = "googlePlay";
	
	public static String MARKET_TYPE_EOE = "EoeMarket";
	public static String MARKET_TYPE_GFAN = "Gfan";
	public static String MARKET_TYPE_PLAY = "Google Play";
	
	
	public static Map<String,String> urlMap = new LinkedHashMap<String,String>();
	static {
		urlMap.put(urlGFanApk, "http://cdn1.down.apk.gfan.com/asdf");
		urlMap.put(urlGFanRedirection, "http://apk.gfan.com/Aspx/UserApp/DownLoad.aspx");
		urlMap.put(urlGFanApplicationDetail, "http://apk.gfan.com/Product/");
		
		urlMap.put(urlEoeApk, "http://c11.eoemarket.com//upload/");
		urlMap.put(urlEoeRedirection, "http://download.eoemarket.com/app");
		urlMap.put(urlEoeApplicationDetail, "http://www.eoemarket.com/apps/");
		
		urlMap.put(urlGooglePlay, "https://play.google.com/store/apps/details?id=");
	}
	
	public static String getContentType(Context content) {
		String type = "";
		Iterator<String> it = urlMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = urlMap.get(key);
			if(content.getUrl().indexOf(value) >-1) {
				type = key;
				break;
			}
		}
		return type;
	}
	
	public static String getContentType(String url) {
		String type = "";
		Iterator<String> it = urlMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = urlMap.get(key);
			if(url.indexOf(value) >-1) {
				type = key;
				break;
			}
		}
		return type;
	}
	
	public static boolean checkUrl(String url) {
		boolean check = false;
		Iterator<String> it = urlMap.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String value = urlMap.get(key);
			if(url.indexOf(value) >-1) {
				check = true;
				break;
			}
		}
		return check;
	}
}
