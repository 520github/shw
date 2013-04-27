/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.eoe;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 根据EOE的APK解析事件触发APP整合
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketAPKIntegrateApp extends EoeMarketIntegrateApp {
	protected static Logger logger =  LoggerFactory.getLogger(EoeMarketAPKIntegrateApp.class);
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.crawler.integrateapp.IntegrateApp#integrateOneAPP(java.lang.String)
	 */
	@Override
	public void integrateOneAPP(String url) {
		if(!this.checkCondiction(url)) {
			logger.info("{}","Integrated condition is not Enough......");
			return ;
		}
		//集成应用市场名称
		this.integrateOneMarketDetail();
		//集成APP
		this.integrateOneAPP();
	}
	
	/**
	 * 判断条件是否满足
	 * 
	 * @param url 
	 * 
	 * @return
	 */
	protected boolean checkCondiction(String url) {
		boolean check = false;
		apk = this.getEoeMarketAPKByUrl(url);
		String apkUrl = apk.getUrl();
		if(StringUtils.isEmpty(apkUrl))return check;
		
		redirec = this.getEoeMarketRedirectionByApkUrl(apkUrl);
		String redirectionUrl = redirec.getUrl();
		if(StringUtils.isEmpty(redirectionUrl))return check;
		
		detail = this.getEoeMarketApplicationDetailByRedirectionUrl(redirectionUrl);
		if(StringUtils.isEmpty(detail.getUrl()))return check;
		
		check = true;
		
		return check;
	}
	
}
