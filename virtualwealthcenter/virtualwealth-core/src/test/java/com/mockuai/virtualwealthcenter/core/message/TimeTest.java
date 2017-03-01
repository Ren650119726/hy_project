package com.mockuai.virtualwealthcenter.core.message;

import com.mockuai.virtualwealthcenter.core.BaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by edgar.zr on 12/23/15.
 */
public class TimeTest extends BaseTest {

    public static void main(String[] args) {
        String chinese = "优惠码优惠码优惠码优惠码优惠码优惠码优惠码优惠码惠码优惠码优惠x码";
        System.out.println(chinese.length());
        System.out.println(chinese.substring(0, 32).length());

    }

    @Test
    public void test() {
        try {
            TimeUnit.DAYS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLenghtOfChinese() {
    }
}
