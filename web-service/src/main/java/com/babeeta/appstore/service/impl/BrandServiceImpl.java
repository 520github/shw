package com.babeeta.appstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.BrandDao;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;
import com.babeeta.appstore.entity.Brand;
import com.babeeta.appstore.service.BrandService;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	private AppDao appDao;
	private BrandDao brandDao;

	@Override
	public long countBrandByBrands(String[] ids) {
		return brandDao.countBrandByBrands(ids);
	}

	@Override
	public long countEnabled() {
		return brandDao.countEnabled();
	}

	@Override
	public List<Brand> findBrandByBrands(String ids[], int offset, int limit) {
		return brandDao.findBrandByBrands(ids, offset, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.babeeta.appstore.service.BrandService#findBrandById(java.lang.String)
	 */
	@Override
	public Brand findBrandById(String id) {
		return brandDao.findById(id);
	}

	@Override
	public List<App> getAppsByBrand(String brandName) {
		return appDao.findByBrandAndStatus(brandName, AppStatus.published);
	}

	@Override
	public List<Brand> getEnabledBrands(int offset, int limit) {
		return brandDao.findEnabledBrands(offset, limit);
	}

	@Autowired
	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	@Autowired
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}
}
