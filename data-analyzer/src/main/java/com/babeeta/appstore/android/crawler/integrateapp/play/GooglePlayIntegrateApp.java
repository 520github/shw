/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.play;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.integrateapp.BasicIntegrateApp;
import com.babeeta.appstore.dao.GooglePlayDao;
import com.babeeta.appstore.entity.GooglePlay;

/**
 * 根据GooglePlay的应用详情解析事件触发APP整合
 * 
 * @author xuehui.miao
 * 
 */
@Service
public class GooglePlayIntegrateApp extends BasicIntegrateApp {
	private GooglePlay detail;
	private GooglePlayDao googlePlayDao;

	public GooglePlayIntegrateApp() {
		market = AnalyzerConstant.MARKET_TYPE_PLAY;
		// googlePlayDao = new GooglePlayDaoImpl();
	}

	private GooglePlay getGooglePlayDao(String url) {
		GooglePlay obj = googlePlayDao.findByUrl(url);
		if (obj == null) {
			obj = new GooglePlay();
		}
		return obj;

	}

	/**
	 * 判断条件是否满足
	 * 
	 * @param url
	 * @return
	 */
	protected boolean checkCondiction(String url) {
		boolean check = false;
		detail = getGooglePlayDao(url);
		if (StringUtils.isEmpty(detail.getUrl())) {
			return check;
		}

		check = true;

		return check;
	}

	@Override
	protected void destroy() {
		super.destroy();
		// this.googlePlayDao = null;
		detail = null;
	}

	protected void integrateOneAPP() {
		try {
			getApp().setId(detail.getPackageName());
			getApp().setNote(detail.getName());
			saveApp();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroy();
		}
	}

	protected void integrateOneMarketDetail() {
		getAppMarkteDetail().setName(detail.getName());
		getAppMarkteDetail().setApkId(detail.getPackageName());
		getAppMarkteDetail().setUrl(detail.getUrl());
		getAppMarkteDetail().setWebUrl(detail.getUrl());
		getAppMarkteDetail().setVersion(detail.getVersion());
		getAppMarkteDetail().setSize(detail.getSize());
		getAppMarkteDetail().setPrice(detail.getPrice());
		getAppMarkteDetail().setLastUpdate(detail.getLastUpdate());
		getAppMarkteDetail().setRequirement(detail.getRequirement());
		getAppMarkteDetail().setOriginalDescription(
				detail.getOriginalDescription());
		getAppMarkteDetail().setLogo(detail.getLogo());
		getAppMarkteDetail().setScreenshotsForPhone(
				detail.getScreenshotsForPhone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.android.crawler.integrateapp.IntegrateApp#
	 * integrateAllApp()
	 */
	@Override
	public void integrateAllApp() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.babeeta.appstore.android.crawler.integrateapp.IntegrateApp#
	 * integrateOneAPP(java.lang.String)
	 */
	@Override
	public void integrateOneAPP(String url) {
		if (!checkCondiction(url)) {
			return;
		}
		integrateOneMarketDetail();
		this.integrateOneAPP();
	}

	@Autowired
	@Required
	public void setGooglePlayDao(GooglePlayDao googlePlayDao) {
		this.googlePlayDao = googlePlayDao;
	}

}
