package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.EoeMarketAPKDao;
import com.babeeta.appstore.entity.EoeMarketAPK;
import com.google.code.morphia.query.Query;

public class EoeMarketAPKDaoImpl extends BasicDaoImpl implements
		EoeMarketAPKDao {
	
	@Override
	public EoeMarketAPK findByUrl(String url) {
		return datastore.createQuery(EoeMarketAPK.class).filter("_id =", url).get();
	}
	
	@Override
	public void saveEoeMarketAPK(EoeMarketAPK eoeMarketAPK) {
		Query<EoeMarketAPK> q = datastore.createQuery(EoeMarketAPK.class).filter("_id =",
				eoeMarketAPK.getId());
		datastore.updateFirst(q, eoeMarketAPK, true);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(EoeMarketAPK.class).field("url").equal(url));
	}

}
