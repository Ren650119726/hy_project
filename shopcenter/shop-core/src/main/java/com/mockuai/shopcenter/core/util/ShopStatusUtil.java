package com.mockuai.shopcenter.core.util;

import com.mockuai.shopcenter.constant.ShopStatusEnum;

/**
 * Created by luliang on 15/7/27.
 */
public class ShopStatusUtil {

    public static String getStatusName(Integer status) {
        if(status == null) {
            return null;
        }
        int val = status.intValue();
        if(val == ShopStatusEnum.PENDING_AUDIT.getStatus()) {
            return ShopStatusEnum.PENDING_AUDIT.getStatusName();
        }
        if(val == ShopStatusEnum.AUDIT_SUCCESS.getStatus()) {
            return ShopStatusEnum.AUDIT_SUCCESS.getStatusName();
        }
        if(val == ShopStatusEnum.AUDIT_FAIL.getStatus()) {
            return ShopStatusEnum.AUDIT_FAIL.getStatusName();
        }
        if(val == ShopStatusEnum.NORMAL_BUSSINESS.getStatus()) {
            return ShopStatusEnum.NORMAL_BUSSINESS.getStatusName();
        }
        if(val == ShopStatusEnum.TEMP_FREEZEN.getStatus()) {
            return ShopStatusEnum.TEMP_FREEZEN.getStatusName();
        }
        if(val == ShopStatusEnum.CLOSE.getStatus()) {
            return ShopStatusEnum.CLOSE.getStatusName();
        }
        return null;
    }
}
