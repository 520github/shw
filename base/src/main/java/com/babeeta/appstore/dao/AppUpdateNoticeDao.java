/**
 * 
 */
package com.babeeta.appstore.dao;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.entity.AppUpdateNotice;

/**
 * APP应用更新通知
 * 
 * @author xuehui.miao
 * 
 */
public interface AppUpdateNoticeDao {

	/**
	 * 查找APP应用更新通知
	 * 
	 * @param offset
	 *            起始位置
	 * @param limit
	 *            限制数量
	 * @return
	 */
	public Iterator<AppUpdateNotice> find();

	/**
	 * 删除一条APP应用更新通知
	 * 
	 * @param appUpdateNotice
	 */
	public void remove(AppUpdateNotice appUpdateNotice);

	/**
	 * 保存APP应用更新通知的列表
	 * 
	 * @param list
	 *            待保存的更新通知列表
	 */
	public void save(List<AppUpdateNotice> list);
}
