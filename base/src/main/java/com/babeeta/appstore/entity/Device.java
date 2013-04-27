package com.babeeta.appstore.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.babeeta.appstore.entity.AppTrack.enumAppSource;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 设备
 * 
 * @author papertiger
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Device implements Serializable {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class InstallApp {
		private String packageName;
		private String versionCode;
		private Date date;
		private enumAppSource appSource;// 应用来源

		public enumAppSource getAppSource() {
			return appSource;
		}

		public Date getDate() {
			return date;
		}

		public String getPackageName() {
			return packageName;
		}

		public String getVersionCode() {
			return versionCode;
		}

		public void setAppSource(enumAppSource appSource) {
			this.appSource = appSource;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public void setVersionCode(String versionCode) {
			this.versionCode = versionCode;
		}

	}

	private static final long serialVersionUID = 5670302168823376759L;

	private String clientVersion;// 客户端版本号

	private Date createDate;// 创建时间

	private String androidId;

	private int apiLevel;

	private boolean pushAlert;

	private String clientId;

	@Id
	private String id;

	private String mac;// mac 地址

	private String os;// 操作系统名称
	private String osVersion;// 操作系统版本号

	private String imei;

	private String token;// 自动分配的token

	private List<InstallApp> apps;

	public String getAndroidId() {
		return androidId;
	}

	public int getApiLevel() {
		return apiLevel;
	}

	public List<InstallApp> getApps() {
		return apps;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public String getImei() {
		return imei;
	}

	public String getMac() {
		return mac;
	}

	public String getOs() {
		return os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getToken() {
		return token;
	}

	public boolean isPushAlert() {
		return pushAlert;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public void setApiLevel(int apiLevel) {
		this.apiLevel = apiLevel;
	}

	public void setApps(List<InstallApp> apps) {
		this.apps = apps;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Device setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
		return this;
	}

	public Device setCreateDate(Date createDate) {
		this.createDate = createDate;
		return this;
	}

	public Device setId(String id) {
		this.id = id;
		return this;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Device setMac(String mac) {
		this.mac = mac;
		return this;
	}

	public Device setOs(String os) {
		this.os = os;
		return this;
	}

	public Device setOsVersion(String osVersion) {
		this.osVersion = osVersion;
		return this;
	}

	public void setPushAlert(boolean pushAlert) {
		this.pushAlert = pushAlert;
	}

	public Device setToken(String token) {
		this.token = token;
		return this;
	}
}
