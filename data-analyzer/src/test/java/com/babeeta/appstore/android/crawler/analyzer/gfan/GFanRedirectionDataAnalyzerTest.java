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
public class GFanRedirectionDataAnalyzerTest extends A_Analyzer {
	
	public GFanRedirectionDataAnalyzerTest() {
	}
	
	@Test
	public void testGFanRedirectionDataAnalyzer() {
		try {
			analyzer = (DataAnalyzer)ctx.getBean("gFanDataAnalyzer");
			Context context = new Context();
			String url = "http://apk.gfan.com/Aspx/UserApp/DownLoad.aspx";
			context.setUrl(url);
			
			//analyzer.process(null, context);
		} catch (Exception e) {
			logger.error("{}", e.getMessage(), e.getCause());
		}
	}
}
