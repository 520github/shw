/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.play;

import org.junit.Test;
import com.babeeta.appstore.android.crawler.integrateapp.IntegrateAppTest;
import com.babeeta.appstore.android.crawler.integrateapp.PortalIntegrateApp;

/**
 * @author xuehui.miao
 *
 */
public class GooglePlayIntegrateAppTest extends IntegrateAppTest {
	@Test
	public void testEoeMarketAPKIntegrateApp() {
		portal = (PortalIntegrateApp)ctx.getBean("portalIntegrateApp");
		String url = "https://play.google.com/store/apps/details?id=com.rovio.angrybirdsspace.ads&feature=featured-apps#?t=W251bGwsMSwyLDIwMywiY29tLnJvdmlvLmFuZ3J5YmlyZHNzcGFjZS5hZHMiXQ..";
		logger.debug("{}",url);
		String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
		logger.debug("{}",path);
		//this.portal.integrateOneAPP(url);
	}
}
