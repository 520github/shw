package com.babeeta.appstore.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class BasicDaoImpl {

	private final static Logger logger = LoggerFactory
			.getLogger(BasicDaoImpl.class);

	private static Mongo mongo;
	private static Morphia morphia ;
	protected static Datastore datastore;

	public BasicDaoImpl() {
		if(datastore != null)return;
		
		morphia = new Morphia();
		try {
			MongoOptions mongoOptions = new MongoOptions();
			mongoOptions.threadsAllowedToBlockForConnectionMultiplier = 512;
			mongoOptions.connectionsPerHost = 512;
			mongoOptions.autoConnectRetry = true;
			mongo = new Mongo(System.getProperty("mongodb.host", "mongodb"),//mongodb
					mongoOptions);
			morphia.mapPackage("com.babeeta.appstore.entity");
			datastore = morphia.createDatastore(mongo,
					System.getProperty("mongodb.db", "android-market"));//my-android-market
			logger.info("Mongod: {} / {}", mongo.getAllAddress(), datastore
					.getDB().getName());
			datastore.ensureIndexes();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	protected <E> EstimatableIterator<E> estimatableIterator(Query<E> query) {
		return new EstimatableIterator<E>(query.iterator(), query.countAll());
	}

	protected Morphia getMorphia() {
		return morphia;
	}

	public void shutdown() {
		if (mongo != null) {
			mongo.close();
		}
	}
}
