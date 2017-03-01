package com.mockuai.distributioncenter.mop.api.action;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.springframework.util.StringUtils;

import static com.mockuai.distributioncenter.common.constant.ActionEnum.QUERY_GOODS_ITEM;

/**
 * Created by 冠生 on 2016/5/20.
 * 获得商品分组
 */
public class QueryGoodsItem extends BaseAction {
    public MopResponse execute(Request request) {
        String appKey = (String) request.getParam("app_key");
        String   groupIdStr = (String) request.getParam("group_id");
        String   sellerIdStr = (String) request.getParam("seller_id");
        String   offset = (String) request.getParam("offset");
        String   count = (String) request.getParam("count");
        String   itemName = (String) request.getParam("item_name");
        String   orderBy = (String) request.getParam("order_by");
        String desc = (String) request.getParam("desc");
        Long userId,groupId, sellerId;

        try {
            userId = Long.parseLong( request.getParam("user_id").toString());

            groupId = Long.parseLong(groupIdStr);
            sellerId = Long.parseLong(sellerIdStr);
        }catch (Exception e){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }

        //分页字段不合法
        if(offset != null &&   ( !offset.matches("\\d+")  )){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        if(count != null &&   ( !count.matches("\\d+")  )){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        BaseRequest baseRequest = new BaseRequest();

        if(!StringUtils.hasText(orderBy)){
            orderBy = "1";
        }
        if( !orderBy.equals("1") && !orderBy.equals("2") &&  !orderBy.equals("3")){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        if(!StringUtils.hasText(desc)){
            desc = "0";
        }
        if(!"0".equals(desc) && !"1".equals(desc)){
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL);
        }
        baseRequest.setParam("orderBy",Integer.parseInt( orderBy));
        baseRequest.setParam("desc",Integer.parseInt(desc));
        baseRequest.setParam("itemName",itemName);

        baseRequest.setCommand(QUERY_GOODS_ITEM.getActionName());
        baseRequest.setParam("appKey",appKey);
        baseRequest.setParam("groupId",groupId);
        baseRequest.setParam("sellerId",sellerId);
        baseRequest.setParam("offset",offset);
        baseRequest.setParam("count",count);
        baseRequest.setParam("userId",userId );
        Response response = getDistributionService().execute(baseRequest);
        if(response.isSuccess()){
            return new MopResponse(response);
        }
        return new MopResponse(response.getCode(),response.getMessage());
    }

    public String getName() {
        return "/marketing/goods/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
