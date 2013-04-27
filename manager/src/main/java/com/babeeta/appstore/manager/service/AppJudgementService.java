package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.entity.AppJudgement;

public interface AppJudgementService {
	/**
	 * 查询数量
	 * 
	 * @param keyword
	 * @return
	 */
	public long countAll(String keyword);

	/**
	 * 查找所有的擂台列表
	 * 
	 * @param keyword
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<AppJudgement> findAppJudgements(String keyword, int limit,
			int offset);

	/**
	 * 根据id查找擂台
	 * 
	 * @param id
	 * @return
	 */
	public AppJudgement findById(String id);

	/**
	 * 存储AppJudgement
	 */
	public void save(AppJudgement appJudgement);
}
