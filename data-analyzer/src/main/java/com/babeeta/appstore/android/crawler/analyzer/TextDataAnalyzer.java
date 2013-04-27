package com.babeeta.appstore.android.crawler.analyzer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;

/**
 * HTML文本解析抽象类
 * @author xuehui.miao
 *
 */
public abstract class TextDataAnalyzer<T> implements DataAnalyzer {
	private static Logger logger = LoggerFactory.getLogger(TextDataAnalyzer.class);
	protected ExtractVisitor<T> visitor;
	protected Parser parser;
	
	public abstract void process(String html, Context context) ;
	
	@Override
	public void process(InputStream inputStream, Context context) {
		String html = "";
		try {
			if(inputStream != null) {
				html = this.readInputStream(inputStream);
			}
			else {
				html = context.getUrl();
			}
			//logger.debug("html:{}", html);
			this.loadVisitor(html);
			
			this.process(html, context);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			html = null;
			this.destroy();
		}
	}
	
	/**
	 * 把流转换字符串
	 * @param inputStream
	 * @return String
	 */
	private String readInputStream(InputStream inputStream) {
		String html = "";
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			byte[] buf = new byte[10240];
			int len = 0;
			while((len = inputStream.read(buf)) != -1){
				out.write(buf, 0, len);
			}
			
			html = new String(out.toByteArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
				out = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return html;
	}
	
	/**
	 * 装入具体的visitor
	 * @param html
	 * @throws Exception
	 */
	private void loadVisitor(String html) throws Exception {
		
		if(html.toLowerCase().startsWith("http://") || html.toLowerCase().startsWith("https://")) {
			parser = new Parser(html);
		}
		else {
			//parser = new Parser();
			Lexer mLexer = new Lexer(new Page(html));
			parser = new Parser(mLexer,new DefaultParserFeedback(DefaultParserFeedback.QUIET));
			//parser.setResource(html);
		}
		parser.visitAllNodesWith(visitor);
	}
	
	
	public ExtractVisitor<T> getVisitor() {
		return visitor;
	}
	
	@Autowired
	@Required
	public void setVisitor(ExtractVisitor<T> visitor) {
		this.visitor = visitor;
	}
	
	protected void destroy() {
		//visitor = null;
		this.parser = null;
	}
}
