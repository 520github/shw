package com.babeeta.appstore.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.babeeta.appstore.dao.SubscriptionRelationDao;
import com.babeeta.appstore.entity.SubscriptionRelation;

public class SubscriptionRelationDaoImpl extends BasicDaoImpl implements
		SubscriptionRelationDao {

	@Override
	public void createSubscriptionRelation(
			SubscriptionRelation subscriptionRelation) {
		datastore.save(subscriptionRelation);
	}

	@Override
	public void deleteSubscriptionRelation(String token, String brandId) {
		datastore.delete(datastore.createQuery(SubscriptionRelation.class)
				.field("token").equal(token)
				.field("brandId").equal(brandId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> distinctToken() {
		return datastore.getCollection(SubscriptionRelation.class).distinct(
				"token");
	}

	@Override
	public SubscriptionRelation findByBrandAndToken(String brand, String token) {
		return datastore.createQuery(SubscriptionRelation.class)
				.field("token").equal(token).field("brandId").equal(brand)
				.get();
	}

	@Override
	public String[] getBrandIdsByToken(String token) {
		List<SubscriptionRelation> list = datastore
				.createQuery(SubscriptionRelation.class)
				.field("token").equal(token)
				.asList();

		List<String> brandIds = new ArrayList<String>();
		String[] temp = new String[] {};
		for (SubscriptionRelation subscriptionRelation : list) {
			brandIds.add(subscriptionRelation.getBrandId());
		}
		return brandIds.toArray(temp);
	}

	@Override
	public List<SubscriptionRelation> getSubscriptionRelationListByToken(
			String token) {
		return datastore.createQuery(SubscriptionRelation.class)
				.field("token").equal(token)
				.order("lastVisitDate")
				.asList();
	}

	@Override
	public void updateSubscriptionRelation(String token, String brandId) {

		datastore.findAndModify(
				datastore.createQuery(SubscriptionRelation.class)
						.field("token").equal(token)
						.field("brandId").equal(brandId),
				datastore.createUpdateOperations(
						SubscriptionRelation.class)
						.set("lastVisitDate", new Date()));
	}
}
