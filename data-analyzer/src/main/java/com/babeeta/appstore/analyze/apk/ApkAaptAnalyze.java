/**
 * 
 */
package com.babeeta.appstore.analyze.apk;

import java.io.InputStream;
import java.util.Map;

/**
 * 解析,APK包通过执行aapt命令得到的输出流内容
 * @author xuehui.miao
 *
 */
public interface ApkAaptAnalyze {
	public Map<String,Object> apkAaptAnalyze(InputStream inputSteam) throws Exception;
}
