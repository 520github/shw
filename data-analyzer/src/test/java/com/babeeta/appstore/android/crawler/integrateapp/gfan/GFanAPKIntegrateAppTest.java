/**
 * 
 */
package com.babeeta.appstore.android.crawler.integrateapp.gfan;

import org.junit.Test;

import com.babeeta.appstore.android.crawler.integrateapp.IntegrateAppTest;
import com.babeeta.appstore.android.crawler.integrateapp.PortalIntegrateApp;

/**
 * @author xuehui.miao
 *
 */
public class GFanAPKIntegrateAppTest extends IntegrateAppTest {
	@Test
	public void testEoeMarketAPKIntegrateApp() {
		try {
			portal = (PortalIntegrateApp)ctx.getBean("portalIntegrateApp");
			String url = "http://apk.gfan.com/Aspx/UserApp/DownLoad.aspx?src=apkpage&apk=GCZgWp1UAHIsXTRU6B0fA9zQnxihwlQfqxqzSJYY4UeCYEcZHwvoV7cg0/08ZsEn4nR4HV/Lxre/8Sv/ga9reMOECNCBmtEDU2AJVpta600=";
			logger.debug("{}",url);
			//this.portal.integrateOneAPP(url);
		} catch (Exception e) {
			logger.error("{}", e.getMessage(), e);
		}		
	}
}
