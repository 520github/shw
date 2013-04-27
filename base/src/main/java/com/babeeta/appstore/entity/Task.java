package com.babeeta.appstore.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Task {
	public enum TaskStatus {
		create,
		processing,
		done
	}

	public enum TaskType {
		index_json,
		brand_notice,
		best_app
	}

	@Id
	private String id;
	private TaskStatus status;
	private TaskType type;
	private Date createTime;
	private Date executeTime;
	private Date beginTime;

	private Date endTime;

	@JsonSerialize(using = DateFormatter.class)
	public Date getBeginTime() {
		return beginTime;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getCreateTime() {
		return createTime;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getEndTime() {
		return endTime;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getExecuteTime() {
		return executeTime;
	}

	public String getId() {
		return id;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public TaskType getType() {
		return type;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public void setType(TaskType type) {
		this.type = type;
	}

}
