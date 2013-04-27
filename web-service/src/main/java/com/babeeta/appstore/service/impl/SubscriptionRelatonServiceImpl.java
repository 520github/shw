package com.babeeta.appstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.BrandDao;
import com.babeeta.appstore.dao.SubscriptionRelationDao;
import com.babeeta.appstore.entity.SubscriptionRelation;
import com.babeeta.appstore.service.SubscriptionRelationService;

@Service("subscriptionRelationService")
public class SubscriptionRelatonServiceImpl implements
		SubscriptionRelationService {

	private BrandDao brandDao;
	private SubscriptionRelationDao subscriptionRelationDao;

	@Override
	public void createSubscriptionRelation(
			SubscriptionRelation subscriptionRelation) {
		SubscriptionRelation sr = subscriptionRelationDao.findByBrandAndToken(
				subscriptionRelation.getBrandId(),
				subscriptionRelation.getToken());
		if (sr == null) {
			subscriptionRelationDao
					.createSubscriptionRelation(subscriptionRelation);
			brandDao.increaseBrandCounter(subscriptionRelation.getBrandId(),
					"subscripted");
		}
	}

	@Override
	public void deleteSubscriptionRelation(String token, String brandId) {
		SubscriptionRelation sr = subscriptionRelationDao.findByBrandAndToken(
				brandId,
				token);
		if (sr != null) {
			subscriptionRelationDao.deleteSubscriptionRelation(token, brandId);
			brandDao.decreaseBrandCounter(brandId,
					"subscripted");
		}
	}

	@Override
	public String[] getBrandIdsByToken(String token) {
		return subscriptionRelationDao.getBrandIdsByToken(token);
	}

	@Override
	public SubscriptionRelation getSubscriptionRelation(String token,
			String brandId) {
		return subscriptionRelationDao.findByBrandAndToken(
				brandId,
				token);

	}

	@Override
	public List<SubscriptionRelation> getSubscriptionRelationListByToken(
			String token) {
		return subscriptionRelationDao
				.getSubscriptionRelationListByToken(token);
	}

	@Autowired
	@Required
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}

	@Autowired
	@Required
	public void setSubscriptionRelationDao(
			SubscriptionRelationDao subscriptionRelationDao) {
		this.subscriptionRelationDao = subscriptionRelationDao;
	}

	@Override
	public void updateSubscriptionRelation(String token, String brandId) {
		subscriptionRelationDao.updateSubscriptionRelation(token, brandId);
	}
}
