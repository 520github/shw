package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.Catalog;

public interface CatalogDao {

	/**
	 * 查找所有的分类
	 * 
	 * @return
	 */
	public List<Catalog> findAll();

	/**
	 * 通过父类获取上架的子类
	 * 
	 * @param catalog
	 * @return 子类列表
	 */
	public List<Catalog> findByParentEnabled(String name);
	
	/**
	 * 通过父类获取所有子类
	 * 
	 * @param catalog
	 * @return 子类列表
	 */
	public List<Catalog> findByParent(String name);

	/**
	 * 通过名称得到分类
	 * 
	 * @return 分类实体
	 */
	public Catalog findCatalog(String name);
	
	/**
	 * 通过名称得到已上架分类
	 * 如果不存在或未上架，返回null
	 * 
	 * @return 分类实体
	 */
	public Catalog findCatalogEnabled(String name);
	/**
	 * 通过多个名称得到对应分类（已上架）
	 * @param names
	 * @return
	 */
	public List<Catalog> findCatalogsEnabled(List<String> names);

	/**
	 * 获取已上架的一级分类列表
	 * 
	 * @return
	 */
	public List<Catalog> findRootCatalogEnabled();
	/**
	 * 获取一级分类
	 * 
	 * @return
	 */
	public List<Catalog> findRootCatalog();

	/**
	 * 获取所有已上架的二级分类
	 * 
	 * @return 分类实体列表
	 */
	public List<Catalog> findSecondaryCatalogsEnabled();
	
	/**
	 * 删除
	 * 
	 * @param name
	 */
	public void remove(String name);

	/**
	 * 添加（修改）
	 * 
	 * @param catalog
	 *            分类实体
	 */
	public void save(Catalog catalog);

}
