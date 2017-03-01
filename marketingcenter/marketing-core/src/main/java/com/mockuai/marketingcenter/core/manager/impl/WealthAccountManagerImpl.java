package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class WealthAccountManagerImpl implements WealthAccountManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(WealthAccountManagerImpl.class);

    @Autowired
    private VirtualWealthClient virtualWealthClient;

    @Override
    public List<WealthAccountDTO> queryWealthAccount(Long userId, Integer wealthType, String appKey) throws MarketingException {
        try {
            Response<List<WealthAccountDTO>> response =
                    virtualWealthClient.queryWealthAccount(userId, wealthType, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }
            LOGGER.error("error to queryWealthAccount, userId : {}, wealthType : {}, errCode : {}, errMsg : {}, appKey :{}",
                    userId, wealthType, response.getResCode(), response.getMessage(), appKey);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryWealthAccount, userId : {} wealthType : {}, appKey :{}",
                    userId, wealthType, appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public void fillUpWealthAccountsForSettlement(Long userId, SettlementInfo settlementInfo, String appKey) throws MarketingException {
        try {
            long exchangeAmount = 0L;

            List<WealthAccountDTO> wealthAccountDTOs = queryWealthAccount(userId, null, appKey);

            //过滤掉账户余额为0的虚拟账户
            wealthAccountDTOs = filterWealthAccountByBalance(wealthAccountDTOs);

            for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
                exchangeAmount += wealthAccountDTO.getAmount() * wealthAccountDTO.getExchangeRate();
            }

            settlementInfo.setExchangeAmount(exchangeAmount);
            settlementInfo.setWealthAccountList(wealthAccountDTOs);
        } catch (MarketingException e) {
            LOGGER.error("error to fillUpWealthAccountsForSettlement, userId : {}, appKey : {}", userId, appKey, e);
            throw e;
        }
    }

    private List<WealthAccountDTO> filterWealthAccountByBalance(List<WealthAccountDTO> wealthAccountDTOs) {
        if (wealthAccountDTOs == null) {
            return null;
        }

        List<WealthAccountDTO> availableWealthAccount = new ArrayList<>();
        for (WealthAccountDTO wealthAccountDTO : wealthAccountDTOs) {
            if (wealthAccountDTO.getAmount().longValue() > 0) {
                availableWealthAccount.add(wealthAccountDTO);
            }
        }

        return availableWealthAccount;
    }
}