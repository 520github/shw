package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.EoeMarketApplicationDetailDao;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;
import com.google.code.morphia.query.Query;

public class EoeMarketApplicationDetailDaoImpl extends BasicDaoImpl implements
		EoeMarketApplicationDetailDao {
	
	@Override
	public EoeMarketApplicationDetail findByUrl(String url) {
		return datastore.createQuery(EoeMarketApplicationDetail.class).
		         filter("_id =", url).get();
	}
	
	@Override
	public EoeMarketApplicationDetail findByRedirectionUrl(String redirectionUrl) {
		return datastore.createQuery(EoeMarketApplicationDetail.class).
		         filter("redirectionUrl =", redirectionUrl).get();
	}
	
	@Override
	public void saveEoeMarketApplictionDetail(
			EoeMarketApplicationDetail eoeMarketApplicationDetail) {
		Query<EoeMarketApplicationDetail> q = datastore.createQuery(EoeMarketApplicationDetail.class).filter("_id =",
				eoeMarketApplicationDetail.getId());
		datastore.updateFirst(q, eoeMarketApplicationDetail, true);
		//datastore.save(eoeMarketApplicationDetail);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(EoeMarketApplicationDetail.class).filter("url =", url));
	}

}
