package com.babeeta.appstore.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Indexes;
import com.google.code.morphia.utils.IndexDirection;

/**
 * 应用
 * 
 * @author chongf
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Indexes({
		// 用于摇摇乐，随机选择App
		@Index("status, random"),
		// 用于分类的应用列表
		@Index("status, catalog, -lastModified"),
		// 用于最新发布排行榜
		@Index("status,escapeRank, -detail.lastUpdate"),
		// 用于最热的排行榜
		@Index("status,escapeRank, -detail.wholeVersionScore.vote"),
		// 用于分类下最新免费
		@Index("status, catalog, detail.price, -lastModified"),
		// 用于分组查询
		@Index("status, catalog, group, -lastModified"),
		// 用于精品推荐
		@Index("status, best, -bestLastModified") })
public class App implements Serializable {

	/**
	 * 应用的状态
	 * 
	 * @author leon
	 * 
	 */
	public static enum AppStatus {
		/**
		 * 等待审核
		 */
		awaiting,
		/**
		 * 以删除
		 */
		deleted,
		/**
		 * 下架
		 */
		disabled,
		/**
		 * 已发布
		 */
		published,
		/**
		 * 没有通过审核（驳回）
		 */
		refused,
		/**
		 * 未处理， 新发现cur
		 */
		unprocessed
	}

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 2395610549057751283L;

	private Map<String, ApplicationMarketDetail> applicationMarketDetails;

	// 用于排序的ascii码名称。汉字都被转换成了拼音
	private String asciiName;

	// 审核员的用户名
	private String audior;

	// 机器的分类（小类）
	private String autoCatalog;

	@CrawlerProperty
	private String availability;// 适用信息（如：此应用软件对 iPhone 和 iPad 都适用。）

	// 徽章
	private String badge;
	private boolean best;// 精品
	private Date bestLastModified;// 精品推荐时间（用于精品列表的排序）

	// 品牌
	private List<String> brand;
	// 人工的分类（小类）
	private List<String> catalog;
	// 简介（人工）
	private String description;

	@Embedded
	private ApplicationMarketDetail detail;

	// 编辑的用户名（新App处理时)
	@Indexed
	private String editor;
	private boolean escapeRank;// 排除排行榜（进入排行榜）

	private String group;// 分组信息
	private String groupAsciiName;// 分组信息拼音
	// 来自apple商店的id
	@Id
	private String id;// 存放packageName
	// 一句话描述
	private String introduction;
	private List<String> keywords;// 对app的几个关键词描述
	@Indexed(value = IndexDirection.DESC)
	private Date lastModified;// 最后更改时间
	// 备注
	private String note;

	// 操作者的用户名（例如上下架操作）
	private String operator;
	// {0,1}之间的随机数, 用于随便逛逛
	private double random;

	// 推荐理由
	private String review;

	// app状态
	private AppStatus status;

	/**
	 * 使用爬虫产生的App对象更新这个对象
	 * 
	 * @param newUpdate
	 */
	public App crawlerUpdate(App newUpdate) {
		for (Method method : App.class.getMethods()) {
			try {
				if (method.getName().length() > 3
						&& method.getName().startsWith("get")
						&& method.getParameterTypes().length == 0) {
					String fieldName = Character.toLowerCase(method.getName()
							.charAt(3)) + method.getName().substring(4);
					logger.debug("{}", fieldName);
					Field field = App.class.getDeclaredField(fieldName);
					Method setter = App.class.getMethod(
							method.getName().replace("get", "set"),
							method.getReturnType());
					if (setter != null
							&& field.getAnnotation(CrawlerProperty.class) != null) {
						logger.debug("{} copy.", fieldName);
						setter.invoke(this,
								method.invoke(newUpdate, new Object[] {}));
					}
				}
			} catch (Exception e) {
				logger.debug("{} - {}", method.getName(), e.toString());
			}
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof App)) {
			return super.equals(obj);
		} else {
			App target = (App) obj;
			if (this.id == null || target.id == null) {
				return super.equals(obj);
			} else {
				return this.id.equals(target.id);
			}
		}
	}

	public String getAsciiName() {
		return asciiName;
	}

	public String getAudior() {
		return audior;
	}

	public String getAutoCatalog() {
		return autoCatalog;
	}

	public String getAvailability() {
		return availability;
	}

	public String getBadge() {
		return badge;
	}

	public Date getBestLastModified() {
		return bestLastModified;
	}

	public List<String> getBrand() {
		return brand;
	}

	public List<String> getCatalog() {
		return catalog;
	}

	public String getDescription() {
		return description;
	}

	public ApplicationMarketDetail getDetail() {
		return detail;
	}

	public String getEditor() {
		return editor;
	}

	public String getGroup() {
		return group;
	}

	public String getGroupAsciiName() {
		return groupAsciiName;
	}

	public String getId() {
		return id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public String getNote() {
		return note;
	}

	public String getOperator() {
		return operator;
	}

	public double getRandom() {
		return random;
	}

	public String getReview() {
		return review;
	}

	public AppStatus getStatus() {
		return status;
	}

	@Override
	public int hashCode() {
		if (this.id != null && this.id.trim().length() > 0) {
			return this.id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public boolean isBest() {
		return best;
	}

	public boolean isEscapeRank() {
		return escapeRank;
	}

	public App setAsciiName(String asciiName) {
		this.asciiName = asciiName;
		return this;
	}

	public App setAudior(String audior) {
		this.audior = audior;
		return this;

	}

	public App setAutoCatalog(String autoCatalog) {
		this.autoCatalog = autoCatalog;
		return this;
	}

	public App setAvailability(String availability) {
		this.availability = availability;
		return this;
	}

	public App setBadge(String badge) {
		this.badge = badge;
		return this;
	}

	public void setBest(boolean best) {
		this.best = best;
	}

	public void setBestLastModified(Date bestLastModified) {
		this.bestLastModified = bestLastModified;
	}

	public App setBrand(List<String> brand) {
		this.brand = brand;
		return this;
	}

	public App setCatalog(List<String> catalog) {
		this.catalog = catalog;
		return this;
	}

	public App setDescription(String description) {
		this.description = description;
		return this;
	}

	public void setDetail(ApplicationMarketDetail detail) {
		this.detail = detail;
	}

	public App setEditor(String editor) {
		this.editor = editor;
		return this;
	}

	public void setEscapeRank(boolean escapeRank) {
		this.escapeRank = escapeRank;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setGroupAsciiName(String groupAsciiName) {
		this.groupAsciiName = groupAsciiName;
	}

	public App setId(String id) {
		this.id = id;
		return this;
	}

	public App setIntroduction(String introduction) {
		this.introduction = introduction;
		return this;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public App setNote(String note) {
		this.note = note;
		return this;
	}

	public App setOperator(String operator) {
		this.operator = operator;
		return this;
	}

	public App setRandom(double random) {
		this.random = random;
		return this;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public App setStatus(AppStatus status) {
		this.status = status;
		return this;
	}

	public void setApplicationMarketDetails(Map<String, ApplicationMarketDetail> applicationMarketDetails) {
		this.applicationMarketDetails = applicationMarketDetails;
	}

	public Map<String, ApplicationMarketDetail> getApplicationMarketDetails() {
		return applicationMarketDetails;
	}
}