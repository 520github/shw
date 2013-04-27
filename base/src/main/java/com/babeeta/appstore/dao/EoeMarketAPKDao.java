/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.EoeMarketAPK;

/**
 * EOE市场APK包
 * @author xuehui.miao
 *
 */
public interface EoeMarketAPKDao {
	
	/**
	 * 按URL移除EOE的APK
	 * 
	 * @param url
	 *           EOE的APK对应的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找EOE的APK
	 * 
	 * @param url 
	 *            EOE的APK对应的URL
	 * @return EOE的APK
	 */
	public EoeMarketAPK findByUrl(String url);
	
	
	/**
	 * 保存一条EOE市场的APK信息
	 * @param eoeMarketAPK
	 */
	public void saveEoeMarketAPK(EoeMarketAPK eoeMarketAPK);
}
