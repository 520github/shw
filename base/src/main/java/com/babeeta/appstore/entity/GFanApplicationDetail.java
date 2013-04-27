/**
 * 
 */
package com.babeeta.appstore.entity;

import java.util.ArrayList;
import java.util.List;

import com.babeeta.appstore.entity.ApplicationMarketDetail.Score;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * GFan市场应用详情信息
 * @author xuehui.miao
 *
 */
@Entity(noClassnameStored = true)
public class GFanApplicationDetail {
	@Id
	private String id;
	private String name;
	private String url;
	private String redirectionUrl;
	private String requirement;// 系统要求
	private String price;// 价格信息
	private String lastUpdate;// 更新时间
	private String size;// app文件大小
	private String version;// 版本号
	private String originalDescription;// 内容提要
	private String publisher;// 出版商
	private Score score;//评分信息
	private String logo;
	private List<String> screenshotsForPad = new ArrayList<String>();//Pad截图
	private List<String> screenshotsForPhone = new ArrayList<String>();// Phone截图
	
	public String getId() {
		return id;
	}
	public GFanApplicationDetail setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRedirectionUrl() {
		return redirectionUrl;
	}
	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOriginalDescription() {
		return originalDescription;
	}
	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public Score getScore() {
		if(score == null)score = new Score();
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<String> getScreenshotsForPad() {
		return screenshotsForPad;
	}
	public void setScreenshotsForPad(List<String> screenshotsForPad) {
		this.screenshotsForPad = screenshotsForPad;
	}
	public List<String> getScreenshotsForPhone() {
		return screenshotsForPhone;
	}
	public void setScreenshotsForPhone(List<String> screenshotsForPhone) {
		this.screenshotsForPhone = screenshotsForPhone;
	}
	
	
}
