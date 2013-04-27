/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.eoe;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 根据EOE的应用详情解析事件触发APP整合
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketApplicationDetailIntegrateApp extends
		EoeMarketIntegrateApp {
	protected static Logger logger =  LoggerFactory.getLogger(EoeMarketApplicationDetailIntegrateApp.class);
	
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
	 * @return
	 */
	protected boolean checkCondiction(String url) {
		boolean check = false;
		
		detail = this.getEoeMarketApplicationDetailByUrl(url);
		String redirectionUrl = detail.getRedirectionUrl();
		if(StringUtils.isEmpty(redirectionUrl))return check;
		
		redirec = this.getEoeMarketRedirectionByUrl(redirectionUrl);
		String apkUrl = redirec.getApkUrl();
		if(StringUtils.isEmpty(apkUrl))return check;
		
		apk = this.getEoeMarketAPKByUrl(apkUrl);
		apkUrl = apk.getUrl();
		if(StringUtils.isEmpty(apkUrl))return check;

		check = true;
		
		return check;
	}
}
