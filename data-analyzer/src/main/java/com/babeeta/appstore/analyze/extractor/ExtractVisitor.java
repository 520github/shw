package com.babeeta.appstore.analyze.extractor;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.visitors.NodeVisitor;

public abstract class ExtractVisitor<T> extends NodeVisitor {
	public abstract T getEntity();
	
	/**
	 * 把字符串转换为float
	 * @param str
	 * @return
	 */
	protected float changeString2float(String str) {
		if(StringUtils.isBlank(str))return 0;
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String t = str.substring(i,i+1);
			if(t.equals(","))t = ".";
			if(t.equals(".")) {
				temp.append(t);
				continue;
			}
			try {
				Integer.parseInt(t);
				temp.append(t);
			} catch (Exception e) {
			}
		}
		if(temp.toString().startsWith(".")) {
			temp.deleteCharAt(0);
		}
		if(temp.toString().endsWith(".")) {
			temp.deleteCharAt(temp.length()-1);
		}
		return Float.parseFloat(temp.toString());
	}
	
	/**
	 * 把字符串转换为int
	 * @param str
	 * @return
	 */
	protected int changeString2int(String str) {
		if(StringUtils.isBlank(str))return 0;
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String t = str.substring(i,i+1);
			try {
				Integer.parseInt(t);
				temp.append(t);
			} catch (Exception e) {
			}
		}
		return Integer.parseInt(temp.toString());
	}
}
