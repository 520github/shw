/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.GFanApplicationDetailDao;
import com.babeeta.appstore.entity.GFanApplicationDetail;
import com.google.code.morphia.query.Query;

/**
 * @author xuehui.miao
 *
 */
public class GFanApplicationDetailDaoImpl extends BasicDaoImpl implements
		GFanApplicationDetailDao {
	
	@Override
	public GFanApplicationDetail findByUrl(String url) {
		return datastore.createQuery(GFanApplicationDetail.class).
		         filter("_id =", url).get();
	}
	
	@Override
	public GFanApplicationDetail findByRedirectionUrl(String redirectionUrl) {
		return datastore.createQuery(GFanApplicationDetail.class).
		         filter("redirectionUrl =", redirectionUrl).get();
	}
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.GFanApplicationDetailDao#saveGFanApplicationDetail(com.babeeta.appstore.entity.GFanApplicationDetail)
	 */
	@Override
	public void saveGFanApplicationDetail(
			GFanApplicationDetail gFanApplicationDetail) {
		Query<GFanApplicationDetail> q = datastore.createQuery(GFanApplicationDetail.class).filter("_id =",
				gFanApplicationDetail.getId());
		datastore.updateFirst(q, gFanApplicationDetail, true);
		//datastore.save(gFanApplicationDetail);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(GFanApplicationDetail.class).filter("url =", url));
	}
}
