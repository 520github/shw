package com.babeeta.appstore.dao.impl;

import org.bson.types.ObjectId;

import com.babeeta.appstore.dao.SequenceDao;

public class ObjectIdSequenceDaoImpl implements SequenceDao<String> {

	@Override
	public String nextSequence(String cellName) {
		return new ObjectId().toString();
	}

}
