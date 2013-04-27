/**
 * 
 */
package com.babeeta.appstore.analyze.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuehui.miao
 *
 */
public class APKParserExtractor<T> implements Extractor<T> {
	private static Logger logger = LoggerFactory.getLogger(HtmlParserExtractor.class);

	protected APKExtractVisitor<T> visitor;

	public APKParserExtractor(APKExtractVisitor<T> visitor) {
		this.visitor = visitor;
	}
	
	@Override
	public T extract(String apkFilePath) {	
		try {
			visitor.setApkFilePath(apkFilePath);
			return visitor.getEntity();
		} catch (Exception e) {
			logger.error("Error on parsing", e.getCause());
		}
		return null;
	}
}
