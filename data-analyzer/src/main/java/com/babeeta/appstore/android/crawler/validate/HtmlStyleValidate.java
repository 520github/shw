/**
 * 
 */
package com.babeeta.appstore.android.crawler.validate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.analyze.extractor.ExtractVisitor;

/**
 * 各个市场HTML详情页面的样式格式校验
 * @author xuehui.miao
 *
 */
public abstract class HtmlStyleValidate<T> implements StyleValidate {
	private static Logger logger = LoggerFactory.getLogger(HtmlStyleValidate.class);
	protected StringBuffer message = new StringBuffer();
	protected ExtractVisitor<T> visitor;
	protected InputStream inputStream;
	
	public HtmlStyleValidate(InputStream inputStream,ExtractVisitor<T> visitor) {
		this.inputStream = inputStream;
		this.visitor = visitor;
		this.loadVisitor(this.readInputStream(inputStream));
	}
	
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.crawler.validate.StyleValidate#getValidateMessage()
	 */
	@Override
	public String getValidateMessage() {
		return message.toString();
	}
	
	/**
	 * 把流转换字符串
	 * @param inputStream
	 * @return String
	 */
	private String readInputStream(InputStream inputStream) {
		String html = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[10240];
			int len = 0;
			while((len = inputStream.read(buf)) != -1){
				out.write(buf, 0, len);
			}
			
			html = new String(out.toByteArray());
			
		} catch (Exception e) {
			logger.error("{}", e.getCause(), e);
		}
		return html;
	}
	
	/**
	 * 装入具体的visitor
	 * @param html
	 */
	private void loadVisitor(String html) {
		try {
			Parser parser;
			if(html.toLowerCase().startsWith("http:\\") || html.toLowerCase().startsWith("https:\\")) {
				parser = new Parser(html);
			}
			else {
				Lexer mLexer = new Lexer(new Page(html));
				parser = new Parser(mLexer,new DefaultParserFeedback(DefaultParserFeedback.QUIET));
			}
			parser.visitAllNodesWith(visitor);
		} catch (Exception e) {
			logger.error("{}", e.getCause(), e);
		}
		
	}
	
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
