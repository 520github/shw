package com.babeeta.appstore.entity;

import java.util.Date;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

/**
 * 打擂结果统计
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
@Indexes({ @Index("appJudgementId,authToken") })
public class AppJudgementVote {
	private String appJudgementId;// 擂台的id
	private String authToken;// 设备的authToken
	private Date date;// 投票的日期时间
	@Id
	private String id;
	private String value;// 投票的值（app id）

	public String getAppJudgementId() {
		return appJudgementId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public Date getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setAppJudgementId(String appJudgementId) {
		this.appJudgementId = appJudgementId;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
