package com.babeeta.appstore.dao;

import java.util.Date;
import java.util.List;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;

public interface AppDao {

	/**
	 * 把app添加到索引文件中
	 * 
	 * @param e
	 */
	public void addIndex(App e);

	/**
	 * 比较APP的版本号是否发生变动
	 * 
	 * @param oldVersion
	 *            原来版本号
	 * @param newVersion
	 *            最新版本号
	 * @return
	 */
	public boolean compareAppVersion(String oldVersion, String newVersion);

	/**
	 * 统计所有未处理的App数量
	 * 
	 * @param search
	 *            关键字
	 * 
	 * @param appStatus
	 *            App的状态
	 * 
	 * @return 所有未处理的数量。包括正在编辑中的
	 */
	public long count(String search, AppStatus[] appStatus);

	/**
	 * 统计所有未处理的App数量
	 * 
	 * @param search
	 *            关键字
	 * @param catalog
	 *            分类
	 * @param appStatus
	 *            App的状态
	 * 
	 * @return 所有未处理的数量。包括正在编辑中的
	 */
	public long count(String search, String catalog, AppStatus[] appStatus);

	/**
	 * get total counts of apps
	 * 
	 * @param catalog
	 * @return
	 */
	public long count(String appId, String catalog, String language,
			String keyword);

	/**
	 * 查询所有的精品推荐的数量
	 * 
	 * @param since
	 *            起始时间
	 * @return
	 */
	public long countBest();

	/**
	 * 查询某时间点起的精品推荐的数量
	 * 
	 * @param since
	 *            起始时间
	 * @return
	 */
	public long countBestSince(Date since);

	/**
	 * 根据类别查其下app的数量
	 * 
	 * @param catalogName
	 *            类别名称
	 * @return App实体列表
	 */
	public long countPublishedByCatalogName(String catalogName);

	/**
	 * 获取搜索命中的数量
	 * 
	 * @param search
	 * @return
	 */
	public int countSearch(String search);

	/**
	 * 把appid为id的app从索引库中删除
	 * 
	 * @param id
	 */
	public void deleteIndex(String id);

	/**
	 * 分页查询未处理的新App
	 * 
	 * @param search
	 *            关键字 返回不大于此ID的应用
	 * 
	 * @param appStatus
	 *            App的状态
	 * @param offset
	 * @param limit
	 * 
	 * @return 按id进行倒序排列的结果
	 */
	public List<App> find(String search, AppStatus[] appStatus, int offset,
			int limit);

	/**
	 * 分页查询未处理的新App
	 * 
	 * @param search
	 *            关键字 返回不大于此ID的应用
	 * @param catalog
	 *            所属分类
	 * 
	 * @param appStatus
	 *            App的状态
	 * @param offset
	 * @param limit
	 * 
	 * @return 按id进行倒序排列的结果
	 */
	public List<App> find(String search, String catalog, AppStatus[] appStatus,
			int offset, int limit);

	/**
	 * get all apps
	 * 
	 * @param catalog
	 * @return
	 */
	public List<App> findAll(String appId, String catalog, String language,
			String keyword, int offset, int limit);

	/***
	 * 获取分类下的app，根据评分排序
	 * 
	 * @param catalogname
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> findAppByCatalogOrderByVote(String catalogname,
			int offset,
			int limit);

	/**
	 * 
	 * @param id
	 *            应用id
	 * @return 应用
	 * 
	 */
	public App findByAppId(String id);

	/**
	 * 按品牌名称查找应用
	 * 
	 * @param brandName
	 *            品牌名称
	 * @param enabled
	 *            是否上架
	 * @return 这个品牌下的应用列表
	 */
	public List<App> findByBrandAndStatus(String brandName, AppStatus appStatus);

	/**
	 * 根据分类名称以及品牌名称获取App列表
	 * 
	 * @param catalogName
	 * @param brandName
	 * @param offset
	 * @param limit
	 * @return
	 */
	public List<App> findByCatalogAndBrand(String catalogName,
			String brandName, int offset, int limit);

	/**
	 * 通过多个appId查找app
	 * 
	 * @param ids
	 *            id列表
	 * @return app实体列表
	 */
	public List<App> findByIds(List<String> ids);

	/**
	 * 根据最后更新时间排序，偏移offset条记录后，取前limit条app
	 * 
	 * @param offset
	 *            偏移量
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            app的个数
	 * @return App实体列表
	 */
	public List<App> findFrontPublishedByLastModified(int offset, int limit);

	/**
	 * 根据评分排序，偏移offset条记录后，取前limit条app
	 * 
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            app的个数
	 * @return App实体列表
	 */
	public List<App> findFrontPublishedByScore(int offset, int limit);

	/**
	 * 通过catalog和group查找app
	 * 
	 * @param catalog
	 * @param brand
	 * @param offset
	 *            起始位移数
	 * @param limit
	 *            查找的个数
	 * @return
	 */
	public List<App> findPublishedByCatalogAndGroup(String catalog,
			String brand, int offset, int limit);

	/**
	 * 根据类别名称查app列表，没有分页
	 * 
	 * @param catalogName
	 *            类别名称
	 * @return App实体列表
	 */
	public List<App> findPublishedByCatalogName(String catalogName);

	/**
	 * 选取分类下的app
	 * 
	 * @param catalogName
	 *            分类名称
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            数量
	 * @return
	 */
	public List<App> findPublishedByCatalogName(String catalogName, int offset,
			int limit);

	/**
	 * 通过appId选取已发布的app
	 * 
	 * @param id
	 *            来自apple store的Id
	 * @return App实体
	 */
	public App findPublishedById(String id);

	/**
	 * 通过多个appId查找已发布的app
	 * 
	 * @param ids
	 *            id列表
	 * @return app实体列表
	 */
	public List<App> findPublishedByIds(List<String> ids);

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
	 * 锁定App（分配编辑）
	 * 
	 * @param id
	 *            需要锁定App的ID
	 * @param editor
	 *            锁定App的编辑
	 * @return 如果锁定成功，返回参数中指定editor的用户名。否则返回锁定此App的编辑的用户名
	 * @exception EntityNotFoundException
	 *                被请求锁定的App不存在
	 */
	public String lock(String id, String editor) throws EntityNotFoundException;

	public void release(String id, String editor);

	/**
	 * 保存app，以存在(_id)则覆盖
	 * 
	 * @param e
	 */
	public void save(App e);

	/**
	 * 根据关键词搜索索引文件，按偏移量offset和限制数limit返回命中结果app列表
	 * 
	 * @param search
	 *            关键词
	 * @return app列表
	 */
	public List<App> search(String search, int offset, int limit);

	/**
	 * 如果库中存在这个app，则修改，否则创建
	 * 
	 * @param e
	 */
	public void updateByAppIdOrCreate(App e);

}
