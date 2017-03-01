package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.higocenter.client.HigoClient;
import com.mockuai.higocenter.common.api.Response;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HigoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
@Service
public class HigoManagerImpl implements HigoManager{
    private static final Logger log = LoggerFactory.getLogger(HigoManagerImpl.class);

    @Resource
    private HigoClient higoClient;

    @Override
    public ItemHigoInfoDTO getItemHigoInfo(Long itemId, Long sellerId, String appKey) throws ItemException {
        //TODO 入参校验
        try{
            Response<ItemHigoInfoDTO> higoResp = higoClient.getItemHigoInfo(itemId, sellerId, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public List<ItemHigoInfoDTO> getItemHigoInfoList(List<Long> itemIdList, Long sellerId, String appKey) throws ItemException {
        //TODO 入参校验
        try{
            Response<List<ItemHigoInfoDTO>> higoResp = higoClient.getItemHigoInfoList(itemIdList, sellerId, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public ItemHigoInfoDTO addItemHigoInfo(ItemHigoInfoDTO itemHigoInfoDTO, String appKey) throws ItemException {
        //TODO 入参校验
        try{
            Response<ItemHigoInfoDTO> higoResp = higoClient.addItemHigoInfo(itemHigoInfoDTO, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public boolean updateItemHigoInfo(ItemHigoInfoDTO itemHigoInfoDTO, String appKey) throws ItemException {
        //TODO 入参校验
        try{
            Response<Void> higoResp = higoClient.updateItemHigoInfo(itemHigoInfoDTO, appKey);
            if(higoResp.isSuccess()){
                return true;
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public SkuHigoInfoDTO getSkuHigoInfo(Long skuId, Long sellerId, String appKey) throws ItemException {
        try{
            Response<SkuHigoInfoDTO> higoResp = higoClient.getSkuHigoInfo(skuId, sellerId, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public SkuHigoInfoDTO addSkuHigoInfo(SkuHigoInfoDTO skuHigoInfoDTO,String appKey) throws ItemException {
        try{
            Response<SkuHigoInfoDTO> higoResp = higoClient.addSkuHigoInfo(skuHigoInfoDTO, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public boolean updateSkuHigoInfo(SkuHigoInfoDTO skuHigoInfoDTO,String appKey) throws ItemException {
        try{
            Response<Void> higoResp = higoClient.updateSkuHigoInfo(skuHigoInfoDTO, appKey);
            if(higoResp.isSuccess()){
                return true;
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

    @Override
    public List<SkuHigoInfoDTO> getSkuHigoInfoList(List<Long> skuIdList, Long sellerId, String appKey)  throws ItemException {
        try{
            Response<List<SkuHigoInfoDTO>> higoResp = higoClient.getSkuHigoInfoList(skuIdList, sellerId, appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new ItemException(ResponseCode.SYS_E_DEPENDENT_SERVICE_EXCEPTION, "higo service exception");
            }
        }catch(Throwable t){
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }
}
