/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.integrateapp.eoe.EoeMarketAPKIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.eoe.EoeMarketApplicationDetailIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.eoe.EoeMarketRedirectionIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.gfan.GFanAPKIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.gfan.GFanApplicationDetailIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.gfan.GFanRedirectionIntegrateApp;
import com.babeeta.appstore.android.crawler.integrateapp.play.GooglePlayIntegrateApp;

/**
 * 所有市场的APP整合入口
 * @author xuehui.miao
 *
 */
@Service
public class PortalIntegrateApp implements IntegrateApp {
	private EoeMarketAPKIntegrateApp eoeMarketAPKIntegrateApp;
	private EoeMarketRedirectionIntegrateApp eoeMarketRedirectionIntegrateApp;
	private EoeMarketApplicationDetailIntegrateApp eoeMarketApplicationDetailIntegrateApp;
	private GFanAPKIntegrateApp gFanAPKIntegrateApp;
	private GFanRedirectionIntegrateApp gFanRedirectionIntegrateApp;
	private GFanApplicationDetailIntegrateApp gFanApplicationDetailIntegrateApp;
	private GooglePlayIntegrateApp googlePlayIntegrateApp;
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.crawler.integrateapp.IntegrateApp#integrateOneAPP(java.lang.String)
	 */
	@Override
	public void integrateOneAPP(String url) {
		IntegrateApp integrateApp = null;
		String type = AnalyzerConstant.getContentType(url);
		if(AnalyzerConstant.urlEoeApk.equalsIgnoreCase(type)) {
			integrateApp = eoeMarketAPKIntegrateApp;
			//integrateApp = new EoeMarketAPKIntegrateApp();
		}
		else if(AnalyzerConstant.urlEoeRedirection.equalsIgnoreCase(type)) {
			integrateApp = eoeMarketRedirectionIntegrateApp;
			//integrateApp = new EoeMarketRedirectionIntegrateApp();
		}
		else if(AnalyzerConstant.urlEoeApplicationDetail.equalsIgnoreCase(type)) {
			integrateApp = eoeMarketApplicationDetailIntegrateApp;
			//integrateApp = new EoeMarketApplicationDetailIntegrateApp();
		}
		else if(AnalyzerConstant.urlGFanApk.equalsIgnoreCase(type)) {
			integrateApp = gFanAPKIntegrateApp;
			//integrateApp = new GFanAPKIntegrateApp();
		}
		else if(AnalyzerConstant.urlGFanRedirection.equalsIgnoreCase(type)) {
			integrateApp = gFanRedirectionIntegrateApp;
			//integrateApp = new GFanRedirectionIntegrateApp();
		}
		else if(AnalyzerConstant.urlGFanApplicationDetail.equalsIgnoreCase(type)) {
			integrateApp = gFanApplicationDetailIntegrateApp;
			//integrateApp = new GFanApplicationDetailIntegrateApp();
		}
		else if(AnalyzerConstant.urlGooglePlay.equalsIgnoreCase(type)) {
			integrateApp = googlePlayIntegrateApp;
			//integrateApp = new GooglePlayIntegrateApp();
		}
		
		integrateApp.integrateOneAPP(url);
		integrateApp = null;
	}
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.crawler.integrateapp.IntegrateApp#integrateAllApp()
	 */
	@Override
	public void integrateAllApp() {
		
	}
	
	@Autowired
	public void setEoeMarketAPKIntegrateApp(
			EoeMarketAPKIntegrateApp eoeMarketAPKIntegrateApp) {
		this.eoeMarketAPKIntegrateApp = eoeMarketAPKIntegrateApp;
	}
	@Autowired
	public void setEoeMarketRedirectionIntegrateApp(
			EoeMarketRedirectionIntegrateApp eoeMarketRedirectionIntegrateApp) {
		this.eoeMarketRedirectionIntegrateApp = eoeMarketRedirectionIntegrateApp;
	}
	@Autowired
	public void setEoeMarketApplicationDetailIntegrateApp(
			EoeMarketApplicationDetailIntegrateApp eoeMarketApplicationDetailIntegrateApp) {
		this.eoeMarketApplicationDetailIntegrateApp = eoeMarketApplicationDetailIntegrateApp;
	}
	@Autowired
	public void setgFanAPKIntegrateApp(GFanAPKIntegrateApp gFanAPKIntegrateApp) {
		this.gFanAPKIntegrateApp = gFanAPKIntegrateApp;
	}
	@Autowired
	public void setgFanRedirectionIntegrateApp(
			GFanRedirectionIntegrateApp gFanRedirectionIntegrateApp) {
		this.gFanRedirectionIntegrateApp = gFanRedirectionIntegrateApp;
	}
	@Autowired
	public void setgFanApplicationDetailIntegrateApp(
			GFanApplicationDetailIntegrateApp gFanApplicationDetailIntegrateApp) {
		this.gFanApplicationDetailIntegrateApp = gFanApplicationDetailIntegrateApp;
	}
	@Autowired
	public void setGooglePlayIntegrateApp(
			GooglePlayIntegrateApp googlePlayIntegrateApp) {
		this.googlePlayIntegrateApp = googlePlayIntegrateApp;
	}

}
