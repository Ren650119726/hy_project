package com.mockuai.itemcenter.common.util;

import com.mockuai.itemcenter.common.constant.BizTagEnum;

/**
 * Created by yindingyu on 16/4/6.
 */
public class BizTagUtil {

    public static final int EMPTY_TAG = 0x0;

    public static int enableTag(BizTagEnum bizTagEnum, int tag) {

        return tag | (1 << (bizTagEnum.getBit() - 1));
    }

    public static int disableTag(BizTagEnum bizTagEnum, int tag) {

        return tag & ~(1 << (bizTagEnum.getBit() - 1));
    }

    public static boolean checkTag(BizTagEnum bizTagEnum, int tag) {

        return ((tag >> (bizTagEnum.getBit() - 1) & 1) == 1);
    }

    public static int emptyTag() {
        return EMPTY_TAG;
    }
}
