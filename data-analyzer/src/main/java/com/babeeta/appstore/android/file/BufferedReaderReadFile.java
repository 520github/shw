/**
 * 
 */
package com.babeeta.appstore.android.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

/**
 * @author xuehui.miao
 *
 */
@Service
public class BufferedReaderReadFile implements ReadFile {

	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.file.ReadFile#readFile(java.io.InputStream)
	 */
	@Override
	public String readFile(InputStream inputStream) throws Exception {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(inputStream));
		StringBuffer result = new StringBuffer();
		String line = null;
		while((line = br.readLine()) != null) {
			result.append(line + "\n");
		}
		return result.toString();
	}

}
