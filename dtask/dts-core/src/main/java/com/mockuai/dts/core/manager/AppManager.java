package com.mockuai.dts.core.manager;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.dts.core.exception.DtsException;

/**
 * Created by zengzhangqiang on 8/25/15.
 */
public interface AppManager {

    public AppInfoDTO getAppInfo(String appKey) throws DtsException;

}
