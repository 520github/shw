package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.GFanRedirectionDao;
import com.babeeta.appstore.entity.GFanRedirection;
import com.google.code.morphia.query.Query;

/**
 * @author xuehui.miao
 *
 */
public class GFanRedirectionDaoImpl extends BasicDaoImpl implements
		GFanRedirectionDao {
	
	@Override
	public GFanRedirection findByUrl(String url) {
		return datastore.createQuery(GFanRedirection.class).
                 filter("_id =", url).get();
	}
	
	@Override
	public GFanRedirection findByApkUrl(String apkUrl) {
		return datastore.createQuery(GFanRedirection.class).
                 filter("apkUrl =", apkUrl).get();
	}
	@Override
	public void saveGFanRedirection(GFanRedirection gFanRedirection) {
		Query<GFanRedirection> q = datastore.createQuery(GFanRedirection.class).filter("_id =",
				gFanRedirection.getId());
		datastore.updateFirst(q, gFanRedirection, true);
		//datastore.save(gFanRedirection);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(GFanRedirection.class).filter("url =", url));
	}

}
