package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.CustomerFeedback;

public interface CustomerFeedbackDao {
	/**
	 * 存储一个客户反馈记录
	 */
	public void save(CustomerFeedback c);
}
