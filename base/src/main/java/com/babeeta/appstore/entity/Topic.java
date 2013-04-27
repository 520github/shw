package com.babeeta.appstore.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
/**
 * 专题
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
public class Topic {
	@Id
	private int id;
	private String link;//专题地址

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
