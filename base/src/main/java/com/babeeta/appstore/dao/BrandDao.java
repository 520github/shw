package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.Brand;

public interface BrandDao {
	/**
	 * 统计所有品牌总数
	 * 
	 * @return
	 */
	long count();

	long countBrandByBrands(String ids[]);

	/**
	 * 查找所有的品牌
	 * 
	 * @return
	 */
	List<Brand> findAll();

	/**
	 * 品牌分页查询
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Brand> findAll(int offset, int limit);

	/**
	 * 按ID查找
	 * 
	 * @param id
	 * @return
	 */
	Brand findById(String id);

	/**
	 * 按名称（不是按拼音）查找
	 * 
	 * @param name
	 * @return
	 */
	Brand findByName(String name);

	List<Brand> findEnabledBrands();

	/**
	 * 查找所有上架的品牌
	 * 
	 * @return
	 */
	List<Brand> findEnabledBrands(int offset, int limit);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void remove(String id);

	/**
	 * 保存，如果没有id就是新建
	 * 
	 * @param brand
	 * @return
	 */
	Brand save(Brand brand);

	/**
	 * 品牌编辑
	 * 
	 * @param brand
	 */
	void updateBrand(String id, Brand brand);

	/**
	 * 更新品牌上下架状态
	 * 
	 * @param state
	 */
	void updateBrandState(String id, boolean state);

	public long countEnabled();

	/**
	 * 为品牌id为id的品牌的计数器的value减一
	 * 
	 * @param id
	 * @param value
	 */
	public void decreaseBrandCounter(String id, String value);

	/**
	 * 根据品牌ID列表查询品牌
	 * 
	 * @param brands
	 * @return
	 */
	public List<Brand> findBrandByBrands(String[] brands, int offset, int limit);

	/**
	 * 为品牌id为id的品牌的计数器的value加一
	 * 
	 * @param id
	 * @param value
	 */
	public void increaseBrandCounter(String id, String value);

}
