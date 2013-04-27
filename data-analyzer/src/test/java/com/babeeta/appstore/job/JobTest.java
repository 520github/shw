/**
 * 
 */
package com.babeeta.appstore.job;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.babeeta.appstore.A_Test;

/**
 * @author hp-pro
 *
 */
public class JobTest extends A_Test {
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("{}", "setUp");
		
		try {
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			logger.error("init ApplicationContext throws exception {}:", e.getMessage(), e);
		}
	}
	
	@Test
	public void testJob() {
		
	}
}
