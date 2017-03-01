package com.mockuai.giftscenter.core.service.action.gifts;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.common.domain.dto.ActionGiftDTO;
import com.mockuai.giftscenter.common.domain.dto.ActionGiftMarketingDTO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ActionGiftManager;
import com.mockuai.giftscenter.core.manager.AppManager;
import com.mockuai.giftscenter.core.manager.GrantCouponRecordManager;
import com.mockuai.giftscenter.core.manager.MarketingManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.BaseAction;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 发放有礼礼品
 */
@Service
public class GrantActionGiftAction extends TransAction {


    private static final Logger logger = LoggerFactory.getLogger(GrantActionGiftAction.class);

    @Autowired
    private ActionGiftManager actionGiftManager;
    @Autowired
    private MarketingManager marketingManager;
    @Autowired
    private GrantCouponRecordManager grantCouponRecordManager;
    @Autowired
    private AppManager appManager;
    public static final Logger LOGGER = LoggerFactory.getLogger(BaseAction.class);

    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
        Request request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        int actionType = (int) request.getParam("actionType");
        int appType = (int) request.getParam("appType");

        long receiverId = (long) request.getParam("receiverId");
        String mobile = (String) request.getParam("mobile");
        AppInfoDTO appInfoDTO = appManager.getAppInfo(appKey);
        LOGGER.info("appKey:{} actionType :{},appType:{},receiverId:{},mobile:{},appInfo.appKey:{}",appKey,actionType,appType,receiverId,mobile,appInfoDTO.getAppType());
        ActionGiftDTO actionGiftDTO =         actionGiftManager.queryByActionType(actionType,appKey);
        //活动已关闭
        if(actionGiftDTO.getOpenFlag() == 0){
            LOGGER.info("{}活动已关闭",actionGiftDTO.getOpenFlag());
            return GiftsUtils.getFailResponse(ResponseCode.BIZ_GIFT_IS_CLOSED);
        }
        String appTypeAction =   actionGiftDTO.getAppType();
        List<String>  appTypeList =  Arrays.asList(  appTypeAction.split(","));
        //客户端已不参与有礼活动
        boolean isFindAppType = false;

        for(String item : appTypeList){
            if( ( actionType<3?appType:appInfoDTO.getAppType()) ==  Integer.parseInt(item.trim())   ){
                isFindAppType = true;
                break;
            }
        }
        if(!isFindAppType){
            LOGGER.info("{}有礼活动的客户端列表:{}",actionType,appTypeList);
            LOGGER.info("appType:{},appInfo.appType:{}客户端已不参与{}有礼活动",appType,appInfoDTO.getAppType(),actionType);
            return GiftsUtils.getFailResponse(ResponseCode.BIZ_GIFT_NOT_CONTAIN_CLIENT);
        }
        //发放有礼
        //发送状态
        try {
            List<ActionGiftMarketingDTO>  actionGiftMarketingDTOList =  actionGiftDTO.getItemList();
            //发券成功记录
            List<ActionGiftMarketingDTO> grantCoupons = new ArrayList<>();
            ActionGiftMarketingDTO grantFailedCoupon = null ;
            String errorTpl = "grant activityCoupons:%s,num:%s failed";
            /**
             *  actionType  1 注册 2邀请 3开店
             *  grantSource 1 开店 2 注册 3邀请
             */
            int grantSource = 0;
            switch (actionType){
                case  1:
                grantSource =2;
                    break;
                case  2:
                grantSource = 3;
                break;
                case 3:
                grantSource = 1;
                break;
            }
            for( ActionGiftMarketingDTO entry : actionGiftMarketingDTOList){
                Boolean grantResult =   marketingManager.grantActivityCoupons(receiverId,entry.getItem().getId(),entry.getCount(),grantSource,appKey);
                //礼包发放异常
                if(! grantResult){
                    logger.warn("grant coupon id:{} name:{} failed ",entry.getItem().getId(),entry.getItem().getName());
                    grantFailedCoupon = entry;
                    continue;
                }
                logger.info("grant coupon id:{} name:{} success ",entry.getItem().getId(),entry.getItem().getName());
                grantCoupons.add(entry);
            }
            //写入发券成功的记录
            if(! grantCoupons.isEmpty()){
                List<GrantCouponRecordDO>  grantCouponRecordDOList = new ArrayList<>();
                for(ActionGiftMarketingDTO item : grantCoupons){
                   int count = item.getCount();
                   GrantCouponRecordDO grantCouponRecordDO = new GrantCouponRecordDO();
                   grantCouponRecordDO.setAmount(item.getItem().getDiscountAmount());
                   grantCouponRecordDO.setCouponId(item.getItem().getId());
                   grantCouponRecordDO.setCouponName(item.getItem().getName());
                   grantCouponRecordDO.setUserId(receiverId);
                   grantCouponRecordDO.setMobile(mobile);
                   grantCouponRecordDO.setActionType(actionType);
                   grantCouponRecordDO.setAppType(appType);
                   pushRecord(grantCouponRecordDOList,grantCouponRecordDO,count);
                }
                grantCouponRecordManager.save(grantCouponRecordDOList);
            }
            //发券失败报异常
            if(grantFailedCoupon != null){
                String errorInfo = String.format(errorTpl,grantFailedCoupon.getItem().getName(), grantFailedCoupon.getCount());
                logger.error(errorInfo);
                return GiftsUtils.getFailResponse(ResponseCode.BIZ_GIFT_GRANT_FAILED.getCode(),errorInfo);
            }
        }catch (Exception e){
            LOGGER.error("",e);
            return GiftsUtils.getFailResponse(ResponseCode.BIZ_GIFT_GRANT_FAILED.getCode(),e.getMessage());
        }

        return GiftsUtils.getSuccessResponse();
    }

    private void pushRecord(List<GrantCouponRecordDO> grantCouponRecordDTOList , GrantCouponRecordDO grantCouponRecordDO, int count){
        for(int i = 0 ;i< count ; i++){
            grantCouponRecordDTOList.add(grantCouponRecordDO);
        }
    }


    @Override
    public String getName() {
        return ActionEnum.GRANT_ACTION_GIFT.getActionName();
    }
}