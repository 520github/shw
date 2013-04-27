package com.babeeta.appstore.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 广告
 * 
 * @author chongf
 *
 */
@Entity(noClassnameStored = true)
public class Advertisement {
	@Id
	private int id;//位置id
	private String pic;//广告图片url
	private String link;//广告链接
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	
}
