/**
 * 
 */
package com.babeeta.appstore.entity;

import java.util.List;

import com.babeeta.appstore.entity.ApplicationMarketDetail.Permission;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * EOE市场APK信息
 * @author xuehui.miao
 *
 */
@Entity(noClassnameStored = true)
public class EoeMarketAPK {
	@Id
	private String id;
	
	private String apkId;//APK包对应的存储编号
	
	private String packageName;
	
	private String url;
	
	private String versionCode;//APK版本号
	
	private String versionName;//APK版本名称
	
	private List<Permission> permissions;// 许可信息

	public String getId() {
		return id;
	}

	public EoeMarketAPK setId(String id) {
		this.id = id;
		return this;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getApkId() {
		return apkId;
	}

	public void setApkId(String apkId) {
		this.apkId = apkId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
}
