package com.babeeta.appstore.android.crawler.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.analyze.extractor.APKExtractVisitor;
import com.babeeta.appstore.storage.LocalDiskSimpleStorageService;
import com.babeeta.appstore.storage.SimpleStorageService;

/**
 * APK文件解析抽象类
 * 
 * @author xuehui.miao
 * 
 */
@Service
public abstract class APKDataAnalyzer<T> implements DataAnalyzer {
	protected SimpleStorageService ssService;
	protected APKExtractVisitor<T> visitor;
	public static final String APKNAME = "apk";
	protected String uuid;

	/**
	 * 创建临时文件
	 * 
	 * @param inputStream
	 * @return String
	 * @throws Exception
	 */
	private String createTempFile(InputStream inputStream) throws Exception {
		FileOutputStream writeFile = null;
		String path = "";
		File tempFile = null;
		try {
			String fileId = UUID.randomUUID().toString();
			tempFile = File.createTempFile(fileId, "." + APKNAME);
			writeFile = new FileOutputStream(tempFile);
			byte[] buf = new byte[10240];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				writeFile.write(buf, 0, len);
			}
			path = tempFile.getAbsolutePath();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				writeFile.close();
				writeFile = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			try {
				inputStream.close();
				inputStream = null;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			tempFile = null;
		}

		return path;
	}

	/**
	 * 装入具体的visitor
	 * 
	 * @param html
	 * @throws Exception
	 */
	private void loadVisitor(String apkFilePath) throws Exception {
		visitor.setApkFilePath(apkFilePath);
	}

	protected void destroy() {
		this.ssService = null;
	}

	public SimpleStorageService getSsService() {
		return ssService;
	}

	public APKExtractVisitor<T> getVisitor() {
		return visitor;
	}

	@Override
	public void process(InputStream inputStream, Context context)
			throws Exception {
		try {
			if (ssService == null) {
				ssService = new LocalDiskSimpleStorageService();
			}

			// 把APK包保存到磁盘中
			this.saveAPK2Disk(inputStream);

			// 创建APK临时文件
			String path = this.createTempFile(ssService.get(APKNAME, uuid));

			// 通过visitor调用aapt解析该APK临时文件
			this.loadVisitor(path);

		} catch (Exception e) {
			throw e;
		} finally {
			// this.destroy();
		}
	}

	public void saveAPK2Disk(InputStream inputStream) throws Exception {
		uuid = ssService.put(APKNAME, inputStream);
	}

	@Autowired
	@Required
	public void setSsService(SimpleStorageService ssService) {
		this.ssService = ssService;
	}

	public void setVisitor(APKExtractVisitor<T> visitor) {
		this.visitor = visitor;
	}

}
