package com.babeeta.appstore.android.crawler.analyzer.gfan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.TextDataAnalyzer;
import com.babeeta.appstore.dao.GFanApplicationDetailDao;
import com.babeeta.appstore.entity.GFanApplicationDetail;

/**
 * GFan市场应用详情页面的数据解析
 * @author xuehui.miao
 *
 */
@Service("gFanApplicationDetailDataAnalyzer")
public class GFanApplicationDetailDataAnalyzer extends TextDataAnalyzer<GFanApplicationDetail> {
	private GFanApplicationDetail gFanApplicationDetail;
	private GFanApplicationDetailDao gFanApplicationDetailDao;
	
	
	public GFanApplicationDetailDataAnalyzer() {
		gFanApplicationDetail = new GFanApplicationDetail();
	}
	@Override
	public void process(String html, Context context) {
		gFanApplicationDetail = visitor.getEntity();
		gFanApplicationDetail.setUrl(context.getUrl());
		gFanApplicationDetail.setId(context.getUrl());
		this.saveGFanApplicationDetail();
	}
	
	private void saveGFanApplicationDetail() {
		gFanApplicationDetailDao.saveGFanApplicationDetail(gFanApplicationDetail);
	}
	
	@Autowired
	@Required
	public void setgFanApplicationDetailDao(
			GFanApplicationDetailDao gFanApplicationDetailDao) {
		this.gFanApplicationDetailDao = gFanApplicationDetailDao;
	}
	
	@Autowired
	@Qualifier("gFanApplicationDetailVisitor")
	@Required
	public void setVisitor(ExtractVisitor<GFanApplicationDetail> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		gFanApplicationDetail = null;
		//gFanApplicationDetailDao = null;
	}
}
