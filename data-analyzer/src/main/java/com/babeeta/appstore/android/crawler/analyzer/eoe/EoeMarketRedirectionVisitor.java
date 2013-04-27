/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.apache.log4j.Logger;
import org.htmlparser.Tag;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.entity.EoeMarketRedirection;

/**
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketRedirectionVisitor extends ExtractVisitor<EoeMarketRedirection> {
	private static Logger logger = Logger.getLogger(EoeMarketRedirectionVisitor.class);
	private EoeMarketRedirection eoeMarketRedirection;
	
	CssSelectorNodeFilter url = new CssSelectorNodeFilter(
			"a.btndown");
	
	@Override
	public void beginParsing() {
		eoeMarketRedirection = new EoeMarketRedirection();
	}
	
	public EoeMarketRedirection getEntity() {
		return eoeMarketRedirection;
	}
	
	@Override
	public void visitTag(Tag tag) {
		if(url.accept(tag)) {
			String urlValue = tag.getAttribute("href");
			logger.debug("urlValue: " + urlValue);
			eoeMarketRedirection.setUrl(urlValue);
		}
	}
}
