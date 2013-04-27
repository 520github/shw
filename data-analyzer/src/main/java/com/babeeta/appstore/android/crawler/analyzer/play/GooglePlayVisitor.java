/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer.play;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Tag;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;
import com.babeeta.appstore.entity.GooglePlay;

/**
 * GooglePlay市场详情页面的解析逻辑
 * @author xuehui.miao
 *
 */
@Service
public class GooglePlayVisitor extends ExtractVisitor<GooglePlay> {
	protected static Logger logger = LoggerFactory.getLogger(GooglePlayVisitor.class);
	
	private GooglePlay googlePlay;
	//应用名称
	CssSelectorNodeFilter name = new CssSelectorNodeFilter(
			"h1.doc-banner-title");
	//版本号
	CssSelectorNodeFilter version = new CssSelectorNodeFilter(
			"dl.doc-metadata-list>dd[itemprop='softwareVersion']");	
	//价格
	CssSelectorNodeFilter price = new CssSelectorNodeFilter(
			"dl.doc-metadata-list>dd[itemprop='offers']");
	//参数:更新时间、大小、应用要求
	CssSelectorNodeFilter paras = new CssSelectorNodeFilter(
			"dl.doc-metadata-list>dd");
	//描述说明
	CssSelectorNodeFilter desc = new CssSelectorNodeFilter(
			"div#doc-description-container>div>div");//div#doc-description-container>div>div>
	//出版商
	CssSelectorNodeFilter publisher = new CssSelectorNodeFilter(
			"a.doc-header-link");
	//评分星级
	CssSelectorNodeFilter star = new CssSelectorNodeFilter(
			"td.doc-details-ratings-price>div>div[class='ratings goog-inline-block']");
	//评分个数
	CssSelectorNodeFilter vote = new CssSelectorNodeFilter(
			"td.doc-details-ratings-price>div>div.goog-inline-block");
	//logoURL
	CssSelectorNodeFilter logo = new CssSelectorNodeFilter(
			"div.doc-banner-icon>img");
	//截图
	CssSelectorNodeFilter screenshots = new CssSelectorNodeFilter(
			"div.screenshot-carousel-content-container>img");
	
	private List<String> screenshotsList = new ArrayList<String>();
	
	@Override
	public void beginParsing() {
		googlePlay = new GooglePlay();
		screenshotsList = new ArrayList<String>();
	}
	
	public GooglePlay getEntity() {
		return googlePlay;
	}
	
	@Override
	public void visitTag(Tag tag) {
		if(name.accept(tag)) {
			googlePlay.setName(tag.toPlainTextString());
		}
		else if(version.accept(tag)) {
			String versionValue = tag.toPlainTextString();
			googlePlay.setVersion(versionValue);
		}
		else if(price.accept(tag)) {
			String priceValue = tag.toPlainTextString();
			googlePlay.setPrice(priceValue);
		}
		else if(paras.accept(tag)) {
			String parasValue = tag.toPlainTextString();
			String html = tag.toHtml();
			if(html.indexOf(lastUpdateName) > -1) {
				googlePlay.setLastUpdate(parasValue);
			}
			else if(html.indexOf(sizeName) > -1) {
				googlePlay.setSize(parasValue);
			}
			else if(html.indexOf(requirementName) > -1) {
				googlePlay.setRequirement(parasValue);
			}
		}
		else if(desc.accept(tag)) {
			String descValue = tag.toHtml();
			googlePlay.setOriginalDescription(descValue);
		}
		else if(publisher.accept(tag)) {
			String publisherValue = tag.toPlainTextString();
			googlePlay.setPublisher(publisherValue);
		}
		else if(star.accept(tag)) {
			String starValue = tag.getAttribute("title");
			googlePlay.getScore().setStar(this.changeString2float(starValue));
		}
		else if(vote.accept(tag)) {
			String voteValue = tag.toPlainTextString();
			googlePlay.getScore().setVote(this.changeString2int(voteValue));
		}
		else if(logo.accept(tag)) {
			String logoValue = tag.getAttribute("src");
			googlePlay.setLogo(logoValue);
		}
		else if(screenshots.accept(tag)) {
			String screenshotsValue = tag.getAttribute("src");
			if(screenshotsValue.trim().startsWith("http")) {
				screenshotsList.add(screenshotsValue);
			}
			googlePlay.setScreenshotsForPhone(screenshotsList);
		}
	}
	
	private static String lastUpdateName = "datePublished";
	private static String sizeName = "fileSize";
	private static String requirementName = "版本";
}
