/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.EoeMarketRedirectionDao;
import com.babeeta.appstore.entity.EoeMarketRedirection;
import com.google.code.morphia.query.Query;

/**
 * @author xuehui.miao
 *
 */
public class EoeMarketRedirectionDaoImpl extends BasicDaoImpl implements
		EoeMarketRedirectionDao {
	
	@Override
	public EoeMarketRedirection findByUrl(String url) {
		return datastore.createQuery(EoeMarketRedirection.class).
                 filter("_id =", url).get();
	}
	
	@Override
	public EoeMarketRedirection findByApkUrl(String apkUrl) {
		return datastore.createQuery(EoeMarketRedirection.class).
                 filter("apkUrl =", apkUrl).get();
	}
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.EoeMarketRedirectionDao#saveEoeMarketRedirection(com.babeeta.appstore.entity.EoeMarketRedirection)
	 */
	@Override
	public void saveEoeMarketRedirection(
			EoeMarketRedirection eoeMarketRedirection) {
		Query<EoeMarketRedirection> q = datastore.createQuery(EoeMarketRedirection.class).filter("_id =",
				eoeMarketRedirection.getId());
		datastore.updateFirst(q, eoeMarketRedirection, true);
		//datastore.save(eoeMarketRedirection);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(EoeMarketRedirection.class).filter("url =", url));
	}

}
