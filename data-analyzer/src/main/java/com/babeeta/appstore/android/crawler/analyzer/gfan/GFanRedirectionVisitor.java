/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.gfan;

import org.htmlparser.Tag;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.entity.GFanRedirection;

/**
 * @author xuehui.miao
 *
 */
@Service("gFanRedirectionVisitor")
public class GFanRedirectionVisitor extends ExtractVisitor<GFanRedirection> {
	protected static Logger logger = LoggerFactory.getLogger(GFanRedirectionVisitor.class);
	private GFanRedirection gFanRedirection;
	
	//URL
	CssSelectorNodeFilter apkUrl = new CssSelectorNodeFilter(
			"script");
	@Override
	public void beginParsing() {
		gFanRedirection = new GFanRedirection();
	}
	
	public GFanRedirection getEntity() {
		return gFanRedirection;
	}
	
	@Override
	public void visitTag(Tag tag) {	
		if(apkUrl.accept(tag)) {
			String apkUrlValue = getApkUrl(tag.toPlainTextString());
			logger.debug("apkUrlValue:{}", apkUrlValue);
			gFanRedirection.setApkUrl(apkUrlValue);
		}
	}
	
	private String getApkUrl(String apkUrlValue) {
		if(apkUrlValue == null)return apkUrlValue;
		if(apkUrlValue.indexOf(apkUrlName) ==-1)return apkUrlValue;
		apkUrlValue = apkUrlValue.substring(apkUrlValue.indexOf(apkUrlName) + apkUrlName.length());
		if(apkUrlValue.indexOf(apkUrlName) ==-1)return apkUrlValue;
		apkUrlValue = apkUrlValue.substring(0, apkUrlValue.indexOf(apkUrlName));
		return apkUrlValue;
	}
	
	private static String apkUrlName = "'";
	
}
