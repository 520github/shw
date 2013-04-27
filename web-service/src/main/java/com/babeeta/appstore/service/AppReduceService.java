package com.babeeta.appstore.service;

import java.util.List;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Catalog;

public interface AppReduceService {

	/**
	 * 根据一个app列表，获取分页对象
	 * 
	 * @param apps
	 *            原始app列表
	 * @param total
	 *            总页数
	 * @param lastPage
	 *            是否是最后一页
	 * @param views
	 *            视图信息
	 * @return
	 */
	public Object getAppPageList(final List<App> apps, final long total,
			final boolean lastPage, final List<Catalog.TabView> views);

	/**
	 * 根据一个app，重组成一个对象
	 * 
	 * @param app
	 * @return
	 */
	public Object getDefaultApp(App app);

	public Object reduceApp(final App app, final boolean simple);
}
