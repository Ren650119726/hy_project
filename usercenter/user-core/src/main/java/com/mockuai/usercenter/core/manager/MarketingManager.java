package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by duke on 15/11/18.
 */
public interface MarketingManager {
    /**
     * 发放积分
     * @param creatorId 发放者
     * @param wealthType 财富类型
     * @param appKey
     * @param grantAmount 发放总量
     * @param receiverId 接收者
     * @param sourceType 发放来源
     * */
    public void grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType, Long grantAmount,
                                      Long receiverId, String appKey) throws UserException;
}
