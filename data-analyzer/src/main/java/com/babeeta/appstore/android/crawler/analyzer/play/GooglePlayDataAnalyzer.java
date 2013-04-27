package com.babeeta.appstore.android.crawler.analyzer.play;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.android.crawler.analyzer.TextDataAnalyzer;
import com.babeeta.appstore.dao.GooglePlayDao;
import com.babeeta.appstore.entity.GooglePlay;

/**
 * GooglePlay市场应用详情页面的数据解析
 * @author xuehui.miao
 *
 */
@Service
public class GooglePlayDataAnalyzer extends TextDataAnalyzer<GooglePlay> {
	private GooglePlay googlePlay;
	private GooglePlayDao googlePlayDao;
	
	public GooglePlayDataAnalyzer() {
		googlePlay = new GooglePlay();
	}
	
	@Override
	public void process(String html, Context context) {
		googlePlay = visitor.getEntity();
		googlePlay.setUrl(context.getUrl());
		googlePlay.setPackageName(this.getPackageName(context.getUrl()));
		googlePlay.setId(context.getUrl());
		this.saveGooglePlay();
	}
	
	private void saveGooglePlay() {
		googlePlayDao.saveGooglePlay(googlePlay);
	}
	
	
	
	/**
	 * 从URL中获取packageName
	 * @param url
	 * @return String
	 */
	private String getPackageName(String url) {
		if(url.indexOf("?") == -1) {
			return url;
		}
		if(url.indexOf("id=") == -1) {
			return url;
		}
		
		url = url.substring(url.indexOf("?")+1);
		url = url.substring(url.indexOf("id=")+3);
		if(url.indexOf("&") == -1) {
			return url.trim();
		}
		url = url.substring(0, url.indexOf("&")).trim();
		return url;
	}
	
	@Autowired
	@Required
	public void setGooglePlayDao(GooglePlayDao googlePlayDao) {
		this.googlePlayDao = googlePlayDao;
	}
	
	@Autowired
	@Qualifier("googlePlayVisitor")
	@Required
	public void setVisitor(ExtractVisitor<GooglePlay> visitor) {
		this.visitor = visitor;
	}
	
}
