package com.babeeta.appstore.android.crawler.analyzer.gfan;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.apk.ApkAaptBadgingAnalyze;
import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
import com.babeeta.appstore.android.file.BufferedReaderReadFile;
import com.babeeta.appstore.android.file.ReadFile;
import com.babeeta.appstore.android.process.CmdProcess;
import com.babeeta.appstore.android.process.MyProcess;
import com.babeeta.appstore.dao.GFanAPKDao;
import com.babeeta.appstore.dao.GFanApplicationDetailDao;
import com.babeeta.appstore.dao.GFanRedirectionDao;
import com.babeeta.appstore.dao.impl.GFanAPKDaoImpl;
import com.babeeta.appstore.dao.impl.GFanRedirectionDaoImpl;
import com.babeeta.appstore.dao.impl.GFanApplicationDetailDaoImpl;
import com.babeeta.appstore.entity.GFanAPK;
import com.babeeta.appstore.entity.GFanApplicationDetail;
import com.babeeta.appstore.entity.GFanRedirection;
import com.babeeta.appstore.storage.LocalDiskSimpleStorageService;
import com.babeeta.appstore.storage.SimpleStorageService;

/**
 * GFan数据解析入口类
 * @author xuehui.miao
 *
 */
@Service("gFanDataAnalyzer")
public class GFanDataAnalyzer implements DataAnalyzer {
	private GFanAPKDataAnalyzer gFanAPKDataAnalyzer;
	private GFanApplicationDetailDataAnalyzer gFanApplicationDetailDataAnalyzer;
	private GFanRedirectionDataAnalyzer gFanRedirectionDataAnalyzer;
	
	public void process(InputStream inputStream, Context content) throws Exception {
		String type = AnalyzerConstant.getContentType(content);
		//APK包
		if(AnalyzerConstant.urlGFanApk.equalsIgnoreCase(type)) {
			this.gFanAPKProcess(inputStream, content);
		}
		//应用详情
		else if(AnalyzerConstant.urlGFanApplicationDetail.equalsIgnoreCase(type)) {
			this.gFanApplicationDetailProcess(inputStream, content);
		}
		//跳转链接
		else if(AnalyzerConstant.urlGFanRedirection.equalsIgnoreCase(type)) {
			this.gFanRedirectionProcess(inputStream, content);
		}
		
	}
	
	/**
	 * GFan市场APK包的处理
	 * @param inputStream
	 * @param content
	 */
	private void gFanAPKProcess(InputStream inputStream, Context content) throws Exception {
//		GFanAPKDao gFanAPKDao = new GFanAPKDaoImpl();
//		SimpleStorageService ssService = new LocalDiskSimpleStorageService();
//		APKExtractVisitor<GFanAPK> visitor = new GFanAPKVisitor();
//		
//		MyProcess process = new CmdProcess();
//		
//		ApkAaptBadgingAnalyze apkAaptAnalyze = new ApkAaptBadgingAnalyze();
//		
//		ReadFile readFile = new BufferedReaderReadFile();
//		apkAaptAnalyze.setReadFile(readFile);
//		
//		visitor.setApkAaptAnalyze(apkAaptAnalyze);
//		visitor.setProcess(process);
//		
//		GFanAPKDataAnalyzer analyzer = new GFanAPKDataAnalyzer();
//		analyzer.setgFanAPKDao(gFanAPKDao);
//		analyzer.setSsService(ssService);
//		analyzer.setVisitor(visitor);
		gFanAPKDataAnalyzer.process(inputStream, content);
	}
	
	/**
	 * GFan市场应用详情的处理
	 * @param inputStream
	 * @param content
	 */
	private void gFanApplicationDetailProcess(InputStream inputStream, Context content) throws Exception {
//		GFanApplicationDetailDao gFanApplicationDetailDao = new GFanApplicationDetailDaoImpl();
//		ExtractVisitor<GFanApplicationDetail> visitor = new GFanApplicationDetailVisitor();
//		
//		GFanApplicationDetailDataAnalyzer analyzer = new GFanApplicationDetailDataAnalyzer();
//		analyzer.setgFanApplicationDetailDao(gFanApplicationDetailDao);
//		analyzer.setVisitor(visitor);
		gFanApplicationDetailDataAnalyzer.process(inputStream, content);
	}
	
	/**
	 * GFan市场跳转页面的处理
	 * @param inputStream
	 * @param content
	 */
	private void gFanRedirectionProcess(InputStream inputStream, Context content) throws Exception {
//		GFanRedirectionDao gFanRedirectionDao = new GFanRedirectionDaoImpl();
//		ExtractVisitor<GFanRedirection> visitor = new GFanRedirectionVisitor();
//		
//		GFanRedirectionDataAnalyzer analyzer = new GFanRedirectionDataAnalyzer();
//		analyzer.setgFanRedirectionDao(gFanRedirectionDao);
//		analyzer.setVisitor(visitor);
		gFanRedirectionDataAnalyzer.process(inputStream, content);
	}
	
	@Autowired
	@Required
	public void setgFanAPKDataAnalyzer(GFanAPKDataAnalyzer gFanAPKDataAnalyzer) {
		this.gFanAPKDataAnalyzer = gFanAPKDataAnalyzer;
	}
	
	@Autowired
	@Required
	public void setgFanApplicationDetailDataAnalyzer(
			GFanApplicationDetailDataAnalyzer gFanApplicationDetailDataAnalyzer) {
		this.gFanApplicationDetailDataAnalyzer = gFanApplicationDetailDataAnalyzer;
	}
	
	@Autowired
	@Required
	public void setgFanRedirectionDataAnalyzer(
			GFanRedirectionDataAnalyzer gFanRedirectionDataAnalyzer) {
		this.gFanRedirectionDataAnalyzer = gFanRedirectionDataAnalyzer;
	}
	
}
