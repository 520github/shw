package com.babeeta.appstore.analyze.extractor;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

public class UrlParserExtractor<T> implements Extractor<T> {
	private static Logger logger = Logger.getLogger(HtmlParserExtractor.class);

	protected Parser parser;
	protected ExtractVisitor<T> visitor;

	public UrlParserExtractor(ExtractVisitor<T> visitor) {
		this.visitor = visitor;
	}
	
	@Override
	public T extract(String url) {	
		try {
			parser = new Parser(url);
			parser.reset();
			parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			logger.error("Error on parsing", e.getCause());
		}
		return visitor.getEntity();
	}
}
