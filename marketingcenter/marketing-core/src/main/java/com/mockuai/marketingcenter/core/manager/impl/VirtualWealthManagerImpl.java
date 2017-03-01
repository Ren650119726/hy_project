package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.VirtualWealthManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class VirtualWealthManagerImpl implements VirtualWealthManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VirtualWealthManagerImpl.class);

    @Autowired
    private VirtualWealthClient virtualWealthClient;

    @Override
    public Boolean grantVirtualWealth(Long creatorId,
                                      Integer wealthType,
                                      Integer sourceType,
                                      Long grantAmount,
                                      Long receiverId,
                                      Long orderId,
                                      String appKey) throws MarketingException {
        try {
            Response<Void> response =
                    virtualWealthClient.grantVirtualWealth(creatorId, wealthType, sourceType, grantAmount, receiverId, orderId, appKey);
            if (response.isSuccess()) {
                return true;
            }
            LOGGER.error("error to grantVirtualWealth, creatorId : {}, wealthType : {}, sourceType : {}, grantAmount : {}, receiverId : {}, errCode : {}, errMsg : {}, appKey :{}",
                    creatorId, wealthType, sourceType, grantAmount, receiverId, response.getResCode(), response.getMessage(), appKey);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to grantVirtualWealth, creatorId : {}, wealthType : {}, sourceType : {}, grantAmount : {}, receiverId : {}, appKey :{}",
                    creatorId, wealthType, sourceType, grantAmount, receiverId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<VirtualWealthDTO> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO, String appKey) throws MarketingException {
        try {
            Response<List<VirtualWealthDTO>> response = virtualWealthClient.queryVirtualWealth(virtualWealthQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryVirtualWealth, virtualWealthQTO : {}, errCode : {}, errMsg : {}, appKey :{}",
                    JsonUtil.toJson(virtualWealthQTO), response.getResCode(), response.getMessage(), appKey);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryVirtualWealth, virtualWealthQTO : {}, appKey :{}",
                    JsonUtil.toJson(virtualWealthQTO), appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}