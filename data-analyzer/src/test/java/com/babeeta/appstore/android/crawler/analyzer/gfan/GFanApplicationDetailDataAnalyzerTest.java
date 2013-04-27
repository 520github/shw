/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.gfan;

import org.junit.Test;

import com.babeeta.appstore.android.crawler.analyzer.A_Analyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;

/**
 * @author xuehui.miao
 *
 */
public class GFanApplicationDetailDataAnalyzerTest extends A_Analyzer {
	
	public GFanApplicationDetailDataAnalyzerTest() {
		
	}
	
	@Test
	public void testGFanAppDetailDataAnalyzer() {
		try {
			Context context = new Context();
			analyzer = (DataAnalyzer)ctx.getBean("gFanDataAnalyzer");
			String url = "http://apk.gfan.com/Product/App147020.html";
			context.setUrl(url);
			
			//analyzer.process(null, context);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
