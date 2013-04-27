package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.TextDataAnalyzer;
import com.babeeta.appstore.dao.EoeMarketApplicationDetailDao;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;

/**
 * EOE市场应用详情页面的数据解析
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketApplicationDetailDataAnalyzer extends TextDataAnalyzer<EoeMarketApplicationDetail> {
	private EoeMarketApplicationDetail eoeAppDetail;
	
	private EoeMarketApplicationDetailDao eoeAppDetailDao;
	
	@Override
	public void process(String html, Context context) {
		eoeAppDetail = visitor.getEntity();
		eoeAppDetail.setUrl(context.getUrl());
		eoeAppDetail.setId(context.getUrl());
		this.saveEoeAppDetail();
	}
	
	private void saveEoeAppDetail() {
		eoeAppDetailDao.saveEoeMarketApplictionDetail(eoeAppDetail);
	}
	
	@Autowired
	@Required
	public void setEoeAppDetailDao(EoeMarketApplicationDetailDao eoeAppDetailDao) {
		this.eoeAppDetailDao = eoeAppDetailDao;
	}
	
	@Autowired
	@Qualifier("eoeMarketApplicationDetailVisitor")
	@Required
	public void setVisitor(ExtractVisitor<EoeMarketApplicationDetail> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		//this.visitor = null;
		eoeAppDetail = null;
		//eoeAppDetailDao = null;
	}
}

