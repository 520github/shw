/**
 * 
 */
package com.babeeta.appstore.android.crawler.validate.eoe;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.babeeta.appstore.android.crawler.analyzer.eoe.EoeMarketApplicationDetailVisitor;
import com.babeeta.appstore.android.crawler.validate.HtmlStyleValidate;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;

/**
 * EOE市场HTML详情页面的样式格式校验
 * @author xuehui.miao
 *
 */
public class EoeMarketApplicationDetailStyleValidate extends
		HtmlStyleValidate<EoeMarketApplicationDetail> {
	
	private EoeMarketApplicationDetail detail;
	
	public EoeMarketApplicationDetailStyleValidate(InputStream inputStream,
			EoeMarketApplicationDetailVisitor visitor) {
		super(inputStream, visitor);
	}
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.crawler.validate.StyleValidate#validateStyle(java.io.InputStream)
	 */
	@Override
	public boolean validateStyle() {
		boolean validate = true;
		detail = visitor.getEntity();
		if(StringUtils.isBlank(detail.getName())) {//应用名称
			message.append("应用名称解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getRedirectionUrl())) {//跳转APK连接
			message.append("跳转APK连接解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getVersion())) {//版本号
			message.append("版本号解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getSize())) {//大小
			message.append("大小解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getLastUpdate())) {//更新时间
			message.append("更新时间解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getRequirement())) {//应用要求
			message.append("应用要求解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getOriginalDescription())) {//描述说明
			message.append("描述说明解析失败!\n");
			validate = false;
		}
		if(message.length() > 0) {
			message.insert(0, "EOE市场HTML详情页面的样式格式发生变更,部分内容无法解析,具体如下:\n");
		}
		return validate;
	}
}
