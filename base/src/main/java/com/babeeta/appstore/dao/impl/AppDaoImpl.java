package com.babeeta.appstore.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;
import com.babeeta.appstore.entity.AppSearchQuery;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.Query;
import com.google.common.base.Strings;

public class AppDaoImpl extends BasicDaoImpl implements AppDao {
	private final static Logger logger = LoggerFactory
			.getLogger(AppDaoImpl.class);

	private LuceneWrapper luceneWrapper;

	private Query<App> createFindQuery(String search, String catalog,
			AppStatus[] appStatus) {
		Query<App> query = datastore.createQuery(App.class);
		if (!Strings.isNullOrEmpty(search)) {
			query.field("detail.name").contains(search);
		}
		if (!Strings.isNullOrEmpty(catalog)) {
			query.field("catalog").equal(catalog);
		}
		Criteria[] criteria = new Criteria[appStatus.length];
		for (int i = 0; i < appStatus.length; i++) {
			criteria[i] = query.criteria("status").equal(appStatus[i]);
		}
		query.or(criteria);
		return query;
	}

	/**
	 * 获取数字版本号
	 * 
	 * @param version
	 *            版本号
	 * @return
	 */
	private int getNumVersion(String version) {
		int numVersion = 0;
		if (Strings.isNullOrEmpty(version)) {
			return numVersion;
		}

		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < version.length(); i++) {
			String str = version.substring(i, i + 1);
			try {
				Integer.parseInt(str);
				temp.append(str);
			} catch (Exception e) {
			}
		}
		if (temp.toString().trim().equals("")) {
			return numVersion;
		}
		numVersion = Integer.parseInt(temp.toString());
		return numVersion;
	}

	/**
	 * 按.分割版本信息
	 * 
	 * @param version
	 * @return
	 */
	private String[] getVersionArray(String version) {
		if (version == null) {
			return null;
		}
		return StringUtils.split(version, ".");
	}

	@Override
	public void addIndex(App e) {
		Document doc = new Document();
		doc.add(new Field("id", e.getId(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));

		Field name = new Field("name", Strings.nullToEmpty(e
				.getDetail().getName()),
				Field.Store.YES, Field.Index.ANALYZED);
		name.setBoost(4);
		doc.add(name);

		Field catalog = new Field("catalog",
				Strings.nullToEmpty(e.getCatalog() == null ? "" : e
						.getCatalog().toString()), Field.Store.YES,
				Field.Index.ANALYZED);
		catalog.setBoost(3);
		doc.add(catalog);

		Field keyword = new Field("keyword", Strings.nullToEmpty(e
				.getKeywords() == null ? "" : e.getKeywords().toString()),
				Field.Store.YES, Field.Index.ANALYZED);
		keyword.setBoost(3);
		doc.add(keyword);

		Field introduction = new Field("introduction", Strings.nullToEmpty(e
				.getIntroduction()), Field.Store.YES, Field.Index.ANALYZED);
		introduction.setBoost(2);
		doc.add(introduction);

		Field description = new Field("description", Strings.nullToEmpty(e
				.getDescription()), Field.Store.YES, Field.Index.ANALYZED);
		description.setBoost(2);
		doc.add(description);

		Field review = new Field("review", Strings.nullToEmpty(e.getReview()),
				Field.Store.YES, Field.Index.ANALYZED);
		review.setBoost(2);
		doc.add(review);
		try {
			luceneWrapper.getWriter().addDocument(doc);
			luceneWrapper.getWriter().commit();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean compareAppVersion(String oldVersion, String newVersion) {
		boolean result = false;
		String oldArray[] = getVersionArray(oldVersion);
		String newArray[] = getVersionArray(newVersion);
		if (oldArray == null || oldArray == null) {
			return result;
		}
		for (int i = 0; i < oldArray.length; i++) {
			String oldVer = oldArray[i];// 旧版本号
			String newVer = null;
			try {
				newVer = newArray[i];// 新版本号
			} catch (Exception e) {
				logger.error(
						"version length is not equal,oldVersion[{}],newVersion[{}]......",
						oldVersion, newVersion);
				return false;
			}
			try {
				if (Integer.parseInt(newVer) > Integer.parseInt(oldVer)) {
					return true;// 发现更新了
				}
			} catch (Exception e) {
				logger.error(
						"version is not only number,oldVersion[{}],newVersion[{}]......",
						oldVersion, newVersion);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#countUnprocessed()
	 */
	@Override
	public long count(String search, AppStatus[] appStatus) {
		return this.count(search, null, appStatus);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#count(java.lang.String,
	 * java.lang.String, com.babeeta.appstore.entity.App.AppStatus[])
	 */
	@Override
	public long count(String search, String catalog, AppStatus[] appStatus) {
		Query<App> query = createFindQuery(search, catalog, appStatus);
		return query.countAll();
	}

	@Override
	public long count(String appId, String catalog, String language,
			String keyword) {

		AppSearchQuery appSearchQuery = new AppSearchQuery();

		Query<App> query = datastore.createQuery(App.class).order(
				"-lastModified");

		appSearchQuery.setDataStoreFiled(query, "id", appId);
		appSearchQuery.setDataStoreFiled(query, "catalog", catalog);
		appSearchQuery.setDataStoreFiled(query, "detail.language", language);
		appSearchQuery.setDataStoreFiled(query, "detail.name", keyword);

		return query.countAll();
	}

	@Override
	public long countBest() {
		Query<App> q = datastore.createQuery(App.class).field("best")
				.equal(true);
		return q.countAll();
	}

	@Override
	public long countBestSince(Date since) {
		Query<App> q = datastore.createQuery(App.class).field("best")
				.equal(true).filter("bestLastModified > ", since);
		return q.countAll();
	}

	@Override
	public long countPublishedByCatalogName(String catalogName) {
		Query<App> q = datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("catalog")
				.equal(catalogName);
		return q.countAll();
	}

	@Override
	public int countSearch(String search) {
		int count = 0;
		IndexSearcher searcher = null;
		try {
			searcher = new IndexSearcher(luceneWrapper.getDirectory());
			String[] fields = { "name", "keyword", "description", "review",
					"introduction", "catalog" };
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					Version.LUCENE_32, fields,
					luceneWrapper.getPaodingAnalyzer());
			parser.setDefaultOperator(QueryParser.Operator.AND);// 以空格分开的短语是and的关系,默认时or
			org.apache.lucene.search.Query query = parser.parse(search);
			TopDocs docs = searcher.search(query, null, Integer.MAX_VALUE);
			count = docs.totalHits;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (searcher != null) {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public void deleteIndex(String id) {
		try {
			luceneWrapper.getWriter().deleteDocuments(new Term("id", id));
			luceneWrapper.getWriter().optimize();
			luceneWrapper.getWriter().commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#findUnprocessed(long, int)
	 */
	@Override
	public List<App> find(String search, AppStatus[] appStatus, int offset,
			int limit) {
		return find(search, null, appStatus, offset, limit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#find(java.lang.String,
	 * java.lang.String, com.babeeta.appstore.entity.App.AppStatus[], int, int)
	 */
	@Override
	public List<App> find(String search, String catalog, AppStatus[] appStatus,
			int offset, int limit) {
		Query<App> query = createFindQuery(search, catalog, appStatus);
		return query.order("-lastModified").offset(offset).limit(limit)
				.asList();
	}

	@Override
	public List<App> findAll(String appId, String catalog, String language,
			String keyword, int offset, int limit) {
		AppSearchQuery appSearchQuery = new AppSearchQuery();

		Query<App> query = datastore.createQuery(App.class)
				.order("-lastModified").offset(offset).limit(limit);

		appSearchQuery.setDataStoreFiled(query, "id", appId);
		appSearchQuery.setDataStoreFiled(query, "catalog", catalog);
		appSearchQuery.setDataStoreFiled(query, "detail.language", language);
		appSearchQuery.setDataStoreFiled(query, "detail.name", keyword);

		return query.asList();
	}

	@Override
	public List<App> findAppByCatalogOrderByVote(String catalogname,
			int offset,
			int limit) {
		// TODO Auto-generated method stub
		return datastore.createQuery(App.class).field("catalog")
				.equal(catalogname).field("status")
				.equal("published").order("-detail.wholeVersionScore.vote")
				.offset(offset)
				.limit(limit).asList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#findByAppId(int)
	 */
	@Override
	public App findByAppId(String id) {
		return datastore.createQuery(App.class).filter("_id =", id).get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#findByBrand(java.lang.String,
	 * boolean)
	 */
	@Override
	public List<App> findByBrandAndStatus(String brandName, AppStatus appStatus) {
		return datastore.createQuery(App.class).field("brand").equal(brandName)
				.field("status").equal(appStatus).order("asciiName").asList();

	}

	@Override
	public List<App> findByCatalogAndBrand(String catalogName,
			String brandName, int offset, int limit) {
		// TODO Auto-generated method stub
		return datastore.createQuery(App.class).field("catalog")
				.equal(catalogName).field("brand").equal(brandName)
				.field("status").equal("published").order("-lastModified")
				.offset(offset).limit(limit).asList();
	}

	@Override
	public List<App> findByIds(List<String> ids) {
		return datastore.createQuery(App.class).filter("_id in", ids.toArray())
				.asList();
	}

	@Override
	public List<App> findFrontPublishedByLastModified(int offset, int limit) {
		// TODO 使用lastUpdate排序
		return datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).filter("escapeRank !=", true)
				.order("-detail.lastUpdate").offset(offset).limit(limit)
				.asList();
	}

	@Override
	public List<App> findFrontPublishedByScore(int offset, int limit) {
		// TODO 使用Score.vote排序
		return datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).filter("escapeRank !=", true)
				.order("-detail.wholeVersionScore.vote").offset(offset)
				.limit(limit)
				.asList();
	}

	@Override
	public List<App> findPublishedByCatalogAndGroup(String catalog,
			String group, int offset, int limit) {
		Query<App> query = datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("catalog").equal(catalog)
				.field("group").equal(group).order("-lastModified")
				.offset(offset).limit(limit);
		return query.asList();
	}

	@Override
	public List<App> findPublishedByCatalogName(String catalogName) {
		Query<App> q = datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("catalog")
				.equal(catalogName).order("-detail.lastUpdate");
		return q.asList();
	}

	@Override
	public List<App> findPublishedByCatalogName(String catalogName, int offset,
			int limit) {
		Query<App> q = datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("catalog")
				.equal(catalogName).order("-detail.lastUpdate").offset(offset)
				.limit(limit);
		return q.asList();
	}

	@Override
	public App findPublishedById(String id) {
		return datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("_id").equal(id).get();
	}

	@Override
	public List<App> findPublishedByIds(List<String> ids) {
		return datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).filter("_id in", ids.toArray())
				.asList();
	}

	@Override
	public App getAppByRandom(double randomNum) {
		App app = datastore.createQuery(App.class).field("status")
				.equal(App.AppStatus.published).field("random")
				.greaterThanOrEq(randomNum).order("random").limit(1).get();
		if (app == null) {
			app = datastore.createQuery(App.class).field("status")
					.equal(App.AppStatus.published).field("random")
					.lessThan(randomNum).order("-random").limit(1).get();
		}
		return app;
	}

	@Override
	public List<App> getAppWithBest(int offset, int limit) {
		Query<App> q = datastore.createQuery(App.class).field("best")
				.equal(true).limit(limit).offset(offset)
				.order("-bestLastModified");
		return q.asList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.dao.AppDao#lock(long, java.lang.String)
	 */
	@Override
	public String lock(String id, String editor) {
		App updated = datastore.findAndModify(
				datastore.createQuery(App.class).field("_id").equal(id)
						.field("status").equal(AppStatus.unprocessed)
						.field("editor").equal(null), datastore
						.createUpdateOperations(App.class)
						.set("editor", editor).set("lastModified", new Date()));
		if (updated == null) {
			App target = datastore.find(App.class, "_id", id).get();
			if (target == null) {
				throw new EntityNotFoundException(App.class, id);
			} else {
				return target.getEditor();
			}
		} else {
			return updated.getEditor();
		}
	}

	@Override
	public void release(String id, String editor) {
		datastore.findAndModify(
				datastore.createQuery(App.class).field("_id").equal(id)
						.field("status").equal(AppStatus.unprocessed)
						.field("editor").equal(editor), datastore
						.createUpdateOperations(App.class).unset("editor"));
	}

	@Override
	public void save(App e) {
		datastore.save(e);
	}

	@Override
	public List<App> search(String search, int offset, int limit) {
		List<App> apps = new ArrayList<App>();
		IndexSearcher searcher = null;
		try {
			searcher = new IndexSearcher(luceneWrapper.getDirectory());
			String[] fields = { "name", "keyword", "description", "review",
					"introduction", "catalog" };
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					Version.LUCENE_32, fields,
					luceneWrapper.getPaodingAnalyzer());
			parser.setDefaultOperator(QueryParser.Operator.AND);// 以空格分开的短语是and的关系,默认时or
			org.apache.lucene.search.Query query = parser.parse(search);
			TopDocs docs = searcher.search(query, null, Integer.MAX_VALUE);
			for (int i = offset; i < docs.scoreDocs.length; i++) {
				if (i >= offset + limit) {
					break;
				}
				ScoreDoc sdoc = docs.scoreDocs[i];
				Document doc = searcher.doc(sdoc.doc);
				App app = findPublishedById(doc.get("id"));
				if (app != null) {
					apps.add(app);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (searcher != null) {
			try {
				searcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return apps;
	}

	public void setLuceneWrapper(LuceneWrapper luceneWrapper) {
		this.luceneWrapper = luceneWrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.babeeta.appstore.dao.AppDao#updateByAppIdOrCreate(com.babeeta.appstore
	 * .entity.App)
	 */
	@Override
	public void updateByAppIdOrCreate(App e) {
		Query<App> q = datastore.createQuery(App.class).filter("_id =",
				e.getId());
		datastore.updateFirst(q, e, true);
	}
}
