/**
 * 
 */
package com.babeeta.appstore.job;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.A_Test;
import com.babeeta.appstore.job.play.GooglePlayJob;

/**
 * @author xuehui.miao
 *
 */
public class GooglePlayJobTest extends A_Test {
	private GooglePlayJob job = new GooglePlayJob();
	public GooglePlayJobTest() {
		this.setUp();
		job = (GooglePlayJob)ctx.getBean("googlePlayJob");
		String name = "WEB-20120404174200277-00023-2897~lab.shanhubay.com~8443.arc.gz";
		String path = "E:\\9000-test\\300-heritrix\\jobs\\play.google.com\\latest\\arcs\\"+name;
		path = "E:\\9000-test\\300-heritrix\\jobs\\play.google.com\\latest\\";
		job.setArchiveDirectory(path);
	}
	
	@Test
	public void testEoeJob() {
		//job.start();
	}
	
//	public static void main(String[] args) {
//		try {
//			GooglePlayJobTest test = new GooglePlayJobTest();
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
