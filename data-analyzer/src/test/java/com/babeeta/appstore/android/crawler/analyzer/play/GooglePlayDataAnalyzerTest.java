/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.play;

import org.junit.Test;

import com.babeeta.appstore.android.crawler.analyzer.A_Analyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;

/**
 * @author xuehui.miao
 *
 */
public class GooglePlayDataAnalyzerTest extends A_Analyzer {
	
	public GooglePlayDataAnalyzerTest() {
	}
	
	@Test
	public void testGooglePlayDataAnalyzer() {
		try {
			analyzer = (DataAnalyzer)ctx.getBean("googlePlayDataAnalyzer");
			Context context = new Context();
			String url = "https://play.google.com/store/apps/details?id=com.valterc.infinitegag&hl=es";
			context.setUrl(url);
			
			//analyzer.process(null, context);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
