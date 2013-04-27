package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.MarkerCounter;

public interface MarkerCounterDao {
	public long countById(MarkerCounter.MarkerCounterId id, String value);

	public void decreaseCounter(MarkerCounter.MarkerCounterId id, String value);

	public List<MarkerCounter> findByEntity(String entityId);

	public MarkerCounter findById(MarkerCounter.MarkerCounterId id);

	public void increaseCounter(MarkerCounter.MarkerCounterId id, String value);
}
