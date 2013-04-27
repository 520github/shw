/**
 * 
 */
package com.babeeta.appstore.android.process;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author xuehui.miao
 *
 */
@Service
public class CmdProcess implements MyProcess {
	private static final Logger logger = LoggerFactory.getLogger(CmdProcess.class);
	private InputStream inputStream;
	private InputStream errorStream;
	private Process proc;
	/* (non-Javadoc)
	 * @see com.babeeta.appstore.android.process.Process#executeCommand()
	 */
	@Override
	public void executeCommand(String cmd) throws Exception {
		try {
			Runtime run = Runtime.getRuntime();
			proc = run.exec(cmd);
			inputStream = proc.getInputStream();
			errorStream = proc.getErrorStream();
			
//			int result = proc.waitFor();
//			if(result == 0) {
//				logger.debug("执行命令["+cmd+"]成功!");
//			}
//			else {
//				logger.error("执行命令["+cmd+"]失败!");
//			}
		} catch (Exception e) {
			throw new Exception("execute command["+cmd+"]throws exception:");
		}finally {
			
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public InputStream getErrorStream() {
		return errorStream;
	}
	
	public void destroyProcess() {
		try {
			if(proc!=null)proc.destroy();
		} catch (Exception e) {
			logger.error("destroyProcess exception:{}", e.getMessage(), e);
		}
		
	}

}
