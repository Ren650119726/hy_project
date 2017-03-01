package com.mockuai.virtualwealthcenter.core.time;

import com.mockuai.virtualwealthcenter.core.BaseTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/12/2016.
 */
public class TimeTest extends BaseTest {

    @Test
    public void test() {
        try {
            Thread.sleep(100000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}