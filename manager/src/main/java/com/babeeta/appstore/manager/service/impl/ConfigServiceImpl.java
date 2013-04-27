package com.babeeta.appstore.manager.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.babeeta.appstore.manager.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigServiceImpl.class);

	@Override
	public void moveTempToJson(String tempPath, String jsonPath) {
		BufferedReader in = null;
		BufferedWriter out = null;
		StringBuffer sb = new StringBuffer();
		try {
			File f = new File(tempPath);
			if (f.exists()) {
				in = new BufferedReader(new InputStreamReader(
						new FileInputStream(f), "UTF-8"));
				String str = "";
				while ((str = in.readLine()) != null) {
					sb.append(str);
				}
				in.close();
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(new File(jsonPath)), "UTF-8"));
				out.write(sb.toString());
				f.delete();// 删除临时文件
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
