package com.babeeta.appstore.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.CatalogDao;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.manager.service.CatalogService;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

	private CatalogDao catalogDao;

	@Override
	public List<Catalog> findAll() {
		return catalogDao.findAll();
	}

	@Override
	public List<Catalog> findAll(boolean root) {
		if (root) {
			return catalogDao.findRootCatalog();
		} else {
			return this.findAll();
		}
	}

	@Override
	public Catalog findByName(String name) {
		return catalogDao.findCatalog(name);
	}

	@Override
	public List<Catalog> findByParent(String parentName) {
		return catalogDao.findByParent(parentName);
	}

	@Override
	public void remove(String name) {
		catalogDao.remove(name);
	}

	@Override
	public void save(Catalog catalog) {
		catalogDao.save(catalog);
	}

	@Autowired
	@Required
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	@Override
	public void setEnabled(String name, boolean enabled) {
		Catalog catalog = this.catalogDao.findCatalog(name);
		if (catalog != null) {
			catalog.setEnabled(enabled);
			this.catalogDao.save(catalog);
		}
	}
}
