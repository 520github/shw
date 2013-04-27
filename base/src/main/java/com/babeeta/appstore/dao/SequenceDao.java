package com.babeeta.appstore.dao;

public interface SequenceDao<T> {

	public T nextSequence(String cellName);
}
