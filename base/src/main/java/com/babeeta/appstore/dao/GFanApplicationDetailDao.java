/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.GFanApplicationDetail;

/**
 * GFan市场应用详情
 * @author xuehui.miao
 *
 */
public interface GFanApplicationDetailDao {
	/**
	 * 按URL移除GFan的应用详情
	 * 
	 * @param url
	 *           GFan的应用详情的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找GFan应用详情
	 * 
	 * @param url 
	 *            GFan应用详情的URL
	 * @return GFan应用详情
	 */
	public GFanApplicationDetail findByUrl(String url);
	
	/**
	 * 按redirectionUrl查找GFan应用详情
	 * 
	 * @param redirectionUrl 
	 *            GFan应用详情的跳转APK的URL
	 * @return GFan应用详情
	 */
	public GFanApplicationDetail findByRedirectionUrl(String redirectionUrl);
	
	/**
	 * 保存一条GFan市场应用详细
	 * @param gFanApplicationDetail
	 */
	public void saveGFanApplicationDetail(GFanApplicationDetail gFanApplicationDetail);
}
