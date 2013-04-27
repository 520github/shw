package com.babeeta.appstore.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.CatalogDao;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.service.CatalogService;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

	private CatalogDao catalogDao;

	@Override
	public List<Catalog> getRoots() {
		return catalogDao.findRootCatalogEnabled();
	}

	@Override
	public List<Catalog> getSecondariesByRoot(Catalog root) {
		return catalogDao.findByParentEnabled(root.getName());
	}

	@Autowired
	@Required
	public void setCatalogDao(CatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	@Override
	public List<Catalog> getRootsWithDesc() {
		List<Catalog> catalogs = catalogDao.findRootCatalogEnabled();
		for (Catalog c : catalogs) {
			List<String> subCatalogs = c.getChildren();
			StringBuffer sb = new StringBuffer();
			if (subCatalogs != null) {
				for (String sub : subCatalogs) {
					Catalog cc = catalogDao.findCatalog(sub);
					if (cc == null || !cc.isEnabled())
						continue;
					sb.append(sub);
					sb.append(",");
				}
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			c.setDescription(sb.toString());
		}
		return catalogs;
	}

	@Override
	public Catalog getCatalog(String name) {
		return catalogDao.findCatalog(name);
	}

	@Override
	public List<String> getCatalogsEditorOpinion(List<String> names) {
		List<Catalog> catalogs = catalogDao.findCatalogsEnabled(names);
		Map<String, Object> map = new HashMap<String, Object>();
		if (catalogs == null){
			return Collections.emptyList();
		}
		for (Catalog c : catalogs) {
			List<String> apps = c.getEditorOpinion();
			if (apps == null)
				continue;
			for (String app : apps) {
				map.put(app, null);
			}
		}
		List<String> ret = new ArrayList<String>();
		ret.addAll(map.keySet());
		return ret;
	}
}
