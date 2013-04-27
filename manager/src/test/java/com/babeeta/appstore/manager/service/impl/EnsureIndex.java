package com.babeeta.appstore.manager.service.impl;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.babeeta.appstore.dao.impl.AppDaoImpl;
import com.babeeta.appstore.dao.impl.LuceneWrapper;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.ApplicationMarketDetail;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class EnsureIndex {
	public static void main(String[] args) {
		EnsureIndex eei = new EnsureIndex();
		eei.process();
	}

	AppDaoImpl appDao;
	DBCollection dbConn;
	Mongo mongo;

	public EnsureIndex() {
		try {
			mongo = new Mongo("mongodb");
			dbConn = mongo.getDB("appstore").getCollection("App");
			appDao = new AppDaoImpl();
			LuceneWrapper luceneWrapper = new LuceneWrapper();
			appDao.setLuceneWrapper(luceneWrapper);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public void process() {
		DBCursor cur = dbConn.find();
		System.out.println("There are " + cur.size() + " apps!");
		int publishedCount = 0;
		long start = System.currentTimeMillis();
		while (cur.hasNext()) {
			DBObject org = cur.next();
			appDao.deleteIndex(org.get("_id").toString());
			if (org.get("status").toString().equals("published")) {
				System.out.println("Ensuring index for " + org.get("_id"));
				App e = toEntity(org);
				appDao.addIndex(e);
				publishedCount++;
			}
		}
		System.out.println("There are " + publishedCount + " published apps!");
		System.out.println("Spend time :"
				+ (System.currentTimeMillis() - start) / 1000 + "s");
	}

	private App toEntity(DBObject db) {
		App app = new App();
		app.setId(db.get("_id").toString());
		app.getDetail().setName(db.get("name").toString());
		BasicDBList catalogs = (BasicDBList) db.get("catalog");
		app.setCatalog(Arrays.asList(catalogs.toArray(new String[catalogs
				.size()])));
		BasicDBList brands = (BasicDBList) db.get("brand");
		app.setBrand(brands == null ? null : Arrays.asList(brands
				.toArray(new String[brands.size()])));
		BasicDBList keywords = (BasicDBList) db.get("brand");
		app.setKeywords(keywords == null ? null : Arrays
				.asList(keywords.toArray(new String[keywords.size()])));
		app.setIntroduction(db.get("introduction").toString());
		app.setDescription(db.get("description").toString());
		app.setReview(db.get("review").toString());
		app.getDetail().setLogo(
				db.get("logo") == null ? "" : db.get("logo").toString());
		app.getDetail().setPrice(db.get("price").toString());
		ApplicationMarketDetail.Score score = new ApplicationMarketDetail.Score();
		score.setStar(db.get("wholeVersionScoreStar.star") == null ? 0
				: (Float) db.get("wholeVersionScoreStar.star"));
		score.setVote(db.get("wholeVersionScoreStar.vote") == null ? 0
				: (Integer) db.get("wholeVersionScoreStar.vote"));
		app.getDetail().setWholeVersionScore(score);
		return app;
	}
}
