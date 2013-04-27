package com.babeeta.appstore.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
/**
 * 增长序列 
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
public class StoredSequence {
	@Id
	private String collectionName;

	private int value;

	public String getCollectionName() {
		return collectionName;
	}

	public int getValue() {
		return value;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
