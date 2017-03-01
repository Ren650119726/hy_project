package com.mockuai.appcenter.common.util;

import com.mockuai.appcenter.common.constant.AppTypeEnum;

/**
 * Created by zengzhangqiang on 9/29/15.
 */
public class BizUtil {
    public static String genAppName(String bizName, AppTypeEnum appTypeEnum){
        return bizName+appTypeEnum.getNameSuffix();
    }
}
