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
public class GFanAPKDataAnalyzerTest extends A_Analyzer {
	
	public GFanAPKDataAnalyzerTest() {
	}
	
	@Test
	public void testGFanAPKDataAnalyzer() {
		try {
			logger.info("{}","start");
			analyzer = (DataAnalyzer)ctx.getBean("gFanDataAnalyzer");
			Context context = new Context();
			String url = "http://cdn1.down.apk.gfan.com/";
			context.setUrl(url);
			
			//analyzer.process(this.getInputStream(), context);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}
