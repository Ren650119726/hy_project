package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.higocenter.client.HigoClient;
import com.mockuai.higocenter.common.api.Response;
import com.mockuai.higocenter.common.domain.HigoSettlementDTO;
import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SettleItemSkuDTO;
import com.mockuai.higocenter.common.domain.qto.ItemHigoInfoQTO;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.HigoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public class HigoManagerImpl implements HigoManager{
    private static final Logger log = LoggerFactory.getLogger(HigoManagerImpl.class);

    @Resource
    private HigoClient higoClient;

    @Override
    public HigoSettlementDTO getHigoSettlement(List<SettleItemSkuDTO> settleItemSkuDTOs,long deliveryFee ,String appKey)
            throws TradeException {
        //TODO 入参校验
        try{
            Response<HigoSettlementDTO> higoResp = higoClient.getHigoSettlement(settleItemSkuDTOs, deliveryFee,appKey);
            if(higoResp.isSuccess()){
                return higoResp.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR, "higo service exception");
            }
        }catch(Throwable t){
            log.error("", t);
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
        }
    }

	@Override
	public List<ItemHigoInfoDTO> queryItemHigoInfo(ItemHigoInfoQTO itemHigoInfoQTO, String appKey)throws TradeException {
		try{
			Response<List<ItemHigoInfoDTO>> response = higoClient.queryItemHigoInfo(itemHigoInfoQTO,appKey);
			if(response.isSuccess()){
                return response.getModule();
            }else{
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                		response.getCode(), response.getMessage());
                throw new TradeException(ResponseCode.SYS_E_REMOTE_CALL_ERROR, "higo service exception");
            }
		}catch(Throwable t){
			log.error("",t);
			throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "system error");
		}
	}
}
