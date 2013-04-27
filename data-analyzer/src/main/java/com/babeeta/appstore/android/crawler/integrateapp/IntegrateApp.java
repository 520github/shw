/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp;

/**
 * 整合APP应用接口
 * @author xuehui.miao
 *
 */
public interface IntegrateApp {
	
	/**
	 * 根据指定的PK整合单个APP
	 * @param url
	 *           url等相关唯一信息
	 */
	public void integrateOneAPP(String url);
	
	
	/**
	 * 整合所有可能的APP
	 */
	public void integrateAllApp();
}
