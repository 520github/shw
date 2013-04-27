package com.babeeta.appstore.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexes;

/**
 * 消息通知
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexes({ @Index("ownerId,read,status")
		, @Index("collapseKey,ownerId")
		, @Index("collapseKey, -_id") })
public class Notification implements Serializable {

	public static enum Status {
		deleted, // 已删除
		expired, // 已过期
		normal;// 常态
	}

	private static final long serialVersionUID = 4325326185564182019L;

	private String action;// 用户点击通知时发生的动作
	private String collapseKey;// 用于区别消息类型，["daliy best","brand subscription",$action]，用于覆盖旧消息
	private Date date;// 通知创建的时间
	@Id
	private String id;
	private String ownerId;// 消息的所有者 Auth Token（对应Device的deviceToken）
	// 如果这个属性带有日期,那么就表明这个通知在这个时间被标记为已读状态。如果为空则表明通知还没有被标记为“已读”
	private Date read;

	private Status status;// 消息的状态

	private String subject;// 通知的标题,展示在“今日更新”中

	public Notification() {
	}

	public String getAction() {
		return action;
	}

	public String getCollapseKey() {
		return collapseKey;
	}

	@JsonSerialize(using = ISODateFormatter.class)
	public Date getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getOwnerId() {
		return ownerId;
	}

	@JsonSerialize(using = ISODateFormatter.class)
	public Date getRead() {
		return read;
	}

	public Status getStatus() {
		return status;
	}

	public String getSubject() {
		return subject;
	}

	public Notification setAction(String action) {
		this.action = action;
		return this;
	}

	public Notification setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
		return this;
	}

	public Notification setDate(Date date) {
		this.date = date;
		return this;
	}

	public Notification setId(String id) {
		this.id = id;
		return this;
	}

	public Notification setOwnerId(String ownerId) {
		this.ownerId = ownerId;
		return this;
	}

	public Notification setRead(Date read) {
		this.read = read;
		return this;
	}

	public Notification setStatus(Status status) {
		this.status = status;
		return this;
	}

	public Notification setSubject(String subject) {
		this.subject = subject;
		return this;
	}

}
