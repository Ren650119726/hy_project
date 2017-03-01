package com.mockuai.marketingcenter.client;

import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.common.domain.qto.ViewSalesQTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;

import java.util.List;

/**
 * Created by huangsiqian on 2016/10/8.
 */
public interface LimitedPurchaseClient {
    //添加活动
    public Response<Boolean> addLimitedPurchase(TimePurchaseQTO timePurchaseQTO, String appKey);
    //活动列表展示
    public Response<List> limitedPurchaseList(TimePurchaseQTO timePurchaseQTO, String appKey);
    //活动 和 产品 修改
    public Response<Boolean> updateLimitedPurchase(TimePurchaseQTO timePurchaseQTO , String appKey);
    //单条活动信息展示
    public Response<TimePurchaseQTO> limitedPurchaseById(TimePurchaseQTO timePurchaseQTO  , String appKey);
    //发布活动
    public Response<Boolean> startLimtedPurchaseAction(TimePurchaseQTO timePurchaseQTO ,String appKey);
    //活动失效
    public Response<Boolean> stopLimtedPurchaseAction(TimePurchaseQTO timePurchaseQTO,String appKey);
    //删除活动中产品
    public  Response<Boolean> deleteActivityGoods(LimitedPurchaseGoodsDTO activityGoodsDTO, String appKey);
    //添加活动中产品
    public  Response<Boolean> addActivityGoods(TimePurchaseQTO timePurchaseQTO,String  appKey);
    //查看限时购销售
    public Response<List> viewSales(ViewSalesQTO viewSalesQTO, String appKey);
    public Response<Boolean> updateLimitedPurchaseStatus(String  appKey);

}