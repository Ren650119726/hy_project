package com.mockuai.marketingcenter.core;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Created by edgar.zr on 8/4/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public abstract class BaseTest extends AbstractTestNGSpringContextTests {

}