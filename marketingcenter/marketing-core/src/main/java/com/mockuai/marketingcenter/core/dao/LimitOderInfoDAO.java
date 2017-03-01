package com.mockuai.marketingcenter.core.dao;

import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;

import java.util.Date;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/3.
 */
public interface LimitOderInfoDAO {
    List<LimitOderInfoDO> queryInfoDtoById(Long activityId);

    LimitOderInfoDO queryLimitOderInfos (LimitOderInfoDO limitOderInfoDO);

    Long addLimitOderInfo(LimitOderInfoDO limitOderInfoDO);
}
