package com.babeeta.appstore.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.ApplicationMarketDetail;
import com.babeeta.appstore.entity.ApplicationMarketDetail.Score;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.MarkerCounter;
import com.babeeta.appstore.resource.JsonObject;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.MarkerService;

@Service("appReduceService")
public class AppReduceServiceImpl implements AppReduceService {

	private MarkerService markerService;

	private Object reduceApps(final List<App> apps, final long total,
			final boolean lastPage, final List<Catalog.TabView> views) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public boolean getLastPage() {
				return lastPage;
			}

			public List<Object> getResult() {
				List<Object> list = new ArrayList<Object>();
				for (final App app : apps) {
					list.add(reduceApp(app, true));
				}
				return list;
			}

			public long getTotal() {
				return total;
			}

			public List<Catalog.TabView> getViews() {
				return views;
			}
		};
		return o;
	}

	private Object reduceDetail(final App app) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public ApplicationMarketDetail.Developer getDeveloper() {
				return app.getDetail().getDeveloper();
			}

			public String getLanguage() {
				return app.getDetail().getLanguage();
			}

			public String getLastUpdate() {
				return app.getDetail().getLastUpdate();
			}

			public String getLogo() {
				return app.getDetail().getLogo();
			}

			public String getMarket() {
				return app.getDetail().getMarket();
			}

			public String getName() {
				return app.getDetail().getName();
			}

			public List<ApplicationMarketDetail.Permission> getPermissions() {
				return app.getDetail().getPermissions();
			}

			public String getPrice() {
				return app.getDetail().getPrice();
			}

			public String getPublisher() {
				return app.getDetail().getPublisher();
			}

			public String getRequirement() {
				return app.getDetail().getRequirement();
			}

			public Score getScore() {
				Score score = app.getDetail()
						.getWholeVersionScore();
				if (score == null) {
					score = new Score().setStar(0).setVote(0);
				}
				return score;
			}

			public List<String> getScreenshotsForPad() {
				return app.getDetail().getScreenshotsForPad();
			}

			public List<String> getScreenshotsForPhone() {
				return app.getDetail().getScreenshotsForPhone();
			}

			public String getSize() {
				return app.getDetail().getSize();
			}

			public String getUrl() {
				return app.getDetail().getUrl();
			}

			public String getVersion() {
				return app.getDetail().getVersion();
			}

			public String getVersionCode() {
				return app.getDetail().getVersionCode();
			}
		};
		return o;
	}

	private Object reduceSimpleDetail(final App app) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public ApplicationMarketDetail.Developer getDeveloper() {
				return app.getDetail().getDeveloper();
			}

			public String getLastUpdate() {
				return app.getDetail().getLastUpdate();
			}

			public String getLogo() {
				return app.getDetail().getLogo();
			}

			public String getMarket() {
				return app.getDetail().getMarket();
			}

			public String getName() {
				return app.getDetail().getName();
			}

			public Score getScore() {
				Score score = app.getDetail()
						.getWholeVersionScore();
				if (score == null) {
					score = new Score().setStar(0).setVote(0);
				}
				return score;
			}

			public String getSize() {
				return app.getDetail().getSize();
			}

			public String getVersion() {
				return app.getDetail().getVersion();
			}

			public String getVersionCode() {
				return app.getDetail().getVersionCode();
			}
		};
		return o;
	}

	@Override
	public Object getAppPageList(final List<App> apps, final long total,
			final boolean lastPage, final List<Catalog.TabView> views) {
		return reduceApps(apps, total, lastPage, views);
	}

	@Override
	public Object getDefaultApp(App app) {
		return reduceApp(app, false);
	}

	public Object reduceApp(final App app, final boolean simple) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			public List<String> getCatalog() {
				return app.getCatalog();
			}

			public Map<String, Long> getCounters() {
				List<MarkerCounter> mcs = markerService.getMarkerCounters(app
						.getId());
				final Map<String, Long> counters = new HashMap<String, Long>();
				for (MarkerCounter mc : mcs) {
					Map<String, Long> innerMap = mc.getCounter();
					Iterator<String> it = innerMap.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						counters.put(key, innerMap.get(key));
					}
				}
				return counters;
			};

			public String getDescription() {
				return app.getDescription();
			}

			public Object getDetail() {
				return simple ? reduceSimpleDetail(app) : reduceDetail(app);
			}

			public String getId() {
				return app.getId();
			}

			public String getIntroduction() {
				return app.getIntroduction();
			}

			public String getReview() {
				return app.getReview();
			}
		};
		return o;
	}

	@Autowired
	@Required
	public void setMarkerService(MarkerService markerService) {
		this.markerService = markerService;
	}
}
