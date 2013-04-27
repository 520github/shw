package com.babeeta.appstore.android.crawler.analyzer.eoe;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Tag;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.entity.EoeMarketApplicationDetail;

@Service
public class EoeMarketApplicationDetailVisitor extends
		ExtractVisitor<EoeMarketApplicationDetail> {
	private static Logger logger = LoggerFactory
			.getLogger(EoeMarketApplicationDetailVisitor.class);
	private EoeMarketApplicationDetail eoeAppDetail;
	// 应用名称
	CssSelectorNodeFilter name = new CssSelectorNodeFilter(
			"div.md_top>dl>dt>h1");
	// 跳转APK连接
	CssSelectorNodeFilter redirectionUrl = new CssSelectorNodeFilter(
			"a.btndown");// a.btndown | h4.down_2>a
	// 版本号
	CssSelectorNodeFilter version = new CssSelectorNodeFilter(
			"div.md_top>dl>dt>span");
	// 参数:大小、适用、更新时间
	CssSelectorNodeFilter param = new CssSelectorNodeFilter(
			"div.middle_details>div.md_top>div.param");
	// 描述说明
	CssSelectorNodeFilter desc = new CssSelectorNodeFilter(
			"div.d_details");
	// 出版商
	// CssSelectorNodeFilter publisher = new CssSelectorNodeFilter(
	// "span");

	// 评分星级
	CssSelectorNodeFilter star = new CssSelectorNodeFilter(
			"div.stars>em");
	// logoURL
	CssSelectorNodeFilter logo = new CssSelectorNodeFilter(
			"div.md_top>a>img");
	// 截图
	CssSelectorNodeFilter screenshots = new CssSelectorNodeFilter(
			"img.screenshot");

	private List<String> screenshotsList = new ArrayList<String>();

	private static String versionName = "版本：";

	private static String sizeName = "大小：";

	private static String requirementName = "适用：";

	private static String lastUpdateName = "更新时间：";

	/**
	 * 解析大小、适用、更新时间等参数
	 * 
	 * @param param
	 */
	private void parseParam(String param) {
		String splitParas[] = param.split("&nbsp;");
		for (int i = 0; i < splitParas.length; i++) {
			String split = splitParas[i].trim();
			if (split.startsWith(sizeName)) {
				split = split.substring(split.indexOf(sizeName)
						+ sizeName.length());
				eoeAppDetail.setSize(split);
			}
			else if (split.startsWith(requirementName)) {
				split = split.substring(split.indexOf(requirementName)
						+ requirementName.length());
				eoeAppDetail.setRequirement(split);
			}
			else if (split.startsWith(lastUpdateName)) {
				split = split.substring(split.indexOf(lastUpdateName)
						+ lastUpdateName.length());
				eoeAppDetail.setLastUpdate(split);
			}
			// logger.debug("{}",split);
		}
	}

	@Override
	public void beginParsing() {
		eoeAppDetail = new EoeMarketApplicationDetail();
		screenshotsList = new ArrayList<String>();
	}

	@Override
	public EoeMarketApplicationDetail getEntity() {
		return eoeAppDetail;
	}

	@Override
	public void visitTag(Tag tag) {
		if (name.accept(tag)) {
			String nameValue = tag.toPlainTextString();
			logger.debug("nameValue: " + nameValue);
			eoeAppDetail.setName(nameValue);
		}
		else if (redirectionUrl.accept(tag)) {
			String redirectionUrlValue = tag.getAttribute("href");
			eoeAppDetail.setRedirectionUrl(redirectionUrlValue);
		}
		else if (version.accept(tag)) {
			String versionValue = tag.toPlainTextString();
			versionValue = versionValue.substring(versionValue
					.indexOf(versionName) + versionName.length());
			eoeAppDetail.setVersion(versionValue);
		}
		else if (param.accept(tag)) {
			String param = tag.toPlainTextString();
			logger.debug("param:{}", param);
			parseParam(param);
		}
		else if (desc.accept(tag)) {
			String descValue = tag.toPlainTextString();
			eoeAppDetail.setOriginalDescription(descValue);
		}
		else if (star.accept(tag)) {
			String starValue = tag.getAttribute("style");
			float value = changeString2float(starValue);
			value = value / 20;
			eoeAppDetail.getScore().setStar(value);
		}
		else if (logo.accept(tag)) {
			String logoValue = tag.getAttribute("src");
			eoeAppDetail.setLogo(logoValue);
		}
		else if (screenshots.accept(tag)) {
			String screenshotsValue = tag.getAttribute("src");
			screenshotsList.add(screenshotsValue);
			eoeAppDetail.setScreenshotsForPhone(screenshotsList);
		}
	}
}
