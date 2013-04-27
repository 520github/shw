package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.entity.Brand;

public interface BrandService {

	/**
	 * 根据品牌名称查询品牌
	 * 
	 * @param name
	 * @return
	 */
	Brand findByName(String name);

	/**
	 * 查询所有品牌
	 * 
	 * @return 品牌列表
	 */
	public List<Brand> findAll();

	/**
	 * 根据品牌ID查询品牌
	 * 
	 * @param id
	 * @return
	 */
	public Brand findById(String id);

	public void notice();

	/**
	 * 根据品牌ID删除品牌
	 * 
	 * @param id
	 */
	public void remove(String id);

	/**
	 * 新建品牌
	 * 
	 * @param brand
	 * @return
	 */
	public Brand save(Brand brand);

	/**
	 * 品牌编辑
	 * 
	 * @param brand
	 */
	public void updateBrand(String id, Brand brand);

	/**
	 * 跟新品牌状态
	 * 
	 * @param state
	 */
	public void updateBrandState(String id, boolean state);
}
