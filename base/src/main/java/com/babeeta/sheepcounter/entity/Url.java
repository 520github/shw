package com.babeeta.sheepcounter.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 资源检测链接
 * 
 * @author chongf
 * 
 */
@Entity(value = "url", noClassnameStored = true)
public class Url {
	@Id
	private String id;
	private String name;
	private String url;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
