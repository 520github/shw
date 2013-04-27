package com.babeeta.appstore.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerFeedback {
	private String apn;// 用户编写反馈时的网络环境 wifi or gprs
	private String content;// 反馈内容
	private Date createDate;// 用户点击发送的时间, 客户端的本地时间
	private Date date;// 创建时间(server)
	private String email;// 用户的email

	@Id
	private String id;

	private String refer;// 界面的 action url, 用户从这个界面跳转到了意见反馈界面

	private Date sendDate;// 消息发送的时间,客户端本地时间

	private String subject;// 用户在 UI 中选择,或填写的主题。可选

	private String token;// 用户的token

	public String getApn() {
		return apn;
	}

	public String getContent() {
		return content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getDate() {
		return date;
	}

	public String getEmail() {
		return email;
	}

	public String getId() {
		return id;
	}

	public String getRefer() {
		return refer;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public String getSubject() {
		return subject;
	}

	public String getToken() {
		return token;
	}

	public void setApn(String apn) {
		this.apn = apn;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
