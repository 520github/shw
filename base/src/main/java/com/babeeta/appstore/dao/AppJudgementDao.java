package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.AppJudgement;

public interface AppJudgementDao {
	/**
	 * 所有擂台的数量
	 * 
	 * @param keyword
	 * @return
	 */
	public long countAll(String keyword);

	/**
	 * 根据id获取擂台
	 * 
	 * @param id
	 * @return
	 */
	public AppJudgement findById(String id);

	/**
	 * 擂台列表查询
	 * 
	 * @param keyword
	 * @param limit
	 * @param offset
	 * @return 擂台列表
	 */
	public List<AppJudgement> findByKeyword(String keyword, int limit,
			int offset);

	/**
	 * 获取历史擂台榜单列表
	 * 
	 * @return
	 */
	public List<AppJudgement> findHistory();

	/**
	 * 获取正在进行擂台榜单列表
	 * 
	 * @return
	 */
	public List<AppJudgement> findOngoing();

	/**
	 * 保存
	 * 
	 * @param appJudgement
	 */
	public void save(AppJudgement appJudgement);

}
