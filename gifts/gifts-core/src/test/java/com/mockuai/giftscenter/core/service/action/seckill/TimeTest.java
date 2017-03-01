package com.mockuai.giftscenter.core.service.action.seckill;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.mockuai.giftscenter.core.BaseTest;

/**
 * Created by edgar.zr on 12/15/15.
 */
public class TimeTest extends BaseTest {

    @Test
    public void test() {
        try {
            TimeUnit.DAYS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}