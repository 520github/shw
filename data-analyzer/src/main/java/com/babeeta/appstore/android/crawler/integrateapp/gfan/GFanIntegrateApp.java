/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.gfan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.integrateapp.BasicIntegrateApp;
import com.babeeta.appstore.dao.GFanAPKDao;
import com.babeeta.appstore.dao.GFanApplicationDetailDao;
import com.babeeta.appstore.dao.GFanRedirectionDao;
import com.babeeta.appstore.entity.GFanAPK;
import com.babeeta.appstore.entity.GFanApplicationDetail;
import com.babeeta.appstore.entity.GFanRedirection;

/**
 * 对GFan市场APP进行整合
 * 
 * @author xuehui.miao
 * 
 */
public abstract class GFanIntegrateApp extends BasicIntegrateApp {
	protected static Logger logger = LoggerFactory
			.getLogger(GFanIntegrateApp.class);
	protected GFanAPKDao gFanAPKDao;
	protected GFanApplicationDetailDao gFanApplicationDetailDao;
	protected GFanRedirectionDao gFanRedirectionDao;

	protected GFanAPK apk;
	protected GFanApplicationDetail detail;
	protected GFanRedirection redirec;

	public GFanIntegrateApp() {
		market = AnalyzerConstant.MARKET_TYPE_GFAN;
		// gFanAPKDao = new GFanAPKDaoImpl();
		// gFanApplicationDetailDao = new GFanApplicationDetailDaoImpl();
		// gFanRedirectionDao = new GFanRedirectionDaoImpl();

	}

	/**
	 * GFan市场APP整合成功之后,移除临时表的记录
	 */
	private void removeGFanApp() {
		try {
			gFanAPKDao.removeByUrl(apk.getUrl());
			gFanApplicationDetailDao.removeByUrl(detail.getUrl());
			gFanRedirectionDao.removeByUrl(redirec.getUrl());
		} catch (Exception e) {
			logger.error("{}", e.getMessage(), e);
		}

	}

	@Override
	protected void destroy() {
		super.destroy();
		apk = null;
		detail = null;
		redirec = null;
		// this.gFanAPKDao = null;
		// this.gFanApplicationDetailDao = null;
		// this.gFanRedirectionDao = null;
	}

	protected GFanAPK getGFanAPKByUrl(String url) {
		GFanAPK obj = gFanAPKDao.findByUrl(url);
		if (obj == null) {
			obj = new GFanAPK();
		}
		return obj;
	}

	protected GFanApplicationDetail getGFanApplicationDetailByRedirectionUrl(
			String url) {
		GFanApplicationDetail obj = gFanApplicationDetailDao
				.findByRedirectionUrl(url);
		if (obj == null) {
			obj = new GFanApplicationDetail();
		}
		return obj;
	}

	protected GFanApplicationDetail getGFanApplicationDetailByUrl(String url) {
		GFanApplicationDetail obj = gFanApplicationDetailDao.findByUrl(url);
		if (obj == null) {
			obj = new GFanApplicationDetail();
		}
		return obj;
	}

	protected GFanRedirection getGFanRedirectionByApkUrl(String url) {
		GFanRedirection obj = gFanRedirectionDao.findByApkUrl(url);
		if (obj == null) {
			obj = new GFanRedirection();
		}
		return obj;
	}

	protected GFanRedirection getGFanRedirectionByUrl(String url) {
		GFanRedirection obj = gFanRedirectionDao.findByUrl(url);
		if (obj == null) {
			obj = new GFanRedirection();
		}
		return obj;
	}

	/**
	 * 整合APP
	 */
	protected void integrateOneAPP() {
		try {
			getApp().setId(apk.getPackageName());
			getApp().setNote(detail.getName());
			saveApp();
			removeGFanApp();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroy();
		}

	}

	/**
	 * 整合APP的市场详情信息
	 */
	protected void integrateOneMarketDetail() {
		getAppMarkteDetail().setApkId(apk.getApkId());
		getAppMarkteDetail().setMarket(AnalyzerConstant.urlGFanApk);
		getAppMarkteDetail().setName(detail.getName());
		getAppMarkteDetail().setUrl(apk.getUrl());
		getAppMarkteDetail().setWebUrl(detail.getUrl());
		getAppMarkteDetail().setVersion(detail.getVersion());
		getAppMarkteDetail().setRequirement(detail.getRequirement());
		getAppMarkteDetail().setOriginalDescription(
				detail.getOriginalDescription());
		getAppMarkteDetail().setSize(detail.getSize());
		getAppMarkteDetail().setPrice(detail.getPrice());
		getAppMarkteDetail().setLastUpdate(detail.getLastUpdate());
		getAppMarkteDetail().setWholeVersionScore(detail.getScore());
		getAppMarkteDetail().setPermissions(apk.getPermissions());
		getAppMarkteDetail().setLogo(detail.getLogo());
		getAppMarkteDetail().setScreenshotsForPhone(
				detail.getScreenshotsForPhone());
		getAppMarkteDetail()
				.setVersionCode(
						(apk.getVersionCode() == null || apk.getVersionCode() == "") ? "0"
								: apk.getVersionCode());
		getAppMarkteDetail()
				.setVersionName(
						(apk.getVersionName() == null || apk.getVersionName() == "") ? "0"
								: apk.getVersionName());
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

	@Autowired
	@Required
	public void setgFanAPKDao(GFanAPKDao gFanAPKDao) {
		this.gFanAPKDao = gFanAPKDao;
	}

	@Autowired
	@Required
	public void setgFanApplicationDetailDao(
			GFanApplicationDetailDao gFanApplicationDetailDao) {
		this.gFanApplicationDetailDao = gFanApplicationDetailDao;
	}

	@Autowired
	@Required
	public void setgFanRedirectionDao(GFanRedirectionDao gFanRedirectionDao) {
		this.gFanRedirectionDao = gFanRedirectionDao;
	}
}
