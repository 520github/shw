package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.App;

public interface AppService {

	/**
	 * 审核通过
	 * 
	 * @param id
	 */
	void approve(String id);

	/**
	 * get count of special app
	 * 
	 * @param appId
	 * @param catalog
	 * @param language
	 * @param keyword
	 * @return
	 */
	long countAll(String appId, String catalog, String language, String keyword);

	/**
	 * 统计等待审核的App
	 * 
	 * @param search
	 * @return
	 */
	long countAwaiting(String search);

	/**
	 * 统计所有已处理但未上架的App
	 * 
	 * @param search
	 *            关键字
	 * @param catalog
	 *            分类
	 * @return
	 */
	long countProcessed(String search, String catalog);

	/**
	 * 统计已经发布的App
	 * 
	 * @param search
	 * @param catalog
	 * @return
	 */
	long countPublished(String search, String catalog);

	/**
	 * 统计所有未处理的App
	 * 
	 * @param search
	 *            关键字
	 * 
	 * @return
	 */
	long countUnprocessed(String search);

	/**
	 * 将应用下架
	 * 
	 * @param id
	 */
	void disable(String id);

	/**
	 * 根据Application ID查找
	 * 
	 * @param id
	 * @return
	 */
	App find(String id) throws EntityNotFoundException;

	/**
	 * get all apps
	 * 
	 * @param catalog
	 *            catalogName
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<App> findAll(String appId, String catalog, String language,
			String keyword, int offset, int limit);

	/**
	 * 查询等待审核的App
	 * 
	 * @param search
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<App> findAwaiting(String search, int offset, int limit);

	/**
	 * 查詢所有已處理但未上架的App
	 * 
	 * @param search
	 *            关键字
	 * @param catalog
	 *            分类
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<App> findProcessed(String search, String catalog, int offset, int limit);

	/**
	 * 查询已经发布的App
	 * 
	 * @param search
	 * @param catalog
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<App> findPublished(String search, String catalog, int offset, int limit);

	/**
	 * 查詢所有未處理的App
	 * 
	 * @param search
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<App> findUnprocessed(String search, int offset, int limit);

	/**
	 * 编辑前锁定App
	 * 
	 * @param id
	 *            App的ID
	 * @param username
	 *            加锁者的用户名
	 * @return 如果锁定成功，返回App的对象。否则返回null。
	 */
	App lock(String id, String username);

	/**
	 * 标记App已经被删除
	 * 
	 * @param appId
	 */
	void markRemove(String appId);

	/**
	 * 上架
	 * 
	 * @param id
	 */
	void published(String id);

	/**
	 * 没有通过审核
	 * 
	 * @param id
	 */
	void refuse(String id);

	/**
	 * 更新一个App
	 * 
	 * @param app
	 */
	void save(App app);

	/**
	 * 更新一个App,仅仅关于状态的改动，不会导致索引改动和最后修改时间改动
	 * 
	 * @param app
	 */
	void toggleSave(App app);

	/**
	 * 更新备注
	 * 
	 * @param id
	 * @param note
	 */
	void updateNote(String id, String note);

	public void release(String id, String username);

}