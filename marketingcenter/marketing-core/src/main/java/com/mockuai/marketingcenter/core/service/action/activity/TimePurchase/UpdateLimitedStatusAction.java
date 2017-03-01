package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityType;
import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.constant.LimitTimeIssueStatus;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedGoodsCorrelationManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/10.
 */
@Service
public class UpdateLimitedStatusAction extends TransAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateLimitedStatusAction.class);
    @Autowired
    private LimitedPurchaseManager limitedPurchaseManager;
    @Autowired
    private LimitedPurchaseGoodsManager limitedPurchaseGoodsManager;
    @Autowired
    private LimitedGoodsCorrelationManager limitedGoodsCorrelationManager;
    @Override
    public String getName() {
       return ActionEnum.UP_DATE_LIMITED_PURCHASE_STATUS.getActionName();
    }

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        Date now = new Date();
        TimePurchaseQTO purchaseQTO = new TimePurchaseQTO();
        List<LimitedPurchaseDTO> purchaseDTOs = limitedPurchaseManager.activityList(purchaseQTO);
        Boolean flag = null;
//        LOGGER.info("time task info:{}", JsonUtil.toJson(purchaseDTOs));
        for(LimitedPurchaseDTO purchaseDTO :purchaseDTOs) {
            //活动状态为未结束才修改状态且活动为发布状态
            if (!purchaseDTO.getRunStatus().equals(LimitTimeActivityStatus.ALREADYOVER.getValue()) && purchaseDTO.getIssueStatus().equals(LimitTimeIssueStatus.ISSUE.getValue())) {


                if (now.before(purchaseDTO.getEndTime()) && now.after(purchaseDTO.getStartTime())) {
                    flag = limitedPurchaseManager.updateLimitedPurchaseStatus("begin", purchaseDTO.getId());
                }
                if (now.after(purchaseDTO.getEndTime())) {
                    flag = limitedPurchaseManager.updateLimitedPurchaseStatus("finish", purchaseDTO.getId());
                    LimitedPurchaseGoodsDO limitedGoodsDO = new LimitedPurchaseGoodsDO();
                    limitedGoodsDO.setActivityId(purchaseDTO.getId());
                    Boolean flag2 = limitedPurchaseGoodsManager.invalidateActivityGoods(limitedGoodsDO);
                    //商品中间表商品失效
                    LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
                    goodsCorrelationDO.setActivityId(purchaseDTO.getId());
                    //限时购活动类型定为0
                    goodsCorrelationDO.setActivityType(ActivityType.LIMITEDPURCHASE.getValue());
                    //商品表商品失效
                    Boolean flag3 = limitedGoodsCorrelationManager.stopActivity(goodsCorrelationDO);
                }
            }
            if(LimitTimeIssueStatus.NOISSUE.getValue().equals(purchaseDTO.getIssueStatus())&&now.after(purchaseDTO.getStartTime())){
                flag = limitedPurchaseManager.updateLimitedPurchaseStatus("finish", purchaseDTO.getId());
                LimitedPurchaseGoodsDO limitedGoodsDO = new LimitedPurchaseGoodsDO();
                limitedGoodsDO.setActivityId(purchaseDTO.getId());
                Boolean flag2 = limitedPurchaseGoodsManager.invalidateActivityGoods(limitedGoodsDO);
                //商品中间表商品失效
                LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
                goodsCorrelationDO.setActivityId(purchaseDTO.getId());
                //限时购活动类型定为0
                goodsCorrelationDO.setActivityType(ActivityType.LIMITEDPURCHASE.getValue());
                //商品表商品失效
                Boolean flag3 = limitedGoodsCorrelationManager.stopActivity(goodsCorrelationDO);

            }
        }
        return MarketingUtils.getSuccessResponse(true);
    }
}
