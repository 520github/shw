/**
 * 
 */
package com.babeeta.appstore.analyze.apk;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.android.file.ReadFile;

/**
 * 解析,APK包通过执行aapt badging命令得到的输出流内容
 * @author xuehui.miao
 *
 */
@Service
public class ApkAaptBadgingAnalyze implements ApkAaptAnalyze {
	private static final Logger logger = LoggerFactory.getLogger(ApkAaptBadgingAnalyze.class);
	/** 存储解析后的结果 */
	private Map<String,Object> resultMap;
	private ReadFile readFile;
	/** 包信息标签 */
	private static String packageTag = "package:";
	/** 应用信息标签 */
	private static String applicationTag = "application:";
	/** 用户权限信息标签 */
	private static String usesPermissionTag = "uses-permission:";
	
	
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.analyze.apk.ApkAaptAnalyze#apkAaptAnalyze(java.io.InputStream)
	 */
	@Override
	public Map<String,Object> apkAaptAnalyze(InputStream inputStream) throws Exception {
		try {
			String contents = readFile.readFile(inputStream);
			resultMap = new HashMap<String,Object>();
			
			String contentsSplit[] = contents.split("\n");
			for (int i = 0; i < contentsSplit.length; i++) {
				String content = contentsSplit[i];
				this.getUsedContent(content);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			this.destroy();
		}
		
		return resultMap;
	}
	
	/**
	 * 获取需要被引用的相关内容
	 * @param content 待分析的内容
	 */
	private void getUsedContent(String content) {
		//获取包名、版本号等信息
		if(content.toLowerCase().indexOf(packageTag) > -1) {
			this.getPackageContent(content);
		}
		//获取应用名称、图片等信息
		else if(content.toLowerCase().indexOf(applicationTag) > -1) {
			this.getApplicationContent(content);
		}
		//获取用户权限相关信息
		else if(content.toLowerCase().indexOf(usesPermissionTag) > -1) {
			this.getUsesPermissionContent(content);
		}
	}
	
	/**
	 * 获取包名、版本号等信息
	 * @param content 待分析的内容
	 */
	private void getPackageContent(String content) {
		this.getContent(packageTag, content);
	}
	
	/**
	 * 获取应用名称、图片等信息
	 * @param content 待分析的内容
	 */
	private void getApplicationContent(String content) {
		this.getContent(applicationTag, content);
	}
	
	/**
	 * 获取用户权限相关信息
	 * @param content 待分析的内容
	 */
	private void getUsesPermissionContent(String content) {
		content = content.substring(usesPermissionTag.length()).trim();
		content = content.replaceAll("'", "").trim();
		String name = usesPermissionTag.substring(0, usesPermissionTag.length()-1);
		List<String> valueList = new ArrayList<String>();
		if(resultMap.containsKey(name)) {
			valueList = (List<String>)resultMap.get(name);
		}
		valueList.add(content);
		logger.debug("permission name[{}] value[{}]", name, content);
		resultMap.put(name, valueList);
	}
	
	/**
	 * 解析出name:key的情况
	 * @param tag 标签名
	 * @param content 内容
	 */
	private void getContent(String tag,String content) {
		content = content.substring(tag.length()).trim();
		String contentSplit[] = content.split(" ");
		for (int i = 0; i < contentSplit.length; i++) {
			String split = contentSplit[i].trim();
			if(split.indexOf("=") ==-1)continue;
			String name = split.substring(0,split.indexOf("=")).trim();
			String value = split.substring(split.indexOf("=")+1).replaceAll("'","").trim();
			resultMap.put(name, value);
		}
	}

	@Autowired
	@Qualifier("bufferedReaderReadFile")
	@Required
	public void setReadFile(ReadFile readFile) {
		this.readFile = readFile;
	}
	
	protected void destroy() {
		//this.readFile = null;
	}
	
}
