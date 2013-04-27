package com.babeeta.appstore.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Indexes;

/**
 * 品牌订阅关系
 * 
 * @author papertiger
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexes({ @Index("brandId,token"),
		@Index("brandId,token,lastVisitDate"),
		@Index("token,lastVisitDate") })
public class SubscriptionRelation implements Serializable {

	private static final long serialVersionUID = -6177903767874818155L;

	// 已订阅品牌列表
	private String brandId;
	@Id
	ObjectId id;
	// 最后访问时间
	private Date lastVisitDate = new Date();
	// 系统自动分配的token
	@Indexed
	private String token;

	public String getBrandId() {
		return brandId;
	}

	public Date getLastVisitDate() {
		return lastVisitDate;
	}

	public String getToken() {
		return token;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public void setLastVisitDate(Date lastVisitDate) {
		this.lastVisitDate = lastVisitDate;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
