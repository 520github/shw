package com.babeeta.appstore.android.crawler.analyzer.gfan;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Tag;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.entity.GFanApplicationDetail;

/**
 * GFan市场详情页面的解析逻辑
 * 
 * @author xuehui.miao
 * 
 */
@Service("gFanApplicationDetailVisitor")
public class GFanApplicationDetailVisitor extends
		ExtractVisitor<GFanApplicationDetail> {
	protected static Logger logger = LoggerFactory
			.getLogger(GFanApplicationDetailVisitor.class);

	private GFanApplicationDetail gFanApplicationDetail;
	// 应用名称
	CssSelectorNodeFilter name = new CssSelectorNodeFilter(
			"div.app_name");
	// URL
	CssSelectorNodeFilter redirectionUrl = new CssSelectorNodeFilter(
			"input#hidDownApkUrl");
	// 版本号
	CssSelectorNodeFilter version = new CssSelectorNodeFilter(
			"span.v");
	// 价格
	CssSelectorNodeFilter price = new CssSelectorNodeFilter(
			"span[class='i i1']>span.d_i_c");
	// 更新时间
	CssSelectorNodeFilter lastUpdate = new CssSelectorNodeFilter(
			"span[class='i i3']>span.d_i_c");
	// 大小
	CssSelectorNodeFilter size = new CssSelectorNodeFilter(
			"span[class='i i5']>span.d_i_c");
	// 应用要求
	CssSelectorNodeFilter requirement = new CssSelectorNodeFilter(
			"span[class='i i6']>span.d_i_c");
	// 描述说明
	CssSelectorNodeFilter desc = new CssSelectorNodeFilter(
			"div.intro");
	// 出版商
	CssSelectorNodeFilter publisher = new CssSelectorNodeFilter(
			"span[class='i i2']>span.d_i_c");
	// 评分星级
	CssSelectorNodeFilter star = new CssSelectorNodeFilter(
			"div[class='score i']>span");
	// logoURL
	CssSelectorNodeFilter logo = new CssSelectorNodeFilter(
			"span[class='i icon']>img");
	// 截图
	CssSelectorNodeFilter screenshots = new CssSelectorNodeFilter(
			"span.imgouter>img");

	private List<String> screenshotsList = new ArrayList<String>();

	private static String GFanHomePage = "http://apk.gfan.com";

	@Override
	public void beginParsing() {
		gFanApplicationDetail = new GFanApplicationDetail();
		screenshotsList = new ArrayList<String>();
	}

	@Override
	public GFanApplicationDetail getEntity() {
		return gFanApplicationDetail;
	}

	@Override
	public void visitTag(Tag tag) {
		if (name.accept(tag)) {
			String nameValue = tag.getFirstChild().toPlainTextString();
			gFanApplicationDetail.setName(nameValue);
		}
		else if (redirectionUrl.accept(tag)) {
			String redirectionUrlValue = GFanHomePage
					+ tag.getAttribute("value");
			gFanApplicationDetail.setRedirectionUrl(redirectionUrlValue);
		}
		else if (version.accept(tag)) {
			String versionValue = tag.toPlainTextString();
			versionValue = versionValue
					.substring(versionValue.indexOf("Ver：") + 4);
			gFanApplicationDetail.setVersion(versionValue);
			logger.debug("versionValue:" + versionValue);
		}
		else if (price.accept(tag)) {
			String priceValue = tag.toPlainTextString();
			gFanApplicationDetail.setPrice(priceValue);
		}
		else if (lastUpdate.accept(tag)) {
			String lastUpdateValue = tag.toPlainTextString();
			gFanApplicationDetail.setLastUpdate(lastUpdateValue);
		}
		else if (size.accept(tag)) {
			String sizeValue = tag.toPlainTextString();
			gFanApplicationDetail.setSize(sizeValue);
		}
		else if (requirement.accept(tag)) {
			String requirementValue = tag.toPlainTextString();
			gFanApplicationDetail.setRequirement(requirementValue);
			logger.debug("requirement:" + requirementValue);
		}
		else if (desc.accept(tag)) {
			String descValue = tag.toPlainTextString();
			gFanApplicationDetail.setOriginalDescription(descValue);
		}
		else if (publisher.accept(tag)) {
			String publisherValue = tag.toPlainTextString();
			gFanApplicationDetail.setPublisher(publisherValue);
		}
		else if (star.accept(tag)) {
			String starValue = tag.getAttribute("class");
			float value = changeString2float(starValue);
			value = value / 2;
			gFanApplicationDetail.getScore().setStar(value);
		}
		else if (logo.accept(tag)) {
			String logoValue = tag.getAttribute("src");
			gFanApplicationDetail.setLogo(logoValue);
		}
		else if (screenshots.accept(tag)) {
			String screenshotsValue = tag.getAttribute("src");
			if (screenshotsValue != null
					&& screenshotsValue.trim().startsWith("http")) {
				screenshotsList.add(screenshotsValue);
			}
			gFanApplicationDetail.setScreenshotsForPhone(screenshotsList);
		}
	}
}
