package com.babeeta.appstore.dao.impl;

import java.util.UUID;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.Mongo;

@Ignore
public abstract class AbstractDaoImplTest {

	private DB db;
	private String dbName;
	private Mongo mongo;

	public AbstractDaoImplTest() {
		super();
	}

	protected DB getDb() {
		return db;
	}

	protected String getDbName() {
		return this.dbName;
	}

	protected Mongo getMongo() {
		return this.mongo;
	}

	@Before
	public void setUp() {
		System.setProperty("mongodb.host", "192.168.20.150");
		dbName = UUID.randomUUID().toString();
		System.setProperty("mongodb.db", getDbName());
		try {

			mongo = new Mongo("192.168.20.150");
		} catch (Exception e) {
			Assume.assumeNoException(e);
		}
		db = mongo.getDB(dbName);
	}

	@After
	public void tearDown() {
		if (db != null) {
			db.dropDatabase();
		}
		if (mongo != null) {
			mongo.close();
		}
	}

	@Test
	public void testNull() {

	}

}