package com.babeeta.appstore.dao.impl;

import java.util.List;

import com.babeeta.appstore.dao.GroupItemDao;
import com.babeeta.appstore.entity.GroupItem;
import com.google.code.morphia.query.Query;

public class GroupItemDaoImpl extends BasicDaoImpl implements GroupItemDao {

	@Override
	public void deleteByCatalogAndGroup(String catalog, String group) {
		this.datastore.delete(this.datastore.createQuery(GroupItem.class)
				.field("catalog").equal(catalog).field("group").equal(group));
	}

	@Override
	public void deleteById(String id) {
		this.datastore.delete(this.datastore.createQuery(GroupItem.class)
				.field("id").equal(id));
	}

	@Override
	public List<GroupItem> findByAppId(String appId) {
		return this.datastore.createQuery(GroupItem.class).field("apps.id")
				.equal(appId).asList();
	}

	@Override
	public List<GroupItem> findByCatalog(String catalog, int offset, int limit) {
		Query<GroupItem> query = this.datastore.createQuery(GroupItem.class)
				.field("catalog").equal(catalog)
				.order("groupAsciiName").offset(offset).limit(limit);
		return query.asList();
	}

	@Override
	public GroupItem findByCatalogAndGroup(String catalog, String group) {
		Query<GroupItem> query = this.datastore.createQuery(GroupItem.class)
				.field("catalog").equal(catalog).field("group").equal(group);
		return query.get();
	}

	@Override
	public void save(GroupItem groupItem) {
		this.datastore.save(groupItem);
	}

}
