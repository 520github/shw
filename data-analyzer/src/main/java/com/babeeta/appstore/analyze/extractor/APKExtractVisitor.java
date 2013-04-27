/**
 * 
 */
package com.babeeta.appstore.analyze.extractor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import com.babeeta.appstore.analyze.apk.ApkAaptAnalyze;
import com.babeeta.appstore.android.file.BufferedReaderReadFile;
import com.babeeta.appstore.android.process.MyProcess;
import com.babeeta.appstore.entity.ApplicationMarketDetail.Permission;

/**
 * APK包解析逻辑
 * @author xuehui.miao
 *
 */
public abstract class APKExtractVisitor<T>  {
	private static Logger logger = LoggerFactory.getLogger(APKExtractVisitor.class);
	/** apk文件路径 */
	private String apkFilePath;
	/** 处理命令的进程*/
	private MyProcess process;
	/** APK包aapt命令输出结果的解析 */
	private ApkAaptAnalyze apkAaptAnalyze;
	/** 名称匹配,Entity与apk中的名称匹配 */
	private Map<String,String> nameMatchMap = new HashMap<String,String>();
	
	public abstract T getEntity() throws Exception;
	
	/**
	 * 从APK包中获取Entity需要的内容
	 */
	protected void getContentFromAPK(Object entity) throws Exception {
		try {	
			//执行aapt命令解析APK包
			String classPath = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
			String cmd = classPath + "aapt dump badging " + apkFilePath;//
			logger.info("execute aapt command{}", cmd);
			process.executeCommand(cmd);
			
			//对输出流进行解析
			Map<String,Object> analyzeMap = 
				apkAaptAnalyze.apkAaptAnalyze(process.getInputStream());
			
			//执行aapt命令失败,退出
			if(!this.isErrorProcess()) {
				return ;
			}
			
			//把解析的结果填充到Entity中
			this.setAnalyzeMap2Entity(entity, analyzeMap);		
			
		} catch (Exception e) {
			throw e;
		}finally {
			//删除临时文件
			this.deleteTempFile();
			this.destroy();
		}
	}
	
	/**
	 *设置APK解析出来的名称与entity属性名对应
	 */
	protected void setNameMacthMap() {
		nameMatchMap.put("name", "packageName");
		nameMatchMap.put("uses-permission", "permissions");
	}
	
	/**
	 * 把解析出来的内容填充到entity中
	 * @param entity
	 * @param analyzeMap
	 */
	private void setAnalyzeMap2Entity(Object entity, Map<String,Object> analyzeMap) {
		this.setNameMacthMap();
		Iterator<String> it = analyzeMap.keySet().iterator();
		while(it.hasNext()) {
			String name = it.next();
			Object value = analyzeMap.get(name);
			if(nameMatchMap.containsKey(name)) {
				name = nameMatchMap.get(name);
			}
			try {
				BeanUtils.copyProperty(entity, name, this.transValue(name, value));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 对值进行转换处理(主要是针对用户权限许可信息)
	 * @param name
	 * @param value
	 * @return
	 */
	private Object transValue(String name,Object value) {
		if(value == null)return value;
		if(!"permissions".equalsIgnoreCase(name))return value;
		
		List<Permission> permissionList = new ArrayList<Permission>();
		try {
			String permissionId = "uses-permission";
			List<String> valueList = (List<String>)value;
			for (int i = 0; i < valueList.size(); i++) {
				Permission per = new Permission();
				per.setId(permissionId);
				per.setText(valueList.get(i));
				permissionList.add(per);
			}
		} catch (Exception e) {
			logger.error("getPermission from apk exception {}", e.getMessage(), e);
		}
		return permissionList;
	}
	
	/**
	 * 删除临时文件
	 */
	private void deleteTempFile() {
		try {
			File tempFile = new File(apkFilePath);
			tempFile.delete();
		} catch (Exception e) {
			logger.error("delete file[{}] exception:", apkFilePath, e);
		}
		
	}
	
	/**
	 * 判断Process进程执行是否有错误
	 * @return
	 * @throws Exception
	 */
	private boolean isErrorProcess() throws Exception {
		BufferedReaderReadFile brf = new BufferedReaderReadFile();
		String error = brf.readFile(process.getErrorStream());	
		if(StringUtils.isNotBlank(error)) {
			if(error.indexOf("no version information available")>-1 && error.indexOf("AndroidManifest.xml") ==-1) {
				return true;
			}
			throw new Exception("analyzer file["+apkFilePath+"] throws exception:\n" + error);
		}		
		return true;
	}
	
	public String getApkFilePath() {
		return apkFilePath;
	}

	public void setApkFilePath(String apkFilePath) {
		this.apkFilePath = apkFilePath;
	}
	
	@Autowired
	@Qualifier("cmdProcess")
	@Required
	public void setProcess(MyProcess process) {
		this.process = process;
	}
	
	@Autowired
	@Qualifier("apkAaptBadgingAnalyze")
	@Required
	public void setApkAaptAnalyze(ApkAaptAnalyze apkAaptAnalyze) {
		this.apkAaptAnalyze = apkAaptAnalyze;
	}
	
	protected void destroy() {
		//this.apkAaptAnalyze = null;
		//this.apkFilePath = null;
		//this.nameMatchMap = null;
		if(process == null)return;
		try {
			if(this.process!=null) {
				this.process.destroyProcess();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(this.process.getInputStream() !=null)this.process.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if(this.process.getErrorStream()!=null)this.process.getErrorStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//this.process = null;
	}
}
