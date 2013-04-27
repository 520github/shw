/**
 * 
 */
package com.babeeta.appstore.job.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
//import com.babeeta.appstore.android.crawler.analyzer.play.GooglePlayDataAnalyzer;
import com.babeeta.appstore.job.Job;

/**
 * GooglePlay市场的arc文件解析的Job
 * @author xuehui.miao
 *
 */
@Service
public class GooglePlayJob extends Job {
	public GooglePlayJob() {
	}
	
	@Autowired
	@Qualifier("googlePlayDataAnalyzer")
	@Required
	public void setDataAnalyzer(DataAnalyzer dataAnalyzer) {
		this.dataAnalyzer = dataAnalyzer;
	}
}
