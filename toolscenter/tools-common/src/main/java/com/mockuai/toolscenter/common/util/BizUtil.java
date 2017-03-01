package com.mockuai.toolscenter.common.util;

import com.mockuai.toolscenter.common.constant.AppTypeEnum;

/**
 * Created by zengzhangqiang on 9/29/15.
 */
public class BizUtil {
    public static String genAppName(String bizName, AppTypeEnum appTypeEnum){
        return bizName+appTypeEnum.getNameSuffix();
    }
}
