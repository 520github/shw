/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.junit.Test;
import com.babeeta.appstore.android.crawler.analyzer.A_Analyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;

/**
 * EOE市场APK数据解析测试类
 * @author xuehui.miao
 *
 */
public class EoeMarketAPKDataAnalyzerTest extends A_Analyzer {
	
	public EoeMarketAPKDataAnalyzerTest() {
		//analyzer = new EoeMarketDataAnalyzer();
	}
	
	@Test
	public void testEoeMarketAPKDataAnalyzer() {
		try {
			logger.info("{}","start");
			analyzer = (DataAnalyzer)ctx.getBean("eoeMarketDataAnalyzer");
			if(analyzer == null) {
				logger.error("eoeMarketDataAnalyzer is null");
			}
			Context context = new Context();
			String url = "http://c11.eoemarket.com//upload/";
			context.setUrl(url);
			
			//analyzer.process(this.getInputStream(), context);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
}
