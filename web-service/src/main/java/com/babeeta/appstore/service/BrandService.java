package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Brand;

public interface BrandService {

	public long countBrandByBrands(String ids[]);

	public long countEnabled();

	/**
	 * 根据token查询品牌
	 * 
	 * @param brands
	 * @return
	 */
	public List<Brand> findBrandByBrands(String ids[], int offset, int limit);

	/**
	 * 根据ID查找品牌
	 * 
	 * @param id
	 *            品牌的ID
	 * @return 如果为null， 则说明不存在
	 */
	public Brand findBrandById(String id);

	/**
	 * 根据品牌获取品牌下的app
	 * 
	 * @return app列表
	 */
	public List<App> getAppsByBrand(String brandName);

	/**
	 * 获取所有的上架品牌
	 * 
	 * @return 品牌列表
	 */
	public List<Brand> getEnabledBrands(int offset, int limit);
}
