package com.babeeta.appstore.android.crawler.analyzer;

import java.io.InputStream;

/**
 * URL资源分析接口
 * @author leon
 *
 */
public interface DataAnalyzer {

	/**
	 * 处理一个URI资源的数据。
	 * @param inputStream 用于读取数据Body
	 * @param context 解析的上下文
	 * @throws Exception 处理失败
	 */
	public void process(InputStream inputStream, Context context) throws Exception;

}
