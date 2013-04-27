/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.gfan;

import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.entity.GFanAPK;

/**
 * GFan市场APK包的解析逻辑
 * @author xuehui.miao
 *
 */
@Service("gFanAPKVisitor")
public class GFanAPKVisitor extends APKExtractVisitor<GFanAPK> {
	private GFanAPK gFanAPK;
	
	public GFanAPKVisitor() {
		gFanAPK = new GFanAPK();
	}
	
	public GFanAPK getEntity() throws Exception {
		this.getContentFromAPK(gFanAPK);
		return gFanAPK;
	}
}
