/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.entity.GooglePlay;
import com.google.code.morphia.query.Query;

/**
 * @author xuehui.miao
 *
 */
public class GooglePlayDaoImpl extends BasicDaoImpl implements
		com.babeeta.appstore.dao.GooglePlayDao {
	
	@Override
	public GooglePlay findByUrl(String url) {
		return datastore.createQuery(GooglePlay.class)
		         .filter("_id =", url).get();
	}
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.GooglePlayDao#saveGooglePlay(com.babeeta.appstore.entity.GooglePlay)
	 */
	@Override
	public void saveGooglePlay(GooglePlay googlePlay) {
		Query<GooglePlay> q = datastore.createQuery(GooglePlay.class).filter("_id =",
				googlePlay.getId());
		datastore.updateFirst(q, googlePlay, true);
		//datastore.save(googlePlay);
	}

}
