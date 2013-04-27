/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.WeiBo;

/**
 * 存储微博账号信息
 * @author xuehui.miao
 *
 */
public interface WeiBoDao {
	/**
	 * 通过微博账号获取微博用户信息
	 * 
	 * @param accountNo 微博账号
	 * @return
	 */
	public WeiBo findByAccountNo(String accountNo);
	
	/**
	 * 通过微博用户唯一标识获取微博用户信息
	 * 
	 * @param uid 微博用户唯一标识
	 * @return
	 */
	public WeiBo findByUid(String uid);
	
	/**
	 * 保存一条微博账号信息
	 * 
	 * @param weiBo
	 */
	public void save(WeiBo weiBo);
	
	
	/**
	 * 根据微博用户唯一标识更新微博用户信息，如果未发现就插入
	 * 
	 * @param weiBo
	 */
	public void updateByUidOrCreate(WeiBo weiBo);
}
