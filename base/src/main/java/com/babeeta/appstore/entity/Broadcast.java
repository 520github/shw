package com.babeeta.appstore.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 广播的通知
 * 
 * @author leon
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Broadcast {

	public static enum Status {
		/**
		 * 召回完成
		 */
		Callbacked("召回完成"),
		/**
		 * 正在召回
		 */
		Callbacking("正在召回"),
		/**
		 * 召回中断
		 */
		CallbackInterrupted("召回中断"),
		/**
		 * 召回排队
		 */
		CallbackPending("召回排队"),

		/**
		 * 发送排队
		 */
		InEden("初始化"),
		/**
		 * 正在发送
		 */
		Sending("正在发送"),
		/**
		 * 发送中断
		 */
		SendInterrupted("发送中断"),

		SendPending("发送排队"),
		/**
		 * 发送完成
		 */
		Sent("发送完成");

		private final Set<String> availableCommands = new HashSet<String>();
		private final String statusName;

		Status(String statusName) {
			this.statusName = statusName;
		}

		Set<String> getAvailableCommandSet() {
			return availableCommands;
		}

		public Set<String> getAvailableCommands() {
			return Collections.unmodifiableSet(availableCommands);
		}

		public String getStatusName() {
			return statusName;
		}

	}

	// 查看推送内容后的动作。
	private String action;
	// 召回计数器
	private int callbacked;
	private Date callbackFinishedDate;
	// 创建日期
	private Date date;
	@Id
	private String id;
	// 最后处理的一个召回Notification的Token
	private String lastCallbackToken;
	// 最后处理的一个发送的Device的Token
	private String lastSendingToken;
	// 操作员
	private String operator;
	// 发送结束日期
	private Date sendFinishedDate;
	// 发送计数器
	private int sent;
	// 状态
	private Status status;
	// 推送的主题
	private String subject;
	// 将要召回的消息数量
	private long toBeCallbacked;
	// 将要发送的数量
	private long toBeSent;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Broadcast) {
			Broadcast right = (Broadcast) obj;
			if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(right.id)) {
				return id.equals(right.id);
			} else {
				return super.equals(obj);
			}
		} else {
			return super.equals(obj);
		}
	}

	public String getAction() {
		return action;
	}

	public int getCallbacked() {
		return callbacked;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getCallbackFinishedDate() {
		return callbackFinishedDate;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getDate() {
		return date;
	}

	public String getId() {
		return id;
	}

	public String getLastCallbackToken() {
		return lastCallbackToken;
	}

	public String getLastSendingToken() {
		return lastSendingToken;
	}

	public String getOperator() {
		return operator;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getSendFinishedDate() {
		return sendFinishedDate;
	}

	public int getSent() {
		return sent;
	}

	@JsonSerialize(using = BroadcastStatusSerializer.class)
	public Status getStatus() {
		return status;
	}

	public String getSubject() {
		return subject;
	}

	public long getToBeCallbacked() {
		return toBeCallbacked;
	}

	public long getToBeSent() {
		return toBeSent;
	}

	@Override
	public int hashCode() {
		if (StringUtils.isNotBlank(id)) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public Broadcast setAction(String action) {
		this.action = action;
		return this;
	}

	public Broadcast setCallbacked(int callbacked) {
		this.callbacked = callbacked;
		return this;
	}

	public Broadcast setCallbackFinishedDate(Date callbackFinishedDate) {
		this.callbackFinishedDate = callbackFinishedDate;
		return this;
	}

	public Broadcast setDate(Date date) {
		this.date = date;
		return this;
	}

	public Broadcast setId(String id) {
		this.id = id;
		return this;
	}

	public Broadcast setLastCallbackToken(String lastCallbackToken) {
		this.lastCallbackToken = lastCallbackToken;
		return this;
	}

	public Broadcast setLastSendingToken(String lastSendingToken) {
		this.lastSendingToken = lastSendingToken;
		return this;
	}

	public Broadcast setOperator(String operator) {
		this.operator = operator;
		return this;
	}

	public Broadcast setSendFinishedDate(Date sendFinishedDate) {
		this.sendFinishedDate = sendFinishedDate;
		return this;
	}

	public Broadcast setSent(int sent) {
		this.sent = sent;
		return this;
	}

	public Broadcast setStatus(Status status) {
		this.status = status;
		return this;
	}

	public Broadcast setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	public Broadcast setToBeCallbacked(long toBeCallbacked) {
		this.toBeCallbacked = toBeCallbacked;
		return this;
	}

	public Broadcast setToBeSent(long toBeSent) {
		this.toBeSent = toBeSent;
		return this;
	}

}
