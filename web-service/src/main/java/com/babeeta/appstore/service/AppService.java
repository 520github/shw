package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.GroupItem;

/**
 * app的业务接口服务 这里涉及的都是状态为Published的app
 * 
 * @author chongf
 * 
 */
public interface AppService {
	/**
	 * 获取所有精品推荐的app的数量
	 * 
	 * @return
	 */
	public long countAllBest();

	/**
	 * 获取所有已发布app的数量
	 * 
	 * @return
	 */
	public long countAllPublished();

	/**
	 * 根据分类名称获取分类下上架应用总数
	 * 
	 * @param Catalog
	 * @return
	 */
	public long countPublishedByCatalogName(String Catalog);

	/**
	 * 搜索命中数量
	 * 
	 * @param search
	 * @return
	 */
	public int countSearch(String search);

	/**
	 * 获取app，根据评分排序
	 * 
	 * @param catalogName
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> getAppByCatalogOrderByVote(String catalogName, int offset,
			int limit);

	/**
	 * 随机查询App
	 * 
	 * @param randomNum
	 * @return
	 */
	public App getAppByRandom(double randomNum);

	/**
	 * 获取精品推荐APP列表
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> getAppWithBest(int offset, int limit);

	/**
	 * 通过类别获取已发布的App列表
	 * 
	 * @param catalog
	 *            类别
	 * @param offset
	 *            偏移
	 * @param limit
	 *            数量
	 * @return App列表
	 */
	public List<App> getByCatalog(Catalog catalog, int offset, int limit);

	/**
	 * 根据分类名称以及分组名称获取App列表
	 * 
	 * @param catalogName
	 * @param brandName
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> getByCatalogAndGroup(String catalogName, String brandName,
			int offset, int limit);

	/**
	 * 根据名称名，按brand分组返回app列表
	 * 
	 * @param catalogName
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<GroupItem> getByCatalogGroupByBrand(String catalogName,
			int offset, int limit);

	/**
	 * 通过appId获取app
	 * 
	 * @return App实体
	 */
	public App getById(String appId);

	/**
	 * 通过多个appId获取app列表
	 * 
	 * @return App实体列表
	 */
	public List<App> getByIds(List<String> ids);

	/**
	 * 通过appId获取已发布的app
	 * 
	 * @return App实体
	 */
	public App getPublishedById(String appId);

	/**
	 * 通过多个appId获取已发布的app列表
	 * 
	 * @return App实体列表
	 */
	public List<App> getPublishedByIds(List<String> ids);

	/**
	 * 搜索，使用关键词查询相关app，使用offset和limit限定数量
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> search(String search, int offset, int limit);

	/**
	 * 取符合条件的前n个app
	 * 
	 * @param offset
	 * @param limit
	 * @param condition
	 *            top的
	 * @return App列表
	 */
	public List<App> topApp(String condition, int offset, int limit);

}
