package com.mockuai.rainbowcenter;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.common.api.Response;
import org.junit.Assert;

/**
 * Created by lizg on 16/5/24.
 */
public class UnitTestUtils {
    public static void assertAndPrint(Response response) {
        Assert.assertTrue(response.isSuccess());
        System.out.println("result:{}"+JsonUtil.toJson(response.getModule()));
    }
}
