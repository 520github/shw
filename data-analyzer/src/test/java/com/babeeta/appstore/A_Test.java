/**
 * 
 */
package com.babeeta.appstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.After;

/**
 * 测试 基类
 * @author xuehui.miao
 *
 */
public abstract class A_Test {
	protected static Logger logger = null;
	protected static ApplicationContext ctx =null;
	@Before
	public void setUp() {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("{}", "setUp");
		
		try {
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			logger.error("init ApplicationContext throws exception {}:", e.getMessage(), e);
		}
	}
	
	@After
	public void tearDown() {
		logger.info("{}", "tearDown");
	}
}
