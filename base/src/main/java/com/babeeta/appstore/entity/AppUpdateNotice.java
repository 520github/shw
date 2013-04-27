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
 * APP应用更新通知
 * 
 * @author xuehui.miao
 * 
 */
@JsonSerialize(include = Inclusion.NON_NULL)
@Entity(noClassnameStored = true)
public class AppUpdateNotice {
	@Id
	private String id;

	private String deviceId;// 客户端设备ID

	private String clientId;// 蝴蝶推送对应的客户端ID

	private Date noticeDate;// 更新通知日期

	public String getClientId() {
		return clientId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public String getId() {
		return id;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

}
