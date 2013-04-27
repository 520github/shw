package com.babeeta.appstore.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexes({
		// 用于分类下品牌
		@Index("catalog,groupAsciiName"),
		// 用于manager内部查询，用分类和品牌查询唯一记录
		@Index("catalog,group"),
		// 用于manager内部查询，用应用id查询brandItem记录
		@Index("apps.id") })
public class GroupItem {
	private List<App> apps;// 存放对应group对应catalog下的最新的前5个app
	private String catalog;
	private String group;
	private String groupAsciiName;// group的拼音
	private boolean hasMoreApp;// 标记，表示除去apps外还有更多的app
	@Id
	private String id;

	public List<App> getApps() {
		return apps;
	}

	public String getBrandAsciiName() {
		return groupAsciiName;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getGroup() {
		return group;
	}

	public String getId() {
		return id;
	}

	public boolean isHasMoreApp() {
		return hasMoreApp;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public void setBrandAsciiName(String brandAsciiName) {
		this.groupAsciiName = brandAsciiName;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setHasMoreApp(boolean hasMoreApp) {
		this.hasMoreApp = hasMoreApp;
	}

	public void setId(String id) {
		this.id = id;
	}

}
