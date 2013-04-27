/**
 * 
 */
package com.babeeta.appstore.job;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.babeeta.appstore.A_Test;
import com.babeeta.appstore.job.eoe.EoeMarketJob;

/**
 * @author xuehui.miao
 *
 */
public class EoeJobTest extends A_Test {
	private EoeMarketJob job ;
	public EoeJobTest() {
		this.setUp();
		job = (EoeMarketJob)ctx.getBean("eoeMarketJob");
		String name = "WEB-20120331053546803-00000-2897~lab.shanhubay.com~8443.arc.gz";
		String path = "E:\\9000-test\\300-heritrix\\jobs\\www.eoemarket.com\\latest\\arcs\\"+name;
		path = "E:\\9000-test\\300-heritrix\\jobs\\www.eoemarket.com\\latest\\";
		//path = "E:\\0010-work\\100-project\\06-opensource\\heritrix3\\engine\\jobs\\www.eoemarket.com\\20120428033244\\";
		job.setArchiveDirectory(path);
	}
	
	@Test
	public void testEoeJob() {
		//job.start();
	}
	
//	public static void main(String[] args) {
//		try {
//			EoeJobTest test = new EoeJobTest();
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
