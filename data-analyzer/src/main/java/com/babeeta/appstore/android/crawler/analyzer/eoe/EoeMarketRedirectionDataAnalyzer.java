package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.TextDataAnalyzer;
import com.babeeta.appstore.dao.EoeMarketRedirectionDao;
import com.babeeta.appstore.entity.EoeMarketRedirection;

/**
 * EOE市场应用详情页面的数据解析
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketRedirectionDataAnalyzer extends TextDataAnalyzer<EoeMarketRedirection> {
	private EoeMarketRedirection eoeMarketRedirection;
	private EoeMarketRedirectionDao eoeMarketRedirectionDao;
	
	public EoeMarketRedirectionDataAnalyzer() {
		eoeMarketRedirection = new EoeMarketRedirection();
	}
	
	@Override
	public void process(String html, Context context) {
		eoeMarketRedirection = visitor.getEntity();
		eoeMarketRedirection.setUrl(context.getUrl());
		eoeMarketRedirection.setApkUrl(context.getLocation());
		eoeMarketRedirection.setId(context.getUrl());
		this.saveEoeMarketRedirection();
	}
	
	private void saveEoeMarketRedirection() {
		eoeMarketRedirectionDao.saveEoeMarketRedirection(eoeMarketRedirection);
	}
	
	@Autowired
	@Required
	public void setEoeMarketRedirectionDao(
			EoeMarketRedirectionDao eoeMarketRedirectionDao) {
		this.eoeMarketRedirectionDao = eoeMarketRedirectionDao;
	}
	
	@Autowired
	@Qualifier("eoeMarketRedirectionVisitor")
	@Required
	public void setVisitor(ExtractVisitor<EoeMarketRedirection> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		//this.visitor = null;
		eoeMarketRedirection = null;
		//eoeMarketRedirectionDao = null;
	}
	
}
