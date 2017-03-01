package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.higocenter.client.HigoClient;
import com.mockuai.higocenter.common.api.Response;
import com.mockuai.higocenter.common.domain.HigoSettlementDTO;
import com.mockuai.higocenter.common.domain.SettleItemSkuDTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.HigoManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public class HigoManagerImpl implements HigoManager {

    private static final Logger log = LoggerFactory.getLogger(HigoManagerImpl.class);

    @Resource
    private HigoClient higoClient;

    @Override
    public HigoSettlementDTO getHigoSettlement(List<SettleItemSkuDTO> settleItemSkuDTOs, Long freight, String appKey)
            throws MarketingException {
        //TODO 入参校验
        try {
            log.info("settlementInfo : {}, freight : {}, appKey : {}", JsonUtil.toJson(settleItemSkuDTOs), freight, appKey);
            Response<HigoSettlementDTO> higoResp = higoClient.getHigoSettlement(settleItemSkuDTOs, freight, appKey);
            if (higoResp.isSuccess()) {
                return higoResp.getModule();
            } else {
                log.error("error to call higo service, errorCode:{}, errorMsg:{}",
                        higoResp.getCode(), higoResp.getMessage());
                throw new MarketingException(ResponseCode.SYS_E_REMOTE_CALL_ERROR, "higo service exception");
            }
        } catch (Throwable t) {
            log.error("", t);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "system error");
        }
    }
}
