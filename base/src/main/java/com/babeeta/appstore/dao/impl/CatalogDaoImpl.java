package com.babeeta.appstore.dao.impl;

import java.util.Collections;
import java.util.List;

import com.babeeta.appstore.dao.CatalogDao;
import com.babeeta.appstore.entity.Catalog;
import com.google.code.morphia.query.Query;

public class CatalogDaoImpl extends BasicDaoImpl implements CatalogDao {

	@Override
	public List<Catalog> findAll() {
		return datastore.find(Catalog.class).order("-weight, name").asList();
	}

	@Override
	public List<Catalog> findByParent(String name) {
		Catalog parent = datastore.get(Catalog.class, name);
		if (parent.getChildren() == null) {
			return Collections.emptyList();
		} else {
			Query<Catalog> q = datastore.createQuery(Catalog.class)
					.filter("name in", parent.getChildren().toArray())
					.order("-weight");
			return Collections.unmodifiableList(q.asList());
		}
	}

	@Override
	public List<Catalog> findByParentEnabled(String name) {
		Catalog parent = datastore.get(Catalog.class, name);
		if (parent.getChildren() == null) {
			return Collections.emptyList();
		} else {
			Query<Catalog> q = datastore.createQuery(Catalog.class)
					.field("enabled")
					.equal(true)
					.filter("name in", parent.getChildren().toArray())
					.order("-weight");
			return Collections.unmodifiableList(q.asList());
		}
	}

	@Override
	public Catalog findCatalog(String name) {
		Query<Catalog> q = datastore.find(Catalog.class, "_id", name);
		return q.get();
	}

	@Override
	public Catalog findCatalogEnabled(String name) {
		Query<Catalog> q = datastore.find(Catalog.class, "_id", name)
				.field("enabled").equal(true);
		return q.get();
	}

	@Override
	public List<Catalog> findCatalogsEnabled(List<String> names) {
		Query<Catalog> q = datastore.createQuery(Catalog.class)
				.field("enabled")
				.equal(true)
				.filter("name in", names.toArray()).order("-weight, name");
		return Collections.unmodifiableList(q.asList());
	}

	@Override
	public List<Catalog> findRootCatalog() {
		Query<Catalog> q = datastore.createQuery(Catalog.class).field("root")
				.equal(true).order("-weight, name");
		return q.asList();
	}

	@Override
	public List<Catalog> findRootCatalogEnabled() {
		Query<Catalog> q = datastore.createQuery(Catalog.class).field("root")
				.equal(true).field("enabled").equal(true)
				.order("-weight, name");
		return q.asList();
	}

	@Override
	public List<Catalog> findSecondaryCatalogsEnabled() {
		return datastore.createQuery(Catalog.class).field("root").equal(false)
				.field("enabled").equal(true)
				.order("-weight, name").asList();
	}

	@Override
	public void remove(String name) {
		datastore.delete(Catalog.class, name);
	}

	public void removeAll() {
		datastore.delete(datastore.createQuery(Catalog.class));
	}

	@Override
	public void save(Catalog catalog) {
		datastore.save(catalog);
	}
}