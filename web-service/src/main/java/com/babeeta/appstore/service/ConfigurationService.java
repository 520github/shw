package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.Advertisement;

public interface ConfigurationService {
	
	/**
	 * 获取当前专题
	 * @return
	 */
	String getCurrentTopicLink();
	
	/**
	 * 获取广告列表
	 * @return
	 */
	List<Advertisement> getAdList();
	
	/**
	 * 获取随便看看的icon
	 * @return
	 */
	String getRandomIcon();
	
	/**
	 * 获取排行榜icon
	 * @return
	 */
	String getRankIcon();
	
	/**
	 * 获取Base配置项信息
	 * @return
	 */
	String getBaseResource();
}
