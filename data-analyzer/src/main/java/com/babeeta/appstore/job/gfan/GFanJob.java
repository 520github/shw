/**
 * 
 */
package com.babeeta.appstore.job.gfan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
//import com.babeeta.appstore.android.crawler.analyzer.gfan.GFanDataAnalyzer;
import com.babeeta.appstore.job.Job;

/**
 * GFan市场的arc文件解析的Job
 * @author xuehui.miao
 *
 */
@Service("gFanJob")
public class GFanJob extends Job {
	public GFanJob() {
		
	}
	
	@Autowired
	@Qualifier("gFanDataAnalyzer")
	@Required
	public void setDataAnalyzer(DataAnalyzer dataAnalyzer) {
		this.dataAnalyzer = dataAnalyzer;
	}
}
