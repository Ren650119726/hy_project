package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.higocenter.common.domain.HigoSettlementDTO;
import com.mockuai.higocenter.common.domain.SettleItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.HigoExtraInfoDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.HigoManager;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mockuai.marketingcenter.common.constant.ComponentType.GET_HIGO_SETTLEMENT;

/**
 * 获取指定商品列表的跨境结算信息
 * <p/>
 * Created by edgar.zr on 1/19/16.
 */
@Service
public class GetHigoSettlement implements Component {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetHigoSettlement.class);

    @Resource
    private HigoManager higoManager;

    public static Context wrapParams(SettlementInfo settlementInfo, String appKey) {
        Context context = new Context();
        context.setParam("settlementInfo", settlementInfo);
        context.setParam("appKey", appKey);
        context.setParam("component", GET_HIGO_SETTLEMENT);
        return context;
    }

    @Override
    public void init() {

    }

    @Override
    public SettlementInfo execute(Context context) throws MarketingException {

        SettlementInfo settlementInfo = (SettlementInfo) context.getParam("settlementInfo");
        String appKey = (String) context.getParam("appKey");

        List<SettleItemSkuDTO> settleItemSkuDTOs = new ArrayList<SettleItemSkuDTO>();
        Map<Long, MarketItemDTO> marketItemMap = new HashMap<Long, MarketItemDTO>();
        for (MarketItemDTO marketItemDTO : settlementInfo.getItemList()) {
            marketItemMap.put(marketItemDTO.getItemSkuId(), marketItemDTO);

            //封装settleItemSku对象，并添加到列表中
            SettleItemSkuDTO settleItemSkuDTO = new SettleItemSkuDTO();
            settleItemSkuDTO.setItemId(marketItemDTO.getItemId());
            settleItemSkuDTO.setItemSkuId(marketItemDTO.getItemSkuId());
            settleItemSkuDTO.setSellerId(marketItemDTO.getSellerId());
            settleItemSkuDTO.setPrice(marketItemDTO.getOriginTotalPrice());
            settleItemSkuDTO.setNumber(marketItemDTO.getNumber());

            settleItemSkuDTOs.add(settleItemSkuDTO);
        }
        LOGGER.info("higo settlementDTO : {}", JsonUtil.toJson(settleItemSkuDTOs));
        //如果商品列表是空的，则直接返回原结算信息
        if (settleItemSkuDTOs.isEmpty()) {
            return settlementInfo;
        }
        LOGGER.info("settlement before higo : {}", JsonUtil.toJson(settlementInfo));
        LOGGER.info("settlement in higo : {}", JsonUtil.toJson(settleItemSkuDTOs));
        HigoSettlementDTO higoSettlementDTO =
                higoManager.getHigoSettlement(settleItemSkuDTOs, settlementInfo.getDeliveryFee(), appKey);

        for (SettleItemSkuDTO settleItemSkuDTO : higoSettlementDTO.getSettleItemSkuList()) {
            MarketItemDTO marketItemDTO = marketItemMap.get(settleItemSkuDTO.getItemSkuId());
            HigoExtraInfoDTO higoExtraInfoDTO = new HigoExtraInfoDTO();
            higoExtraInfoDTO.setOriginalTaxFee(settleItemSkuDTO.getTaxFee());
            higoExtraInfoDTO.setFinalTaxFee(settleItemSkuDTO.getTaxFee());
            higoExtraInfoDTO.setTaxRate(settleItemSkuDTO.getTaxRate());
            higoExtraInfoDTO.setDeliveryType(settleItemSkuDTO.getDeliveryType());
            marketItemDTO.setHigoExtraInfo(higoExtraInfoDTO);
        }

        //整个商品列表的跨境结算信息
        HigoExtraInfoDTO higoExtraInfoDTO = new HigoExtraInfoDTO();
        higoExtraInfoDTO.setOriginalTaxFee(higoSettlementDTO.getOriginalTaxFee());
        higoExtraInfoDTO.setFinalTaxFee(higoSettlementDTO.getFinalTaxFee());

        settlementInfo.setHigoExtraInfo(higoExtraInfoDTO);
        return settlementInfo;
    }

    @Override
    public String getComponentCode() {
        return GET_HIGO_SETTLEMENT.getCode();
    }
}