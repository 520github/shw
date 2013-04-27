/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.eoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.integrateapp.BasicIntegrateApp;
import com.babeeta.appstore.dao.EoeMarketAPKDao;
import com.babeeta.appstore.dao.EoeMarketApplicationDetailDao;
import com.babeeta.appstore.dao.EoeMarketRedirectionDao;
import com.babeeta.appstore.entity.EoeMarketAPK;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;
import com.babeeta.appstore.entity.EoeMarketRedirection;

/**
 * 对EOE市场APP进行整合
 * 
 * @author xuehui.miao
 * 
 */
public abstract class EoeMarketIntegrateApp extends BasicIntegrateApp {
	protected EoeMarketAPK apk;
	protected EoeMarketRedirection redirec;
	protected EoeMarketApplicationDetail detail;

	protected EoeMarketAPKDao eoeMarketAPKDao;
	protected EoeMarketRedirectionDao eoeMarketRedirectionDao;
	protected EoeMarketApplicationDetailDao eoeMarketApplicationDetailDao;

	public EoeMarketIntegrateApp() {
		market = AnalyzerConstant.MARKET_TYPE_EOE;
		// eoeMarketAPKDao = new EoeMarketAPKDaoImpl();
		// eoeMarketRedirectionDao = new EoeMarketRedirectionDaoImpl();
		// eoeMarketApplicationDetailDao = new
		// EoeMarketApplicationDetailDaoImpl();
	}

	/**
	 * EOE市场APP整合成功之后,移除临时表的记录
	 */
	private void removeEoeMarketApp() {
		eoeMarketAPKDao.removeByUrl(apk.getUrl());
		eoeMarketApplicationDetailDao.removeByUrl(detail.getUrl());
		eoeMarketRedirectionDao.removeByUrl(redirec.getUrl());
	}

	@Override
	protected void destroy() {
		super.destroy();
		apk = null;
		detail = null;
		redirec = null;
		// this.eoeMarketAPKDao = null;
		// this.eoeMarketApplicationDetailDao = null;
		// this.eoeMarketRedirectionDao = null;
	}

	protected EoeMarketAPK getEoeMarketAPKByUrl(String url) {
		EoeMarketAPK obj = eoeMarketAPKDao.findByUrl(url);
		if (obj == null) {
			obj = new EoeMarketAPK();
		}
		return obj;
	}

	protected EoeMarketApplicationDetail getEoeMarketApplicationDetailByRedirectionUrl(
			String url) {
		EoeMarketApplicationDetail obj = eoeMarketApplicationDetailDao
				.findByRedirectionUrl(url);
		if (obj == null) {
			obj = new EoeMarketApplicationDetail();
		}
		return obj;
	}

	protected EoeMarketApplicationDetail getEoeMarketApplicationDetailByUrl(
			String url) {
		EoeMarketApplicationDetail obj = eoeMarketApplicationDetailDao
				.findByUrl(url);
		if (obj == null) {
			obj = new EoeMarketApplicationDetail();
		}
		return obj;
	}

	protected EoeMarketRedirection getEoeMarketRedirectionByApkUrl(String url) {
		EoeMarketRedirection obj = eoeMarketRedirectionDao.findByApkUrl(url);
		if (obj == null) {
			obj = new EoeMarketRedirection();
		}
		return obj;
	}

	protected EoeMarketRedirection getEoeMarketRedirectionByUrl(String url) {
		EoeMarketRedirection obj = eoeMarketRedirectionDao.findByUrl(url);
		if (obj == null) {
			obj = new EoeMarketRedirection();
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
			removeEoeMarketApp();
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
	public void setEoeMarketAPKDao(EoeMarketAPKDao eoeMarketAPKDao) {
		this.eoeMarketAPKDao = eoeMarketAPKDao;
	}

	@Autowired
	@Required
	public void setEoeMarketApplicationDetailDao(
			EoeMarketApplicationDetailDao eoeMarketApplicationDetailDao) {
		this.eoeMarketApplicationDetailDao = eoeMarketApplicationDetailDao;
	}

	@Autowired
	@Required
	public void setEoeMarketRedirectionDao(
			EoeMarketRedirectionDao eoeMarketRedirectionDao) {
		this.eoeMarketRedirectionDao = eoeMarketRedirectionDao;
	}

}
