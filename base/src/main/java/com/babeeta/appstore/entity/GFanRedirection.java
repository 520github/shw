/**
 * 
 */
package com.babeeta.appstore.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * GFan市场跳转APK信息
 * @author xuehui.miao
 *
 */
@Entity(noClassnameStored = true)
public class GFanRedirection {
	@Id
	private String id;
	private String url;
	private String apkUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public GFanRedirection setUrl(String url) {
		this.url = url;
		return this;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
}
