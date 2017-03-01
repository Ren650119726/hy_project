package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.MarketingManager;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by duke on 15/11/18.
 */
@Service
public class MarketingManagerImpl implements MarketingManager {
    @Resource
    VirtualWealthClient virtualWealthClient;

    @Override
    public void grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType, Long grantAmount,
                                   Long receiverId, String appKey) throws UserException {
        Response<Void> response = virtualWealthClient.grantVirtualWealth(
                creatorId, wealthType, sourceType, grantAmount, receiverId, appKey);
        if (!response.isSuccess()) {
            throw new UserException(ResponseCode.SYS_E_SERVICE_EXCEPTION, response.getMessage());
        }
    }
}
