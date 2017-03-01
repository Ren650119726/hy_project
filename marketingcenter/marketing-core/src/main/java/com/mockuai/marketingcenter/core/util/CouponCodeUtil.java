package com.mockuai.marketingcenter.core.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 三十六进制计数器
 */
public class CouponCodeUtil {

    private static final Integer LENGTH_OF_COUPON_CODE = 8;
    private static final Character DEFAULT_CODE = 'X';
    private static final Long COUPON_CODE_MOD = 46656L;

    /**
     * 生成长度为 7 的 32 进制数
     *
     * @return
     */
    public static String genCode() {
        return new BigInteger(130, new SecureRandom()).toString(32).toUpperCase().substring(0, 7);
    }
}