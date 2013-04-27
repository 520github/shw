package com.babeeta.appstore.android;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.job.Job;
import com.babeeta.appstore.job.eoe.EoeMarketJob;
import com.babeeta.appstore.job.gfan.GFanJob;
import com.babeeta.appstore.job.play.GooglePlayJob;

@Service
public class DataAnalyzerService implements Daemon{
	private static final Logger logger = LoggerFactory.getLogger(DataAnalyzerService.class);
	private EoeMarketJob eoeMarketJob;
	private GFanJob gFanJob;
	private GooglePlayJob googlePlayJob;

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		String jobdir = arg0.getArguments()[1];
		ApplicationContext ctx = null;
		try {
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			logger.error("init applicationContext.xml throw exception:{}", e.getMessage(), e);
			return ;
		}
		try {
			DataAnalyzerService service = (DataAnalyzerService)ctx.getBean("dataAnalyzerService");
			service.init(jobdir);
		} catch (Exception e) {
			logger.error("init dataAnalyzerService object throw exception:{}", e.getMessage(), e);
		}
	}
	
	private void init(String jobdir) {
		if(jobdir == null)jobdir = "";
		logger.info("jobdir:{}", jobdir);
		Job job = null;
		if(jobdir.indexOf("eoe") > -1) {
			job = eoeMarketJob;
			//job = new EoeMarketJob();
		}
		else if(jobdir.indexOf("gfan") > -1) {
			job = gFanJob;
			//job = new GFanJob();
		}
		else if(jobdir.indexOf("google") > -1) {
			job = googlePlayJob;
			//job = new GooglePlayJob();
		}
		if(job == null) {
			logger.error("jobdir [{}] is invalidate.....",jobdir);
			return ;
		}
		job.setArchiveDirectory(jobdir);
		job.start();
	}

	@Override
	public void start() throws Exception {
		
	}

	@Override
	public void stop() throws Exception {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Autowired
	public void setEoeMarketJob(EoeMarketJob eoeMarketJob) {
		this.eoeMarketJob = eoeMarketJob;
	}
	@Autowired
	public void setgFanJob(GFanJob gFanJob) {
		this.gFanJob = gFanJob;
	}
	@Autowired
	public void setGooglePlayJob(GooglePlayJob googlePlayJob) {
		this.googlePlayJob = googlePlayJob;
	}

//	public static void main(String[] args) {
//		try {
//			DataAnalyzerService obj = new DataAnalyzerService();
//			obj.init(args[0]);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
