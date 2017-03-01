package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DistributionManager;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/6/16.
 */
public class DistributionManagerImpl implements DistributionManager {
    private static final Logger log = LoggerFactory.getLogger(DistributionManagerImpl.class);

    @Resource
    private DistributionClient distributionClient;

    @Override
    public List<DistShopDTO> queryDistShop(List<Long> distributorIdList, String appKey) throws TradeException {
        try {
            DistShopQTO distShopQTO = new DistShopQTO();
            distShopQTO.setSellerIds(distributorIdList);
            Response<List<DistShopDTO>> response = distributionClient.queryShop(distShopQTO, appKey);
            if (response.isSuccess() == false) {
                log.error("error to queryDistShop, distributorIdList:{}, code:{}, message:{}",
                        JsonUtil.toJson(distributorIdList), response.getCode(), response.getMessage());
                return Collections.EMPTY_LIST;
            }else {
                return response.getModule();
            }
        } catch (Exception e) {
            log.error("error to queryDistShop, distributorIdList:{}", JsonUtil.toJson(distributorIdList), e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }


    @Override
    public List<SellerDTO> queryDistSeller(List<Long> distributorIdList, String appKey) throws TradeException {
        try {
            SellerQTO distShopQTO = new SellerQTO();
            distShopQTO.setIds(distributorIdList);
            Response<List<SellerDTO>> response = distributionClient.querySeller(distShopQTO, appKey);
            if (response.isSuccess() == false) {
                log.error("error to querySeller, distributorIdList:{}, code:{}, message:{}",
                        JsonUtil.toJson(distributorIdList), response.getCode(), response.getMessage());
                return Collections.EMPTY_LIST;
            }else {
                return response.getModule();
            }
        } catch (Exception e) {
            log.error("error to querySeller, distributorIdList:{}", JsonUtil.toJson(distributorIdList), e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }
    }

    @Override
    public DistShopDTO getDistShop(Long distributorId, String appKey) throws TradeException {
        try {
            Response<DistShopDTO> response = distributionClient.getShopBySellerId(distributorId, appKey);
            if (response.isSuccess() == false) {
                log.error("error to getDistShop, distributorId:{}, code:{}, message:{}",
                        distributorId, response.getCode(), response.getMessage());
                throw new TradeException(ResponseCode.BIZ_E_DIST_SHOP_NOT_EXIST);
            }else {
                return response.getModule();
            }
        } catch (Exception e) {
            log.error("error to queryDistShop, distributorId:{}", distributorId, e);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

    }



}
