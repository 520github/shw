package com.babeeta.appstore.android.crawler.analyzer.eoe;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.APKDataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.dao.EoeMarketAPKDao;
import com.babeeta.appstore.entity.EoeMarketAPK;

/**
 * EOE市场APK包的数据解析
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketAPKDataAnalyzer extends APKDataAnalyzer<EoeMarketAPK> {
	private EoeMarketAPK eoeMarketAPK;
	
	private EoeMarketAPKDao eoeMarketAPKDao;
	
	public EoeMarketAPKDataAnalyzer() {
		eoeMarketAPK = new EoeMarketAPK();
	}
	
	@Override
	public void process(InputStream inputStream, Context context) throws Exception {
		try {
			super.process(inputStream, context);
			eoeMarketAPK = visitor.getEntity();
			eoeMarketAPK.setUrl(context.getUrl());
			eoeMarketAPK.setId(context.getUrl());
			eoeMarketAPK.setApkId(uuid);
			this.saveEoeMarketAPK();
		} catch (Exception e) {
			throw e;
		}finally {
			this.destroy();
		}
		
	}
	
	private void saveEoeMarketAPK() {
		eoeMarketAPKDao.saveEoeMarketAPK(eoeMarketAPK);
	}
	
	@Autowired
	@Required
	public void setEoeMarketAPKDao(EoeMarketAPKDao eoeMarketAPKDao) {
		this.eoeMarketAPKDao = eoeMarketAPKDao;
	}
	
	@Autowired
	@Qualifier("eoeMarketAPKVisitor")
	@Required
	public void setVisitor(APKExtractVisitor<EoeMarketAPK> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		//this.visitor = null;
		this.eoeMarketAPK = null;
		//this.eoeMarketAPKDao = null;
	}
	
}
