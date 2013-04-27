package com.babeeta.appstore.manager.service;

import java.util.List;

import com.babeeta.appstore.entity.BestApp;

public interface BestAppService {

	public void delete(String id);

	public List<BestApp> findAList();

	public void save(BestApp bestapp);

	public void saveAppBest();

}
