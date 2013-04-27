package com.babeeta.appstore.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;

@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class BestApp {
	@Id
	private String id;
	@Reference
	private App app;
	private Date createTime;

	public App getApp() {
		return app;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getId() {
		return id;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

}
