package com.babeeta.appstore.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;

public class ApplicationMarketDetail {

	public static class Developer {
		private String email;
		private String name;
		private String website;

		public String getEmail() {
			return email;
		}

		public String getName() {
			return name;
		}

		public String getWebsite() {
			return website;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setWebsite(String website) {
			this.website = website;
		}
	}

	public static class Permission {
		private String id;
		private String text;

		public String getId() {
			return id;
		}

		public String getText() {
			return text;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * 评分
	 * 
	 * @author leon
	 * 
	 */
	public static class Score {
		// 星级
		private float star;
		// 评分数量
		private int vote;

		public float getStar() {
			return star;
		}

		public int getVote() {
			return vote;
		}

		public Score setStar(float star) {
			this.star = star;
			return this;
		}

		public Score setVote(int vote) {
			this.vote = vote;
			return this;
		}

	}

	@CrawlerProperty
	private String apkId;
	@CrawlerProperty
	private String change;// 版本新功能(改变)
	@Embedded
	@CrawlerProperty
	private Score currentVersionScore;// 当前版本评分
	private Developer developer;// 开发者

	@CrawlerProperty
	private String genre;// 在apple商店的类别
	@Id
	@CrawlerProperty
	private String id;
	@CrawlerProperty
	private String language;// 适用语言
	@CrawlerProperty
	private String lastUpdate;// 更新时间(App在apple商店上的最后更新时间)
	@CrawlerProperty
	private String logo;
	private String market;
	@CrawlerProperty
	private String name;
	@CrawlerProperty
	private String originalDescription;// 内容提要
	private List<Permission> permissions;// 许可信息
	@CrawlerProperty
	private String price;// 价格信息
	@CrawlerProperty
	private String publisher;// 出版商
	@CrawlerProperty
	private String requirement;// 系统要求
	@CrawlerProperty
	private List<String> screenshotsForPad = new ArrayList<String>();// Ipad截图
	@CrawlerProperty
	private List<String> screenshotsForPhone = new ArrayList<String>();// IPhone截图
	@CrawlerProperty
	private String size;// app文件大小

	private String url;

	private String webUrl;

	private String version;
	
	private String versionCode;//APK版本号
	
	private String versionName;//APK版本名称

	@Embedded
	@CrawlerProperty
	private Score wholeVersionScore; // 总评分

	public String getApkId() {
		return apkId;
	}

	public String getChange() {
		return change;
	}

	public Score getCurrentVersionScore() {
		return currentVersionScore;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public String getGenre() {
		return genre;
	}

	public String getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public String getLogo() {
		return logo;
	}

	public String getMarket() {
		return market;
	}

	public String getName() {
		return name;
	}

	public String getOriginalDescription() {
		return originalDescription;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public String getPrice() {
		return price;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getRequirement() {
		return requirement;
	}

	public List<String> getScreenshotsForPad() {
		return screenshotsForPad;
	}

	public List<String> getScreenshotsForPhone() {
		return screenshotsForPhone;
	}

	public String getSize() {
		return size;
	}

	public String getUrl() {
		return url;
	}

	public String getVersion() {
		return version;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public Score getWholeVersionScore() {
		return wholeVersionScore;
	}

	public void setApkId(String apkId) {
		this.apkId = apkId;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public void setCurrentVersionScore(Score currentVersionScore) {
		this.currentVersionScore = currentVersionScore;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOriginalDescription(String originalDescription) {
		this.originalDescription = originalDescription;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public void setScreenshotsForPad(List<String> screenshotsForPad) {
		this.screenshotsForPad = screenshotsForPad;
	}

	public void setScreenshotsForPhone(List<String> screenshotsForPhone) {
		this.screenshotsForPhone = screenshotsForPhone;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public void setWholeVersionScore(Score wholeVersionScore) {
		this.wholeVersionScore = wholeVersionScore;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
}
