/**
 * 
 */
package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.WeiBoDao;
import com.babeeta.appstore.entity.WeiBo;
import com.google.code.morphia.query.Query;

/**
 * 存储微博账号信息
 * @author xuehui.miao
 *
 */
public class WeiBoDaoImpl extends BasicDaoImpl implements WeiBoDao {

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.WeiBoDao#findByAccountNo(java.lang.String)
	 */
	@Override
	public WeiBo findByAccountNo(String accountNo) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.WeiBoDao#findByUid(java.lang.String)
	 */
	@Override
	public WeiBo findByUid(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.dao.WeiBoDao#save(com.babeeta.appstore.entity.WeiBo)
	 */
	@Override
	public void save(WeiBo weiBo) {
		datastore.save(weiBo);
	}

	/* (non-Javadoc)
	 * @see com.babeeta.app
	 * store.dao.WeiBoDao#updateByUidOrCreate(com.babeeta.appstore.entity.WeiBo)
	 */
	@Override
	public void updateByUidOrCreate(WeiBo weiBo) {
		Query<WeiBo> query = datastore.createQuery(WeiBo.class).field("uid").equal(weiBo.getUid());
		datastore.updateFirst(query, weiBo, true);
	}

}
