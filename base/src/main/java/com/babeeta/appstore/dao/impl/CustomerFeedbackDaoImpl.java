package com.babeeta.appstore.dao.impl;

import com.babeeta.appstore.dao.CustomerFeedbackDao;
import com.babeeta.appstore.entity.CustomerFeedback;

public class CustomerFeedbackDaoImpl extends BasicDaoImpl implements
		CustomerFeedbackDao {
	/**
	 * 存储一个客户反馈记录
	 */
	@Override
	public void save(CustomerFeedback c) {
		this.datastore.save(c);
	}
}
