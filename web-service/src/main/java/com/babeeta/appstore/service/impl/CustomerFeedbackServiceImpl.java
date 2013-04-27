package com.babeeta.appstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.CustomerFeedbackDao;
import com.babeeta.appstore.entity.CustomerFeedback;
import com.babeeta.appstore.service.CustomerFeedbackService;

@Service("customerFeedbackService")
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {
	private CustomerFeedbackDao customerFeedbackdao;

	@Override
	public void saveCustomerFeedback(CustomerFeedback c) {
		customerFeedbackdao.save(c);
	}

	@Autowired
	@Required
	public void setCustomerFeedbackdao(CustomerFeedbackDao customerFeedbackdao) {
		this.customerFeedbackdao = customerFeedbackdao;
	}
}
