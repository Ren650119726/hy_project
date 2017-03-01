package com.mockuai.marketingcenter.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.client.GainsSetClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.DistributorManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;

/**
 * Created by edgar.zr on 5/23/2016.
 */
public class DistributorManagerImpl implements DistributorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributorManagerImpl.class);

    @Autowired
    private DistributionClient distributionClient;
    @Resource
    private GainsSetClient gainsSetClient;

    @Override
    public List<DistShopDTO> queryShop(DistShopQTO distShopQTO, String appKey) throws MarketingException {
        try {
            Response<List<DistShopDTO>> response = distributionClient.queryShop(distShopQTO, appKey);
            if (response.isSuccess()) {
                return response.getModule();
            }

            LOGGER.error("error to queryShop, distShopQTO : {}, appKey : {}, code : {}, msg : {}",
                    JsonUtil.toJson(distShopQTO), appKey, response.getCode(), response.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, response.getMessage());
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to queryShop, distShopQTO : {}, appKey : {}", JsonUtil.toJson(distShopQTO), appKey, e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }
    

    @Override
    public GainsSetDTO  getGainsSet(String appKey) throws MarketingException {
        Response<GainsSetDTO> response;
        try {
            response =gainsSetClient.getGainsSet(appKey);
            if(! response.isSuccess()){
                throw new MarketingException(ResponseCode.SYS_E_REMOTE_CALL_ERROR,response.getMessage());
            }
            return response.getModule();
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SYS_E_REMOTE_CALL_ERROR,"未能查询收益信息");
        }
    }
    
    public List<ItemSkuDistPlanDTO> getItemSkuDistPlanList(Long itemId , String appKey) throws MarketingException {
        Response<List<ItemSkuDistPlanDTO>> itemSkuDistPlanResponse ;
        try {
            itemSkuDistPlanResponse = distributionClient.getItemSkuDistPlanByItemId(itemId,appKey);

        }catch (Exception e){
            LOGGER.error("itemId:{} , 未能查询到商品分佣信息 message:{}",itemId.intValue(),e.getMessage());
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,
               String.format("%d , 未能查询到商品分佣信息 message:%s",itemId.intValue(),e.getMessage())     );
        }
        if(itemSkuDistPlanResponse.isSuccess()){
            return itemSkuDistPlanResponse.getModule();
        }else {
            LOGGER.error("itemId:{} ,未能查询到商品分佣信息 appKey:{}code:{},msg:{}",
                    itemId.intValue(),appKey,itemSkuDistPlanResponse.getCode(),itemSkuDistPlanResponse.getMessage()  );

            throw new MarketingException(ResponseCode.SYS_E_REMOTE_CALL_ERROR,
                    String.format("%d , 未能查询到商品分佣信息 appKey:%s,code:%s,msg:%s",itemId.intValue(),
                            appKey,itemSkuDistPlanResponse.getCode(),itemSkuDistPlanResponse.getMessage()));
        }
    }
    
}