package com.mockuai.marketingcenter.core.service.action;

import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 6/28/2016.
 */
public class CommonTest {

	@Test
	public void test() {
		StopWatch watch = new StopWatch();
		watch.start();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println(watch.getTime());

		watch.reset();
		watch.start();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.err.println(watch.getTime());
	}
}