/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.eoe;

import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.entity.EoeMarketAPK;

/**
 * @author xuehui.miao
 *
 */
@Service
public class EoeMarketAPKVisitor extends APKExtractVisitor<EoeMarketAPK> {
	private EoeMarketAPK eoeMarketAPK;
	
	public EoeMarketAPKVisitor() {
		eoeMarketAPK = new EoeMarketAPK();
	}
	
	public EoeMarketAPK getEntity() throws Exception {
		this.getContentFromAPK(eoeMarketAPK);
		return eoeMarketAPK;
	}
	
	
}
