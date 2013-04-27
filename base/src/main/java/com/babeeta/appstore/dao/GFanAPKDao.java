package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.GFanAPK;

/**
 * GFan市场APK包
 * @author xuehui.miao
 *
 */
public interface GFanAPKDao {
	/**
	 * 按URL移除GFan的APK
	 * 
	 * @param url
	 *           GFan的APK对应的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找GFan的APK
	 * 
	 * @param url 
	 *            GFan的APK对应的URL
	 * @return GFan的APK
	 */
	public GFanAPK findByUrl(String url);
	
	/**
	 * 保存一条GFan市场APK包信息的记录
	 * @param gFanAPK GFan市场APK包信息
	 */
	public void saveGFanAPK(GFanAPK gFanAPK);
}
