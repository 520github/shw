/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.GooglePlay;

/**
 * GooglePlay市场的应用详情
 * @author xuehui.miao
 *
 */
public interface GooglePlayDao {
	
	/**
	 * 按URL查找GooglePlay应用详情
	 * 
	 * @param url 
	 *            GooglePlay应用详情对应的URL
	 * @return GooglePlay应用详情
	 */
	public GooglePlay findByUrl(String url);
	
	/**
	 * 保存一条googlePlay应用详情记录
	 * @param googlePlay googlePlay应用详情
	 */
	public void saveGooglePlay(GooglePlay googlePlay);
}
