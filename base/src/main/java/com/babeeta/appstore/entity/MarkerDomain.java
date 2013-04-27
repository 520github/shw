package com.babeeta.appstore.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 标记域
 * 
 * 某种标记
 * 
 * @author chongf
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MarkerDomain {

	public static enum CounterMethod {
		/**
		 * 根据点击计数，在删除标记时不会改变计数器
		 */
		click,
		/**
		 * 根据设备计数，在删除标记时计数器减一
		 */
		device
	}

	private CounterMethod counterBy;// 标记域类型

	@Id
	private String id;

	public CounterMethod getCounterBy() {
		return counterBy;
	}

	public String getId() {
		return id;
	}

	public void setCounterBy(CounterMethod counterBy) {
		this.counterBy = counterBy;
	}

	public void setId(String id) {
		this.id = id;
	}

}
