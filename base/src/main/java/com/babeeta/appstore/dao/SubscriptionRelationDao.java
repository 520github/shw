package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.SubscriptionRelation;

public interface SubscriptionRelationDao {

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

	public List<String> distinctToken();

	/**
	 * 根据品牌和设备查询订阅关系
	 * 
	 * @param brand
	 * @param token
	 * @return
	 */
	public SubscriptionRelation findByBrandAndToken(String brand, String token);

	/**
	 * 根据token查询品牌ID列表
	 * 
	 * @param token
	 * @return
	 */
	public String[] getBrandIdsByToken(String token);

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
