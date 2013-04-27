package com.babeeta.appstore.analyze.extractor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

public class HtmlParserExtractor<T> implements Extractor<T> {

	private static Logger logger = LoggerFactory.getLogger(HtmlParserExtractor.class);

	protected Parser parser = new Parser();
	protected ExtractVisitor<T> visitor;

	public HtmlParserExtractor(ExtractVisitor<T> visitor) {
		this.visitor = visitor;
	}

	@Override
	public T extract(String source) {
		parser.reset();
		try {
			parser.setResource(source);
			parser.visitAllNodesWith(visitor);
		} catch (ParserException e) {
			logger.error("Error on parsing", e.getCause());
		}
		return visitor.getEntity();
	}
}
