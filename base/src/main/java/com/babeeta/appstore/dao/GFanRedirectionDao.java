/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.GFanRedirection;

/**
 * GFan市场跳转APK的URL
 * @author xuehui.miao
 *
 */
public interface GFanRedirectionDao {
	/**
	 * 按URL移除GFan的跳转APK页面信息
	 * 
	 * @param url
	 *           GFan跳转APK页面的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找GFan的跳转APK页面信息
	 * 
	 * @param url 
	 *            GFan跳转APK页面的URL
	 * @return GFan应用详情
	 */
	public GFanRedirection findByUrl(String url);
	
	/**
	 * 按apkUrl查找GFan跳转APK页面信息
	 * 
	 * @param apkUrl 
	 *            APK的URL
	 * @return GFan应用详情
	 */
	public GFanRedirection findByApkUrl(String apkUrl);
	
	/**
	 * 保存一条GFan市场跳转APK下载的链接
	 * @param gFanRedirection 
	 */
	public void saveGFanRedirection(GFanRedirection gFanRedirection);
}
