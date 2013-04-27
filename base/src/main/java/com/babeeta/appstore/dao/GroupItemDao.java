package com.babeeta.appstore.dao;

import java.util.List;

import com.babeeta.appstore.entity.GroupItem;

/**
 * 
 * @author chongf
 */
public interface GroupItemDao {

	public void deleteByCatalogAndGroup(String catalog, String Group);

	public void deleteById(String id);

	public List<GroupItem> findByAppId(String appId);

	public List<GroupItem> findByCatalog(String catalog, int offset, int limit);

	public GroupItem findByCatalogAndGroup(String catalog, String group);

	public void save(GroupItem groupItem);
}
