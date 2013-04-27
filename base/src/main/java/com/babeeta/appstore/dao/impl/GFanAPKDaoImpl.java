/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.GFanAPKDao;
import com.babeeta.appstore.entity.GFanAPK;
import com.google.code.morphia.query.Query;

/**
 * @author xuehui.miao
 *
 */
public class GFanAPKDaoImpl extends BasicDaoImpl implements GFanAPKDao {
	
	@Override
	public GFanAPK findByUrl(String url) {
		return datastore.createQuery(GFanAPK.class).filter("_id =", url).get();
	}
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.GFanAPKDao#saveGFanAPK(com.babeeta.appstore.entity.GFanAPK)
	 */
	@Override
	public void saveGFanAPK(GFanAPK gFanAPK) {
		Query<GFanAPK> q = datastore.createQuery(GFanAPK.class).filter("_id =",
				gFanAPK.getId());
		datastore.updateFirst(q, gFanAPK, true);
		//datastore.save(gFanAPK);
	}
	
	@Override
	public void removeByUrl(String url) {
		datastore.delete(datastore.createQuery(GFanAPK.class).filter("url", url));
	}

}
