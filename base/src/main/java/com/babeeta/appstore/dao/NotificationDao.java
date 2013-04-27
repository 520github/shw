package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.dao.impl.EstimatableIterator;
import com.babeeta.appstore.entity.Notification;

public interface NotificationDao {
	/**
	 * 根据id删除消息
	 * 
	 * @param id
	 */
	public void deleteById(String id);

	/**
	 * 根据ownerId和collapseKey删除通知
	 * 
	 * @param ownerId
	 * @param collapseKey
	 */
	public void deleteByOwnerIdAndCollapseKey(String ownerId, String collapseKey);

	/**
	 * 通过id获取消息体
	 * 
	 * @param id
	 * @return
	 */
	public Notification findById(String id);

	/**
	 * 获取用户的某个消息
	 * 
	 * @param ownerId
	 * @param collapseKey
	 * @return
	 */
	public Notification findByOwnerIdAndCollapseKey(String ownerId,
			String collapseKey);

	/**
	 * 获取从某个消息(since)以后的消息
	 * 
	 * @param ownerId
	 *            用户token
	 * @param since
	 *            上次发给该用户的最后的消息ID
	 * @param limit
	 *            读取消息的数量
	 * @return 消息列表
	 */
	public List<Notification> findByOwnerIdAndSince(String ownerId,
			String since, int limit);

	/**
	 * 获取正常状态未读的消息数量（作为badge的显示数）
	 * 
	 * @param ownerId
	 *            用户token
	 * @param since
	 *            上次发给该用户的最后的消息ID
	 * @return
	 */
	public long findNormalUnreadCount(String ownerId,
			String since);

	/**
	 * 按collapseKey迭代所有未删除的Notification
	 * 
	 * @param action
	 * @return
	 */
	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey);

	public EstimatableIterator<Notification> iteratorByCollapseKey(
			String collapseKey,
			String since);

	/**
	 * 消息存储
	 * 
	 * @param n
	 * @return
	 */
	public Notification save(Notification n);
}
