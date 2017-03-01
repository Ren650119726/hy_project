package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.client.GainsSetClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.common.domain.qto.ItemSkuDistPlanQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.DistributorManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/5/19.
 *  * updated by jiguansheng
 */
@Service
public class DistributorManagerImpl implements DistributorManager {

    private static final Logger log = LoggerFactory.getLogger(DistributorManagerImpl.class);


    @Resource
    private DistributionClient distributionClient;
    @Resource
    private GainsSetClient gainsSetClient;

    @Override
    public GainsSetDTO  getGainsSet(String appKey) throws ItemException {
        Response<GainsSetDTO> response;
        try {
            response =gainsSetClient.getGainsSet(appKey);
            if(! response.isSuccess()){
                throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,response.getMessage());
            }
            return response.getModule();
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,"未能查询收益信息");
        }
    }

    @Override
    public List<SellerDTO> querySeller(SellerQTO sellerQTO,String appKey) throws ItemException{

        Response<List<SellerDTO>> response;
        try {
            response = distributionClient.querySeller(sellerQTO, appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,
                    "未能查询到分销商信息");
        }

        if(response.isSuccess()){
            return response.getModule();
        }else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"未能查询到分销商信息");
        }
    }

    @Override
    public List<DistShopDTO> queryShop(DistShopQTO qto,String appKey) throws ItemException{

        Response<List<DistShopDTO>> response;
        try {
            response = distributionClient.queryShop(qto, appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,"未能查询到分销商信息");
        }

        if(response.isSuccess()){
            return response.getModule();
        }else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"未能查询到分销商信息");
        }
    }
    //getDistByItemSkuId

    /**
     *
     * @param itemSkuIdList
     * @param appKey
     * @return
     * @throws ItemException
     */
    @Override
    public List<ItemSkuDistPlanDTO> getDistByItemSkuId(List<Long> itemSkuIdList, String appKey) throws ItemException{

        Response<List<ItemSkuDistPlanDTO>> response;
        try {
            ItemSkuDistPlanQTO itemSkuDistPlanQTO = new ItemSkuDistPlanQTO();
            itemSkuDistPlanQTO.setItemSkuIdList(itemSkuIdList);
            response = distributionClient.getDistByItemSkuId(itemSkuDistPlanQTO,appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,"未能查询到分销商信息");
        }

        if(response.isSuccess()){
            return response.getModule();
        }else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"未能查询到分销商信息");
        }
    }




    @Override
    public DistShopForMopDTO getShopForMopBySellerId(Long distributorId, String appKey) throws ItemException {

        Response<DistShopForMopDTO> response;

        try {
            response = distributionClient.getShopForMopBySellerId(distributorId, appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,"未能查询到分销商信息");
        }

        if(response.isSuccess()){
            return response.getModule();
        }else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"未能查询到分销商信息");
        }

    }
    public List<ItemSkuDistPlanDTO> getItemSkuDistPlanList(Long itemId , String appKey) throws ItemException {
        Response<List<ItemSkuDistPlanDTO>> itemSkuDistPlanResponse ;
        try {
            itemSkuDistPlanResponse = distributionClient.getItemSkuDistPlanByItemId(itemId,appKey);

        }catch (Exception e){
            log.error("itemId:{} , 未能查询到商品分佣信息 message:{}",itemId.intValue(),e.getMessage());
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION,
               String.format("%d , 未能查询到商品分佣信息 message:%s",itemId.intValue(),e.getMessage())     );
        }
        if(itemSkuDistPlanResponse.isSuccess()){
            return itemSkuDistPlanResponse.getModule();
        }else {
            log.error("itemId:{} ,未能查询到商品分佣信息 appKey:{}code:{},msg:{}",
                    itemId.intValue(),appKey,itemSkuDistPlanResponse.getCode(),itemSkuDistPlanResponse.getMessage()  );

            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,
                    String.format("%d , 未能查询到商品分佣信息 appKey:%s,code:%s,msg:%s",itemId.intValue(),
                            appKey,itemSkuDistPlanResponse.getCode(),itemSkuDistPlanResponse.getMessage()));
        }
    }



}
