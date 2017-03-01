package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ActivityType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedGoodsCorrelationDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseGoodsQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseSKUGoodsQTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseDAO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedGoodsCorrelationManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**添加活动和商品，添加商品时需添加到两张表中（商品表和中间表）
 * Created by huangsiqian on 2016/10/8.
 */
@Service
public class AddTimePurchaseAction extends TransAction {
    @Autowired
    private LimitedPurchaseManager activityManager;
    @Autowired
    private LimitedPurchaseGoodsManager activityGoodsManager;
    @Autowired
    private LimitedGoodsCorrelationManager limitedGoodsCorrelationManager;
    @Autowired
    private LimitedPurchaseDAO limitedPurchaseDAO;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {
        TimePurchaseQTO timePurchaseQTO = (TimePurchaseQTO)context.getRequest().getParam("timePurchaseQTO");
        //入参校验
        if (timePurchaseQTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请输入数据");
        }
        if (null == timePurchaseQTO.getActivityName() || "".equals(timePurchaseQTO.getActivityName())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入活动名");
        }
        if (null == timePurchaseQTO.getVoucherType() || "".equals(timePurchaseQTO.getVoucherType())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传入优惠券使用参数");
        }
        if(timePurchaseQTO.getActivityName().length()>50){
            return new MarketingResponse(ResponseCode.PARAMETER_ERROR, "活动名超过限制");
        }
        //活动时间校验，活动时间不能为空
        if (timePurchaseQTO.getStartTime() == null || timePurchaseQTO.getEndTime() == null) {
            return new MarketingResponse(ResponseCode.PARAM_E_ACTIVITY_TIME_CANNOT_BE_NULL);
        }

        //活动时间有效性验证,只要判断结束时间是否在开始时间之前
        if (timePurchaseQTO.getStartTime().after(timePurchaseQTO.getEndTime())) {
            return new MarketingResponse<>(ResponseCode.PARAM_E_ACTIVITY_TIME_INVALID);
        }
        //选择优惠券叠加，默认叠加
        if (null ==timePurchaseQTO.getActivityTag()||"".equals(timePurchaseQTO.getActivityTag())) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "请传放活动角标");
        }


        TimePurchaseQTO activityQTO = new TimePurchaseQTO();
        activityQTO.setActivityName(timePurchaseQTO.getActivityName());
        activityQTO.setActivityTag(timePurchaseQTO.getActivityTag());
        activityQTO.setStartTime(timePurchaseQTO.getStartTime());
        activityQTO.setEndTime(timePurchaseQTO.getEndTime());
        if("1".equals(timePurchaseQTO.getVoucherType().toString())||"0".equals(timePurchaseQTO.getVoucherType().toString())){
            activityQTO.setVoucherType(timePurchaseQTO.getVoucherType());
        }else{
            return  new MarketingResponse(ResponseCode.PARAMETER_ERROR, "请传入正确的优惠券标记");
        }

        LimitedPurchaseDO purchaseDO = new LimitedPurchaseDO();
        purchaseDO.setActivityName(timePurchaseQTO.getActivityName());
        purchaseDO.setActivityTag(timePurchaseQTO.getActivityTag());
        purchaseDO.setStartTime(timePurchaseQTO.getStartTime());
        purchaseDO.setEndTime(timePurchaseQTO.getEndTime());
        purchaseDO.setVoucherType(timePurchaseQTO.getVoucherType());
        //活动开始状态添加 (需加定时任务)
        //activityQTO.setStatus(timePurchaseQTO.getStatus());
        //取出要保存商品信息
        List<TimePurchaseGoodsQTO> list = timePurchaseQTO.getGoodsInfo();
        if(list == null||list.size() == 0){
            return MarketingUtils.getFailResponse(ResponseCode.PARAMETER_NULL.getCode(), "商品未添加");
        }
        //定标记（定义为全局变量）
        Long last_id = null;
        //取SPU级商品
        Iterator<TimePurchaseGoodsQTO> it = list.iterator();
        while(it.hasNext()) {
            TimePurchaseGoodsQTO timePurchaseGoodsQTO = it.next();
            if (null == timePurchaseGoodsQTO.getItemId()||"".equals(timePurchaseGoodsQTO.getItemId())) {
                return new MarketingResponse(ResponseCode.PARAMETER_NULL, "产品id为空");
            }
            //判断同一spu商品不能参加有时间交叉的活动（判断活动中间表中）
            List<LimitedGoodsCorrelationDTO> goodsMsg = limitedGoodsCorrelationManager.selectMsgByItemId(timePurchaseGoodsQTO.getItemId());
            if(goodsMsg!=null||!goodsMsg.isEmpty()) {
                Iterator<LimitedGoodsCorrelationDTO> it3 = goodsMsg.iterator();
                while (it3.hasNext()) {
                    LimitedGoodsCorrelationDTO goodsCorrelationDTO = it3.next();
                    Long tableStartTime = goodsCorrelationDTO.getStartTime().getTime();
                    Long tableEndTime = goodsCorrelationDTO.getEndTime().getTime();
                    Long startTime =timePurchaseQTO.getStartTime().getTime();
                    Long endTime = timePurchaseQTO.getEndTime().getTime();

                    if ((startTime<tableStartTime||startTime.equals(tableStartTime))
                            && (tableStartTime<endTime||tableStartTime.equals(endTime))) {
                        return new MarketingResponse(ResponseCode.PARAMETER_ERROR,timePurchaseGoodsQTO.getItemName() + "已参加活动");
                    }
                    if ((tableStartTime<startTime||startTime.equals(tableStartTime))
                            && (tableEndTime>startTime||tableEndTime.equals(endTime))) {
                        return new MarketingResponse(ResponseCode.PARAMETER_ERROR,timePurchaseGoodsQTO.getItemName() + "已参加活动");

                    }
                }
            }
            //保存活动信息逻辑
            LimitedPurchaseDO resultpurchaseDO = limitedPurchaseDAO.selectActivities(purchaseDO);
            //如果取出活动有值，则不处理，继续添加商品
            //（只在商品没有参加别的活动时存入表中时，添加活动信息，第二次添加时判断是否加入过活动信息，如果加入则跳过不再重复加入活动信息）
            if(null==resultpurchaseDO){
                last_id = activityManager.addActivities(activityQTO);
                if(last_id<1) {
                    return MarketingUtils.getFailResponse(ResponseCode.SERVICE_EXCEPTION.getCode(), "添加活动失败");
                }

            }


            if (null == timePurchaseGoodsQTO.getSkuInfo()||"".equals(timePurchaseGoodsQTO.getSkuInfo())) {
                return new MarketingResponse(ResponseCode.PARAMETER_NULL, "sku单品为空");
            }


            //取出SKU级商品
            List<TimePurchaseSKUGoodsQTO> goodsDTOs = timePurchaseGoodsQTO.getSkuInfo();
            Iterator<TimePurchaseSKUGoodsQTO> it2 = goodsDTOs.iterator();
            while(it2.hasNext()) {
                TimePurchaseSKUGoodsQTO SKUgoods = it2.next();
                LimitedPurchaseGoodsDO activityGoodsDO = new LimitedPurchaseGoodsDO();
                //数量大于0在前端已经判断过
                if (null == SKUgoods.getGoodsQuantity()||"".equals(SKUgoods.getGoodsQuantity())) {
                    return new MarketingResponse(ResponseCode.PARAMETER_NULL, "限购数量为空");
                }
                //activity_id和item_id,goods_quantity在循环外取
                activityGoodsDO.setActivityId(last_id);
                activityGoodsDO.setItemId(timePurchaseGoodsQTO.getItemId());

                //此逻辑是在限时购商品表中判断，因为现在要判断活动中间表中是否有重，所以只活动中间表中表即可
                // （根据item_id取出activity_id,根据activity_id取出活动时间，最后判断活动时间是否有交叉）

//                while (it3.hasNext()){
//                List activityIdList = limitedPurchaseGoodsDAO.selectActivityIdByItemId(timePurchaseGoodsQTO.getItemId());
//                //循环取出活动id
//                Iterator<Long> it3 = activityIdList.iterator();
//                while (it3.hasNext()){
//                    Long activityId= it3.next();
//                    //循环取出活动信息
//                    LimitedPurchaseDTO activityDTO = activityManager.activityById(activityId);
//                    if(activityDTO.getStartTime().before(timePurchaseQTO.getStartTime())
//                            &&activityDTO.getEndTime().after(timePurchaseQTO.getStartTime())){
//                        return new MarketingResponse(ResponseCode.PARAMETER_ERROR, "已经有相同商品参加同一时间段限时购活动");
//                    }
//                    if(activityDTO.getStartTime().after(timePurchaseQTO.getStartTime())
//                            &&activityDTO.getStartTime().before(timePurchaseQTO.getEndTime())){
//                        return new MarketingResponse(ResponseCode.PARAMETER_ERROR, "已经有相同商品参加同一时间段限时购活动");
//                    }
//
//                }


                //sku级别取限购数量,skuId,价格
                activityGoodsDO.setGoodsQuantity(SKUgoods.getGoodsQuantity());

                activityGoodsDO.setSkuId(SKUgoods.getSkuId());
                activityGoodsDO.setGoodsPrice(SKUgoods.getGoodsPrice());
                Boolean flag = activityGoodsManager.addActivityGoods(activityGoodsDO);
                if (!flag) {
                    return new MarketingResponse(ResponseCode.PARAMETER_NULL, "添加商品失败");
                }
            }
            //限时购商品添加完成后，再添加到活动中间表中
            LimitedGoodsCorrelationDO goodsCorrelationDO = new LimitedGoodsCorrelationDO();
            //活动id为活动表返回出来的id
            goodsCorrelationDO.setActivityId(last_id);
            goodsCorrelationDO.setItemId(timePurchaseGoodsQTO.getItemId());
            //0代表限时购活动
            goodsCorrelationDO.setActivityType(ActivityType.LIMITEDPURCHASE.getValue());
            goodsCorrelationDO.setStartTime(timePurchaseQTO.getStartTime());
            goodsCorrelationDO.setEndTime(timePurchaseQTO.getEndTime());
            Boolean flag2 = limitedGoodsCorrelationManager.addActivityGoods(goodsCorrelationDO);
            if (!flag2) {
                return new MarketingResponse(ResponseCode.PARAMETER_NULL, "添加中间表商品失败");
            }
        }
        return MarketingUtils.getSuccessResponse(last_id);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_TIME_PURCHASE.getActionName();
    }
}



