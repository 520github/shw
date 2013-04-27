/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.EoeMarketApplicationDetail;

/**
 * EOE市场应用详细
 * @author xuehui.miao
 *
 */
public interface EoeMarketApplicationDetailDao {
	/**
	 * 按URL移除EOE的应用详情
	 * 
	 * @param url
	 *           EOE的应用详情的URL
	 */
	public void removeByUrl(String url);
	
	/**
	 * 按URL查找EOE应用详情
	 * 
	 * @param url 
	 *            EOE应用详情的URL
	 * @return EOE应用详情
	 */
	public EoeMarketApplicationDetail findByUrl(String url);
	
	/**
	 * 按redirectionUrl查找EOE应用详情
	 * 
	 * @param redirectionUrl 
	 *            EOE应用详情的跳转APK的URL
	 * @return EOE应用详情
	 */
	public EoeMarketApplicationDetail findByRedirectionUrl(String redirectionUrl);
	
	/**
	 * 保存一条EOE市场应用详细
	 * @param eoeMarketApplicationDetail
	 */
	public void saveEoeMarketApplictionDetail(EoeMarketApplicationDetail eoeMarketApplicationDetail);
}
