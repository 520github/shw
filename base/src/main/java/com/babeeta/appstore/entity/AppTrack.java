/**
 * 
 */
package com.babeeta.appstore.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 应用日志跟踪
 * 
 * @author xuehui.miao
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = Inclusion.NON_NULL)
public class AppTrack {
	public static enum enumAppSource {
		/** 珊瑚湾 */
		coralBay,
		/** 其他 */
		other
	}

	public static enum enumOperateType {
		/** 安装 */
		install,
		/** 卸载 */
		uninstall,
	}

	@Id
	private String id;

	private String deviceId;

	private String packageName;// 应用包名

	private String versionCode;// 版本号

	private enumAppSource appSource;// 应用来源

	private enumOperateType type;// 操作类型

	private Date operateDate;// 操作日期

	public enumAppSource getAppSource() {
		return appSource;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getId() {
		return id;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public String getPackageName() {
		return packageName;
	}

	public enumOperateType getType() {
		return type;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setAppSource(enumAppSource appSource) {
		this.appSource = appSource;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setType(enumOperateType type) {
		this.type = type;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

}
