/**
 * 
 */
package com.babeeta.appstore.entity;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * EOE市场跳转APK信息
 * @author xuehui.miao
 *
 */
@Entity(noClassnameStored = true)
public class EoeMarketRedirection {
	@Id
	private String id;
	private String url;
	private String apkUrl;
	
	public String getId() {
		return id;
	}
	public EoeMarketRedirection setId(String id) {
		this.id = id;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
}
