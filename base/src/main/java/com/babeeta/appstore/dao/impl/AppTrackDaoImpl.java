/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.AppTrackDao;
import com.babeeta.appstore.entity.AppTrack;

/**
 * 应用日志跟踪
 * @author xuehui.miao
 *
 */
public class AppTrackDaoImpl extends BasicDaoImpl implements AppTrackDao {

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.AppTrackDao#save(com.babeeta.appstore.entity.AppTrack)
	 */
	@Override
	public void save(AppTrack appTrack) {
		datastore.save(appTrack);
	}

}
