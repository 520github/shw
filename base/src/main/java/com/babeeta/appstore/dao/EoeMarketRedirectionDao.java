/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.EoeMarketRedirection;

/**
 * EOE市场APK下载URL路径
 * @author xuehui.miao
 *
 */
public interface EoeMarketRedirectionDao {
	/**
	 * 按URL移除EOE的跳转APK页面信息
	 * 
	 * @param url
	 *           EOE跳转APK页面的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找EOE的跳转APK页面信息
	 * 
	 * @param url 
	 *            EOE跳转APK页面的URL
	 * @return EOE应用详情
	 */
	public EoeMarketRedirection findByUrl(String url);
	
	/**
	 * 按apkUrl查找EOE跳转APK页面信息
	 * 
	 * @param apkUrl 
	 *            APK的URL
	 * @return EOE应用详情
	 */
	public EoeMarketRedirection findByApkUrl(String apkUrl);
	
	
	/**
	 * 保存一条EOE市场跳转APK下载的链接
	 * @param eoeMarketRedirection
	 */
	public void saveEoeMarketRedirection(EoeMarketRedirection eoeMarketRedirection);
}
