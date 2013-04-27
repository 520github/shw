/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.junit.Test;

import com.babeeta.appstore.android.crawler.analyzer.A_Analyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;

/**
 * EOE市场应用详情数据解析测试类
 * @author xuehui.miao
 *
 */
public class EoeApplicationDetailDataAnalyzerTest extends A_Analyzer {
	
	public EoeApplicationDetailDataAnalyzerTest() {
		
	}
	
	@Test
	public void testEoeAppDetailDataAnalyzer() {
		try {
			analyzer = (DataAnalyzer)ctx.getBean("eoeMarketDataAnalyzer");
			Context context = new Context();
			String url = "http://www.eoemarket.com/apps/10321";
			context.setUrl(url);
			
			analyzer.process(null, context);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}
