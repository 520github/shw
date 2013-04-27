/**
 * 
 */
package com.babeeta.appstore.android.process;

import java.io.InputStream;

/**
 * @author xuehui.miao
 *
 */
public interface MyProcess {
	public void executeCommand(String cmd) throws Exception;
	
	public InputStream getInputStream();
	
	public InputStream getErrorStream();
	
	public void destroyProcess();
}
