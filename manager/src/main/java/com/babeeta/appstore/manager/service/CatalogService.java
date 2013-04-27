package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.entity.Catalog;

public interface CatalogService {

	/**
	 * 查找所有的catalog
	 * 
	 * @return
	 */
	List<Catalog> findAll();

	/**
	 * 查找所有的catalog
	 * 
	 * @param root
	 *            只查找顶层的Catalog？
	 * @return
	 */
	List<Catalog> findAll(boolean root);

	/**
	 * 根据名称查找catalog对象
	 * 
	 * @param name
	 * @return
	 */
	Catalog findByName(String name);

	/**
	 * 查找分类下的子分类
	 * 
	 * @param parentId
	 * @return
	 */
	List<Catalog> findByParent(String parentName);

	/**
	 * 删除一个分类
	 * 
	 * @param name
	 */
	void remove(String name);

	/**
	 * 保存
	 * 
	 * @param catalog
	 */
	void save(Catalog catalog);

	/**
	 * 设置分类的上下架状态
	 * 
	 * @param name
	 * @param enabled
	 */
	void setEnabled(String name, boolean enabled);

}
