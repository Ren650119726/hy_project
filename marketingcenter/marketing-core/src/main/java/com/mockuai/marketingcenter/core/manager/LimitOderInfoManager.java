package com.mockuai.marketingcenter.core.manager;

import com.mockuai.marketingcenter.common.domain.dto.LimitOderInfoDTO;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

import java.util.Date;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/3.
 */
public interface LimitOderInfoManager {
    LimitOderInfoDO  queryLimitOderInfos(LimitOderInfoDO limitOderInfoDO)throws MarketingException;
    List<LimitOderInfoDO>  queryInfoDtoById( Long activityId)throws  MarketingException;

    Boolean addLimitOderInfo(LimitOderInfoDO limitOderInfoDO)throws MarketingException;
}
