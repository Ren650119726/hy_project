package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.customer.client.CustomerClient;
import com.mockuai.customer.common.api.Response;
import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.CustomerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by edgar.zr on 3/04/2016.
 */
public class CustomerManagerImpl implements CustomerManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerManagerImpl.class);

    @Autowired
    private CustomerClient customerClient;

    @Override
    public MemberDTO getMemberByUserId(Long userId, String appKey) throws MarketingException {
        try {
            Response<MemberDTO> response = customerClient.getMemberByUserId(userId, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to getMemberByUserId, userId : {}, appKey :{}, errCode : {}, errMsg : {}",
                    userId, appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getMemberByUserId, userId : {}, appKey :{}", userId, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
}