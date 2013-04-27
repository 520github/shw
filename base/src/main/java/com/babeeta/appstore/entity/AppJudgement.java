package com.babeeta.appstore.entity;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 打擂
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
public class AppJudgement {
	public static class Candidate {
		private String appId;
		private String logo;

		private String name;
		private int vote;// 这个app在这场擂台中的得票数量

		public String getAppId() {
			return appId;
		}

		public String getLogo() {
			return logo;
		}

		public String getName() {
			return name;
		}

		public int getVote() {
			return vote;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setVote(int vote) {
			this.vote = vote;
		}

	}

	private boolean available;// 上下架
	private Date begin;// 比赛开始时间
	private List<Candidate> candidateList;// 候选app的列表
	private Date date;// 创建时间
	private String desc;// 擂台说明
	private Date end;// 比赛结束时间
	@Id
	private String id;
	private Date lastModify;// 最后修改时间
	private String operator;// 操作者

	private String title;// 擂台标题

	@JsonSerialize(using = ChineseDateFormatter.class)
	public Date getBegin() {
		return begin;
	}

	public List<Candidate> getCandidateList() {
		return candidateList;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getDate() {
		return date;
	}

	public String getDesc() {
		return desc;
	}

	@JsonSerialize(using = ChineseDateFormatter.class)
	public Date getEnd() {
		return end;
	}

	public String getId() {
		return id;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public String getOperator() {
		return operator;
	}

	public String getTitle() {
		return title;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public void setCandidateList(List<Candidate> candidateList) {
		this.candidateList = candidateList;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
