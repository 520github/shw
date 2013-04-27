package com.babeeta.appstore.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * 分类
 * 
 * @author chongf
 * 
 */
@Entity(noClassnameStored = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Catalog {
	public static enum AppViewType {
		groupedList, scrollList;
	}

	public static class TabView {
		private boolean defaultView;// 客户端默认开启的视图
		private String name;// 视图的名称,客户端会把它展示在”tab”按钮上。
		private AppViewType type;// 视图的类型,目前支持简单列表和分组列表。客户端不认识的类型应该忽略,并且不再展示这个视图
		private String url;// 视图数据来来源。当这个视图被选中后,客户端需要通过这个url 来获取数据

		public TabView() {
		}

		public TabView(String name, AppViewType type, String url,
				boolean defaultView) {
			this.name = name;
			this.type = type;
			this.url = url;
			this.defaultView = defaultView;
		};

		public String getName() {
			return name;
		}

		public AppViewType getType() {
			return type;
		}

		public String getUrl() {
			return url;
		}

		public boolean isDefaultView() {
			return defaultView;
		}

		public void setDefaultView(boolean defaultView) {
			this.defaultView = defaultView;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setType(AppViewType type) {
			this.type = type;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public static enum TargetView {
		/**
		 * 下一步进入app列表view
		 */
		applist,
		/**
		 * 下一步进入子分类view
		 */
		catalog;
	}

	// 子类列表
	private List<String> children;
	// 跳转目标（applist或catalog）
	private TargetView defaultView = TargetView.applist;
	// 说明(小类列举)
	private String description;
	// 小编推荐,只存放App的Id
	private List<String> editorOpinion;
	// 上架
	private boolean enabled;
	// 类别的图标
	private String icon;
	@Id
	private String name;

	// 是否为顶层分类
	private boolean root;

	// 自定义视图列表
	private List<TabView> view;

	// 权重
	private int weight;

	public List<String> getChildren() {
		return children;
	}

	public TargetView getDefaultView() {
		return defaultView;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getEditorOpinion() {
		return editorOpinion;
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public List<TabView> getView() {
		return view;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isRoot() {
		return root;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public void setDefaultView(TargetView defaultView) {
		this.defaultView = defaultView;
	}

	public Catalog setDescription(String description) {
		this.description = description;
		return this;
	}

	public Catalog setEditorOpinion(List<String> editorOpinion) {
		this.editorOpinion = editorOpinion;
		return this;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Catalog setName(String name) {
		this.name = name;
		return this;
	}

	public Catalog setRoot(boolean root) {
		this.root = root;
		return this;
	}

	public void setView(List<TabView> view) {
		this.view = view;
	}

	public Catalog setWeight(int weight) {
		this.weight = weight;
		return this;
	}

}
