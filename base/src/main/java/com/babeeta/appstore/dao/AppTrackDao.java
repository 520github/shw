/**
 * 
 */
package com.babeeta.appstore.dao;

import com.babeeta.appstore.entity.AppTrack;

/**
 * 应用日志跟踪
 * @author xuehui.miao
 *
 */
public interface AppTrackDao {
	
	/**
	 * 保存一个AppTrack
	 * 
	 * @param appTrack
	 */
	public void save(AppTrack appTrack);
}
