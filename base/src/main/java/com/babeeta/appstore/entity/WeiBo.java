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
 * 存储微博用户信息
 * @author xuehui.miao
 *
 */
@Entity(noClassnameStored=true)
@JsonSerialize(include=Inclusion.NON_NULL)
public class WeiBo {
	/** 微博来源 */
	public static enum enumSource {
		/** 新浪微博 */
		sina
	}
	
	public static class Parameters {
		private String uid;//用户唯一标识

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}
	}
	@Id
	private String id;
	
	private String uid;//用户唯一标识
	
	private String token;//客户端设备标识
	
	private String accountNo;//微博账号
	
	private Date lastDate;//最新绑定日期
	
	private enumSource source;
	
	private String action;//动作
	
	private Date date;//日志记录日期
	
	private String authToken;//device的token
	
	private Parameters parameters;//日志属性内容
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public enumSource getSource() {
		return source;
	}

	public void setSource(enumSource source) {
		this.source = source;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
}
