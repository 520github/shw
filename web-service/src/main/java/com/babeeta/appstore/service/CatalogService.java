package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.Catalog;

public interface CatalogService {
	/**
	 * 获取一级分类
	 * @return
	 * 			一级分类列表
	 */
	List<Catalog> getRoots();
	/**
	 * 获取含有说明一级分类
	 * @return
	 * 			一级分类列表（含说明）
	 */
	List<Catalog> getRootsWithDesc();
	/**
	 * 根据一级分类获取二级分类
	 * @param root 一级分类
	 * @return
	 * 			二级分类列表
	 */
	List<Catalog> getSecondariesByRoot(Catalog root);
	/**
	 * 根据名称获得分类
	 * @param name
	 * @return
	 * 			类别实体
	 */
	Catalog getCatalog(String name);
	/**
	 * 根据多个分类名称，返回所有分类的小编推荐
	 * @param name
	 * @return
	 */
	List<String> getCatalogsEditorOpinion(List<String> catalogs);
}
