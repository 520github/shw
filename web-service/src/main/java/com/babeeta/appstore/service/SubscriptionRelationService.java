package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.SubscriptionRelation;

public interface SubscriptionRelationService {

	/**
	 * 创建品牌订阅关系
	 * 
	 * @param subscriptionRelation
	 */
	public void createSubscriptionRelation(
			SubscriptionRelation subscriptionRelation);

	/**
	 * 删除品牌订阅关系
	 * 
	 * @param token
	 * @param brandId
	 */
	public void deleteSubscriptionRelation(String token, String brandId);

	public String[] getBrandIdsByToken(String token);

	public SubscriptionRelation getSubscriptionRelation(String token,
			String brandId);

	/**
	 * 根据token查询订阅关系
	 * 
	 * @param token
	 * @return
	 */
	public List<SubscriptionRelation> getSubscriptionRelationListByToken(
			String token);

	/**
	 * 更新品牌访问时间
	 */
	public void updateSubscriptionRelation(String token, String brandId);

}
