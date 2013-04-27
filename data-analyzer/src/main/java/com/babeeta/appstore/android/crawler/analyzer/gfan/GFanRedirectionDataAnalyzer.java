package com.babeeta.appstore.android.crawler.analyzer.gfan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.dao.GFanRedirectionDao;
import com.babeeta.appstore.entity.GFanRedirection;
import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.TextDataAnalyzer;

/**
 * GFan市场跳转到APK页面的数据解析
 * @author xuehui.miao
 *
 */
@Service("gFanRedirectionDataAnalyzer")
public class GFanRedirectionDataAnalyzer extends TextDataAnalyzer<GFanRedirection> {
	private GFanRedirection gFanRedirection;
	private GFanRedirectionDao gFanRedirectionDao;
	
	public GFanRedirectionDataAnalyzer() {
		gFanRedirection = new GFanRedirection();
	}
	
	@Override
	public void process(String html, Context context) {
		gFanRedirection = visitor.getEntity();
		gFanRedirection.setUrl(context.getUrl());
		gFanRedirection.setId(context.getUrl());
		this.saveGFanRedirection();
	}
	
	private void saveGFanRedirection() {
		gFanRedirectionDao.saveGFanRedirection(gFanRedirection);
	}
	
	@Autowired
	@Required
	public void setgFanRedirectionDao(GFanRedirectionDao gFanRedirectionDao) {
		this.gFanRedirectionDao = gFanRedirectionDao;
	}
	
	@Autowired
	@Qualifier("gFanRedirectionVisitor")
	@Required
	public void setVisitor(ExtractVisitor<GFanRedirection> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		gFanRedirection = null;
		//gFanRedirectionDao = null;
	}

}
