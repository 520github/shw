/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.dao.AppUpdateNoticeDao;
import com.babeeta.appstore.entity.AppUpdateNotice;

/**
 * APP应用更新通知
 * 
 * @author xuehui.miao
 * 
 */
public class AppUpdateNoticeDaoImpl extends BasicDaoImpl implements
		AppUpdateNoticeDao {

	@Override
	public Iterator<AppUpdateNotice> find() {
		return datastore.createQuery(AppUpdateNotice.class).iterator();
	}

	@Override
	public void remove(AppUpdateNotice appUpdateNotice) {
		datastore.delete(appUpdateNotice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppUpdateNoticeDao#save(java.util.List)
	 */
	@Override
	public void save(List<AppUpdateNotice> list) {
		datastore.save(list);
	}

}
