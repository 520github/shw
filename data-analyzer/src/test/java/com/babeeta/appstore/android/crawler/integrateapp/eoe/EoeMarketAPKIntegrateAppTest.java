/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.eoe;

import org.junit.Test;
import com.babeeta.appstore.android.crawler.integrateapp.IntegrateAppTest;
import com.babeeta.appstore.android.crawler.integrateapp.PortalIntegrateApp;

/**
 * 根据EOE的APK解析事件触发APP整合的测试单元
 * @author xuehui.miao
 *
 */
public class EoeMarketAPKIntegrateAppTest extends IntegrateAppTest {
	@Test
	public void testEoeMarketAPKIntegrateApp() {
		portal = (PortalIntegrateApp)ctx.getBean("portalIntegrateApp");
		String url = "http://c11.eoemarket.com//upload/2010/0405/apps/5601/apks/11138/0e83060e-a552-0c56-9fbd-6f87743f8f22.apk";
		logger.debug("{}",url);
		//this.portal.integrateOneAPP(url);
	}
}
