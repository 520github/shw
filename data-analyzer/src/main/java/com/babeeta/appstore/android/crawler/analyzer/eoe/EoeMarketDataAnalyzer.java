package com.babeeta.appstore.android.crawler.analyzer.eoe;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.apk.ApkAaptBadgingAnalyze;
import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.DataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketAPKDataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketAPKVisitor;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketApplicationDetailDataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketApplicationDetailVisitor;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketRedirectionDataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketRedirectionVisitor;
import com.babeeta.appstore.android.file.BufferedReaderReadFile;
import com.babeeta.appstore.android.file.ReadFile;
import com.babeeta.appstore.android.process.CmdProcess;
import com.babeeta.appstore.android.process.MyProcess;
import com.babeeta.appstore.dao.AppDao;
import com.babeeta.appstore.dao.EoeMarketAPKDao;
import com.babeeta.appstore.dao.EoeMarketApplicationDetailDao;
import com.babeeta.appstore.dao.EoeMarketRedirectionDao;
import com.babeeta.appstore.dao.impl.EoeMarketAPKDaoImpl;
import com.babeeta.appstore.dao.impl.EoeMarketApplicationDetailDaoImpl;
import com.babeeta.appstore.dao.impl.EoeMarketRedirectionDaoImpl;
import com.babeeta.appstore.entity.EoeMarketAPK;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;
import com.babeeta.appstore.entity.EoeMarketRedirection;
import com.babeeta.appstore.storage.LocalDiskSimpleStorageService;
import com.babeeta.appstore.storage.SimpleStorageService;

@Service
public class EoeMarketDataAnalyzer implements DataAnalyzer {
	private EoeMarketAPKDataAnalyzer eoeMarketAPKDataAnalyzer;
	private EoeMarketApplicationDetailDataAnalyzer eoeMarketApplicationDetailDataAnalyzer;
	private EoeMarketRedirectionDataAnalyzer eoeMarketRedirectionDataAnalyzer;
	
	public void process(InputStream inputStream, Context content) throws Exception {
//		if(inputStream == null) {
//			throw new Exception("传入的输入流对象为空！");
//		}
		String type = AnalyzerConstant.getContentType(content);
		//APK包
		if(AnalyzerConstant.urlEoeApk.equalsIgnoreCase(type)) {
			this.eoeAPKProcess(inputStream, content);
		}
		//跳转链接
		else if(AnalyzerConstant.urlEoeRedirection.equalsIgnoreCase(type)) {
			this.eoeRedirectionProcess(inputStream, content);
		}
		//应用详情
		else if(AnalyzerConstant.urlEoeApplicationDetail.equalsIgnoreCase(type)) {
			this.eoeApplicationDetailProcess(inputStream, content);
		}
		else {
			
		}
	}
	
	/**
	 * EoeMarket市场APK包的处理
	 * @param inputStream
	 * @param content
	 */
	private void eoeAPKProcess(InputStream inputStream, Context content) throws Exception {
		//EoeMarketAPKDao EoeMarketAPKDao = new EoeMarketAPKDaoImpl();
		//SimpleStorageService ssService = new LocalDiskSimpleStorageService();
		//APKExtractVisitor<EoeMarketAPK> visitor = new EoeMarketAPKVisitor();
		
		//MyProcess process = new CmdProcess();
		
		//ApkAaptBadgingAnalyze apkAaptAnalyze = new ApkAaptBadgingAnalyze();
		
		//ReadFile readFile = new BufferedReaderReadFile();
		//apkAaptAnalyze.setReadFile(readFile);
		
		//visitor.setApkAaptAnalyze(apkAaptAnalyze);
		//visitor.setProcess(process);
		
		//appDao.countBest();
		//EoeMarketAPKDataAnalyzer analyzer = new EoeMarketAPKDataAnalyzer();
		//analyzer.setEoeMarketAPKDao(EoeMarketAPKDao);
		//eoeMarketAPKDataAnalyzer.setSsService(ssService);
		//eoeMarketAPKDataAnalyzer.setVisitor(visitor);
		eoeMarketAPKDataAnalyzer.process(inputStream, content);
	}
	
	/**
	 * EoeMarket市场应用详情的处理
	 * @param inputStream
	 * @param content
	 */
	private void eoeApplicationDetailProcess(InputStream inputStream, Context content) throws Exception {
//		EoeMarketApplicationDetailDao eoeAppDetailDao = new EoeMarketApplicationDetailDaoImpl();
//		ExtractVisitor<EoeMarketApplicationDetail> visitor = new EoeMarketApplicationDetailVisitor();
//		
//		EoeMarketApplicationDetailDataAnalyzer analyzer = new EoeMarketApplicationDetailDataAnalyzer();
//		analyzer.setEoeAppDetailDao(eoeAppDetailDao);
//		analyzer.setVisitor(visitor);
		eoeMarketApplicationDetailDataAnalyzer.process(inputStream, content);
	}
	
	/**
	 * EoeMarket市场跳转页面的处理
	 * @param inputStream
	 * @param content
	 */
	private void eoeRedirectionProcess(InputStream inputStream, Context content) throws Exception {
//		EoeMarketRedirectionDao EoeMarketRedirectionDao = new EoeMarketRedirectionDaoImpl();
//		ExtractVisitor<EoeMarketRedirection> visitor = new EoeMarketRedirectionVisitor();
//		
//		EoeMarketRedirectionDataAnalyzer analyzer = new EoeMarketRedirectionDataAnalyzer();
//		analyzer.setEoeMarketRedirectionDao(EoeMarketRedirectionDao);
//		analyzer.setVisitor(visitor);
		eoeMarketRedirectionDataAnalyzer.process(inputStream, content);
	}
	
	@Autowired
	@Required
	public void setEoeMarketAPKDataAnalyzer(
			EoeMarketAPKDataAnalyzer eoeMarketAPKDataAnalyzer) {
		this.eoeMarketAPKDataAnalyzer = eoeMarketAPKDataAnalyzer;
	}
	
	@Autowired
	@Required
	public void setEoeMarketApplicationDetailDataAnalyzer(
			EoeMarketApplicationDetailDataAnalyzer eoeMarketApplicationDetailDataAnalyzer) {
		this.eoeMarketApplicationDetailDataAnalyzer = eoeMarketApplicationDetailDataAnalyzer;
	}
	
	@Autowired
	@Required
	public void setEoeMarketRedirectionDataAnalyzer(
			EoeMarketRedirectionDataAnalyzer eoeMarketRedirectionDataAnalyzer) {
		this.eoeMarketRedirectionDataAnalyzer = eoeMarketRedirectionDataAnalyzer;
	}
	
}
