/**
 * 
 */
package com.babeeta.appstore.android.crawler.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.babeeta.appstore.A_Test;

/**
 * 各个市场数据解析测试基类
 * @author xuehui.miao
 *
 */
public abstract class A_Analyzer extends A_Test {
	protected DataAnalyzer analyzer;
	//protected String fileName = "E:\\9000-test\\100-project\\10-珊瑚湾\\10-android\\android_test.apk";
	public InputStream getInputStream() {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("android_test.apk");
			//return new FileInputStream(new File(fileName));
		} catch (Exception e) {
			logger.error(e.getMessage(), e.getCause());
		}finally {
			//logger.debug("{}","getInputStream success");
		}
		try {
			if(inputStream == null) {
				inputStream = new FileInputStream(File.createTempFile("temp", "apk"));
			}	
		} catch (Exception e) {
			logger.error("{}",e.getMessage(), e.getCause());
		}
		return inputStream;
		
	}
}
