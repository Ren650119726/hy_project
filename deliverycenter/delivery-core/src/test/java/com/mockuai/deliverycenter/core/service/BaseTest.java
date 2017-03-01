package com.mockuai.deliverycenter.core.service;

import java.io.IOException;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 类BaseTest.java的实现描述：单元测试基类
 * 
 * @author dong.dong 26, 2012 5:44:44 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml" })
public class BaseTest {

	@Before
	public void init() throws IOException {
		// DOMConfigurator.configure(ClassLoaderUtil.getResource("log4j.xml"));
	}

}
