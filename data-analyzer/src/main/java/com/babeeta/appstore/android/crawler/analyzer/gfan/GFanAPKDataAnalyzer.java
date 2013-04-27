package com.babeeta.appstore.android.crawler.analyzer.gfan;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.android.crawler.analyzer.APKDataAnalyzer;
import com.babeeta.appstore.android.crawler.analyzer.Context;
import com.babeeta.appstore.dao.GFanAPKDao;
import com.babeeta.appstore.entity.GFanAPK;

/**
 * GFan市场APK包的数据解析
 * @author xuehui.miao
 *
 */
@Service("gFanAPKDataAnalyzer")
public class GFanAPKDataAnalyzer extends APKDataAnalyzer<GFanAPK> {
	private GFanAPK gFanAPK;
	private GFanAPKDao gFanAPKDao;
	
	public GFanAPKDataAnalyzer() {
		gFanAPK = new GFanAPK();
	}
	
	@Override
	public void process(InputStream inputStream, Context context) throws Exception {
		try {
			super.process(inputStream, context);
			gFanAPK = visitor.getEntity();
			gFanAPK.setUrl(context.getUrl());
			gFanAPK.setId(context.getUrl());
			gFanAPK.setApkId(uuid);
			this.saveGFanAPK();
		} catch (Exception e) {
			throw e;
		}finally {
			this.destroy();
		}
	}
	
	private void saveGFanAPK() {
		gFanAPKDao.saveGFanAPK(gFanAPK);
	}
	
	@Autowired
	@Required
	public void setgFanAPKDao(GFanAPKDao gFanAPKDao) {
		this.gFanAPKDao = gFanAPKDao;
	}
	
	@Autowired
	@Qualifier("gFanAPKVisitor")
	@Required
	public void setVisitor(APKExtractVisitor<GFanAPK> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		super.destroy();
		//this.visitor = null;
		this.gFanAPK = null;
		//this.gFanAPKDao = null;
	}

}
