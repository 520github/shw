/**
 * 
 */
package com.babeeta.appstore.job;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.A_Test;
import com.babeeta.appstore.job.gfan.GFanJob;

/**
 * @author xuehui.miao
 *
 */
public class GFanJobTest extends A_Test {
	private GFanJob job = new GFanJob();
	public GFanJobTest() {
		this.setUp();
		job = (GFanJob)ctx.getBean("gFanJob");
		String name = "WEB-20120407120318838-00098-9255~lab.shanhubay.com~8443.arc.gz";
		String path = "E:\\9000-test\\300-heritrix\\jobs\\apk.gfan.com\\latest\\arcs\\"+name;
		path = "E:\\9000-test\\300-heritrix\\jobs\\apk.gfan.com\\latest\\";
		job.setArchiveDirectory(path);
	}
	
	@Test
	public void testEoeJob() {
		//job.start();
	}
	
//	public static void main(String[] args) {
//		try {
//			GFanJobTest test = new GFanJobTest();
//			if(args != null && args.length >0) {
//				if(logger == null) {
//					logger = LoggerFactory.getLogger(EoeJobTest.class);
//				}
//				logger.error("{}", "go start.....");
//				test.job.setArchiveDirectory(args[0]);
//				logger.error("home path:{}", args[0]);
//			}
//			test.job.start();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
