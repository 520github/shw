package com.babeeta.appstore.dao;

import java.util.Iterator;
import java.util.List;

import com.babeeta.appstore.entity.BestApp;

public interface BestAppDao {

	public void delete(String id);

	public List<BestApp> findAList();

	public Iterator<BestApp> findAll();

	public void save(BestApp bestapp);

}
