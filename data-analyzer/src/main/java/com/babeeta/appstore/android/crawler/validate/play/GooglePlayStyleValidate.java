/**
 * 
 */
package com.babeeta.appstore.android.crawler.validate.play;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import com.babeeta.appstore.android.crawler.analyzer.play.GooglePlayVisitor;
import com.babeeta.appstore.android.crawler.validate.HtmlStyleValidate;
import com.babeeta.appstore.entity.GooglePlay;

/**
 * GooglePlay市场HTML详情页面的样式格式校验
 * @author xuehui.miao
 *
 */
public class GooglePlayStyleValidate extends HtmlStyleValidate<GooglePlay> {
	private GooglePlay detail;
	
	public GooglePlayStyleValidate(InputStream inputStream,
			GooglePlayVisitor visitor) {
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
		if(StringUtils.isBlank(detail.getVersion())) {//版本号
			message.append("版本号解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getSize())) {//大小
			message.append("大小解析失败!\n");
			validate = false;
		}
		if(StringUtils.isBlank(detail.getPrice())) {//价格
			message.append("价格解析失败!\n");
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
		if(StringUtils.isBlank(detail.getPublisher())) {//出版商
			message.append("出版商解析失败!\n");
			validate = false;
		}
		if(message.length() > 0) {
			message.insert(0, "GooglePlay市场HTML详情页面的样式格式发生变更,部分内容无法解析,具体如下:\n");
		}
		return validate;
	}
}
