/**
 * 
 */
package com.babeeta.appstore.android.crawler.validate;

import java.io.InputStream;

import com.babeeta.appstore.android.crawler.AnalyzerConstant;
import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketApplicationDetailVisitor;
import com.babeeta.appstore.android.crawler.analyzer.gfan.GFanApplicationDetailVisitor;
import com.babeeta.appstore.android.crawler.analyzer.play.GooglePlayVisitor;
import com.babeeta.appstore.android.crawler.validate.eoe.EoeMarketApplicationDetailStyleValidate;
import com.babeeta.appstore.android.crawler.validate.gfan.GFanApplicationDetailStyleValidate;
import com.babeeta.appstore.android.crawler.validate.play.GooglePlayStyleValidate;

/**
 * 各个市场的样式格式校验的入口
 * @author xuehui.miao
 *
 */
public class PortalStyleValidate  {
	
	/**
	 * 获取样式格式校验对象
	 * @param url
	 * @param inputStream
	 * @return StyleValidate
	 */
	public static StyleValidate getStyleValidate(String url,InputStream inputStream) {
		StyleValidate style = null;
		//通过URL获取市场来源
		String type = AnalyzerConstant.getContentType(url);
		if(AnalyzerConstant.urlEoeApplicationDetail.equalsIgnoreCase(type)) {//EOE市场
			EoeMarketApplicationDetailVisitor visitor = new EoeMarketApplicationDetailVisitor();
			style = new EoeMarketApplicationDetailStyleValidate(inputStream,visitor);
		}
		else if(AnalyzerConstant.urlGFanApplicationDetail.equalsIgnoreCase(type)) {//GFan市场
			GFanApplicationDetailVisitor visitor = new GFanApplicationDetailVisitor();
			style = new GFanApplicationDetailStyleValidate(inputStream,visitor);
		}
		else if(AnalyzerConstant.urlGooglePlay.equalsIgnoreCase(type)) {//GooglePlay市场
			GooglePlayVisitor visitor = new GooglePlayVisitor();
			style = new GooglePlayStyleValidate(inputStream,visitor);
		}
		
		return style;
	}

}
