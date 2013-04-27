/**
 * 
 */
package com.babeeta.appstore.job.eoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
//import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketDataAnalyzer;
import com.babeeta.appstore.job.Job;

/**
 * EOE市场的arc文件解析的Job
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketJob extends Job {
	public EoeMarketJob() {
		
	}
	
	@Autowired
	@Qualifier("eoeMarketDataAnalyzer")
	@Required
	public void setDataAnalyzer(DataAnalyzer dataAnalyzer) {
		this.dataAnalyzer = dataAnalyzer;
	}
}
