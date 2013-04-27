/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.junit.Test;

import com.babeeta.appstore.android.crawler.analyzer.A_Analyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;

/**
 * @author xuehui.miao
 *
 */
public class EoeMarketRedirectionDataAnalyzerTest extends A_Analyzer {
	
	public EoeMarketRedirectionDataAnalyzerTest() {
		
	}
	
	@Test
	public void testEoeMarketRedirectionDataAnalyzer() {
		try {
			analyzer = (DataAnalyzer)ctx.getBean("eoeMarketDataAnalyzer");
			Context context = new Context();
			String url = "http://www.eoemarket.com/apps/10321";
			context.setUrl(url);
			
			//analyzer.process(null, context);
		} catch (Exception e) {
			logger.error("{}", e.getMessage(), e.getCause());
		}
	}
}
