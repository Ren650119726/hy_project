package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.HigoExtraInfoDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DistributionManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.JsonUtil;
import com.mockuai.tradecenter.core.util.ModelUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * 处理订单商品分销商信息，填充分销商店铺名称
 * Created by zengzhangqiang on 6/6/16.
 */
public class HandleDistributorInfoStep extends TradeBaseStep {

    @Override
    public StepName getName() {
        return StepName.HANDLE_HIGO_EXTRA_INFO_STEP;
    }

    @Override
    public TradeResponse execute() {
        List<OrderItemDO> orderItemDOList = (List<OrderItemDO>) this.getAttr("orderItemList");
        String appKey = (String) this.getAttr("appKey");
        DistributionManager distributionManager = (DistributionManager) this.getBean("distributionManager");


        Set<Long> distributorIdSet = new HashSet<Long>();
        for (OrderItemDO orderItemDO : orderItemDOList) {
            //分销商id校验
            if (orderItemDO.getDistributorId() == null || orderItemDO.getDistributorId().longValue() <= 0) {
                logger.error("distributorId is invalid, distributorId:{}, itemId:{}, skuId:{}, userId:{}",
                        orderItemDO.getDistributorId(), orderItemDO.getItemId(),
                        orderItemDO.getItemSkuId(), orderItemDO.getUserId());
                return new TradeResponse(ResponseCode.PARAM_E_PARAM_INVALID,
                        "商品携带的分销商id无效，商品名称：" + orderItemDO.getItemName());
            }
            distributorIdSet.add(orderItemDO.getDistributorId());
        }

        List<Long> distributorIdList = new ArrayList<Long>();
        distributorIdList.addAll(distributorIdSet);
        Map<Long, String> distShopMap = new HashMap<Long, String>();
        try {
            List<DistShopDTO> distShopDTOList = distributionManager.queryDistShop(distributorIdList, appKey);
            for (DistShopDTO distShopDTO : distShopDTOList) {
                distShopMap.put(distShopDTO.getSellerId(), distShopDTO.getShopName());
            }
        } catch (TradeException e) {
            //FIXME 出现错误情况，则忽略之，避免影响下单流程
            logger.error("error to queryDistShop, distributorIdList:{}",
                    JsonUtil.toJson(distributorIdList), e);
        }

        //校验是否有查不到的分销商
        for (OrderItemDO orderItemDO : orderItemDOList) {
            //分销商id校验
            if (distShopMap.containsKey(orderItemDO.getDistributorId()) == false) {
                logger.error("distributorId is invalid, distributorId:{}, itemId:{}, skuId:{}, userId:{}",
                        orderItemDO.getDistributorId(), orderItemDO.getItemId(),
                        orderItemDO.getItemSkuId(), orderItemDO.getUserId());
                return new TradeResponse(ResponseCode.PARAM_E_PARAM_INVALID,
                        "商品携带的分销商id无效，商品名称：" + orderItemDO.getItemName());
            }
        }

        //填充订单商品中的店铺信息
        if (distShopMap.isEmpty() == false) {
            for (OrderItemDO orderItemDO : orderItemDOList) {
                if (orderItemDO.getDistributorId() == null) {
                    continue;
                }
                orderItemDO.setDistributorName(distShopMap.get(orderItemDO.getDistributorId()));
            }
        }

        return ResponseUtils.getSuccessResponse();
    }
}