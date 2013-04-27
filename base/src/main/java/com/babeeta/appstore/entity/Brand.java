package com.babeeta.appstore.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

/**
 * 品牌
 * 
 * @author leon
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Brand implements Serializable {
	private static final long serialVersionUID = -1013959027040094567L;
	// 用于排序的ascii码名称。汉字都被转换成了拼音
	@Indexed
	private String asciiName;
	private Map<String, Long> counter;// 计数器
	private boolean enabled;
	private String icon;
	@Id
	private String id;
	private Date lastModified;// 最后更新时间（人工的，点击订阅更新按钮时更换此值）

	@Indexed
	private String name;

	private int weight;// 排序权重

	public String getAsciiName() {
		return asciiName;
	}

	public Map<String, Long> getCounter() {
		return counter;
	}

	public String getIcon() {
		return icon;
	}

	public String getId() {
		return id;
	}

	@JsonSerialize(using = DateFormatter.class)
	public Date getLastModified() {
		return lastModified;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Brand setAsciiName(String asciiName) {
		this.asciiName = asciiName;
		return this;
	}

	public void setCounter(Map<String, Long> counter) {
		this.counter = counter;
	}

	public Brand setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public Brand setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public Brand setId(String id) {
		this.id = id;
		return this;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Brand setName(String name) {
		this.name = name;
		return this;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
