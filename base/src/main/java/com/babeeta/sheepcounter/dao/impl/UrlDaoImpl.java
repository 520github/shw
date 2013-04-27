package com.babeeta.sheepcounter.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babeeta.sheepcounter.dao.UrlDao;
import com.babeeta.sheepcounter.entity.Url;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;

public class UrlDaoImpl implements UrlDao {

	private final static Logger logger = LoggerFactory
			.getLogger(UrlDaoImpl.class);

	private static String increaseHexId(String org) {
		int orgInt = Integer.parseInt(org, 16);
		return Integer.toHexString(orgInt + 1).toString();
	}

	protected final Datastore datastore;

	private final Mongo mongo;

	private final Morphia morphia;

	public UrlDaoImpl() {
		morphia = new Morphia();
		try {
			MongoOptions mongoOptions = new MongoOptions();
			mongoOptions.threadsAllowedToBlockForConnectionMultiplier = 512;
			mongoOptions.connectionsPerHost = 512;
			mongoOptions.autoConnectRetry = true;
			mongo = new Mongo(System.getProperty("mongodb.host", "mongodb"),
					mongoOptions);
			morphia.mapPackage("com.babeeta.sheepcounter.entity");
			datastore = morphia.createDatastore(mongo, "SheepCounter");
			logger.info("Mongod: {} / {}", mongo.getAllAddress(), datastore
					.getDB().getName());
			datastore.ensureIndexes();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Url> find(String keywords, int offset, int limit) {
		Query<Url> q = this.datastore.createQuery(Url.class).field("name")
				.containsIgnoreCase(keywords).offset(offset).limit(limit);
		return q.asList();
	}

	private Url findLastest() {
		return this.datastore.createQuery(Url.class).order("-_id").limit(1)
				.get();
	}

	@Override
	public Url save(Url url) {
		if (url.getId() == null || url.getId().length() == 0) {
			Url lastest = findLastest();
			if (lastest != null) {
				String lastestId = lastest.getId();
				url.setId(increaseHexId(lastestId));
			} else {
				url.setId("3000");
			}
		}
		this.datastore.save(url);
		return url;
	}

}
