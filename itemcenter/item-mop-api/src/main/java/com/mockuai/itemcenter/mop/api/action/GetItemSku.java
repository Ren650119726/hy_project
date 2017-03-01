package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.mop.api.domain.*;
import com.mockuai.itemcenter.mop.api.util.ExMopApiUtil;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class GetItemSku extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetItemSku.class);

    public MopResponse execute(Request request) {
        String skuUidStr = (String) request.getParam("sku_uid");
        String appKey = (String) request.getParam("app_key");

        Long userId = (Long) request.getAttribute("user_id");

        SkuUidDTO skuUidDTO = MopApiUtil.parseSkuUid(skuUidStr);
        if (skuUidDTO == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "sku_uid is invalid");
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.GET_ITEM_SKU.getActionName());
        itemReq.setParam("ID", skuUidDTO.getSkuId());
        itemReq.setParam("sellerId", skuUidDTO.getSellerId());
        itemReq.setParam("userId",userId);
        itemReq.setParam("appKey", appKey);

        try {

            Response<ItemSkuDTO> itemResp = this.getItemService().execute(itemReq);

            if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
                Map<String, Object> data = new HashMap<String, Object>();
                MopItemSkuDTO mopItemSkuDTO = MopApiUtil.genMopItemSku(itemResp.getModule());

                data.put("item_sku", mopItemSkuDTO);


                return new MopResponse(data);
            } else {
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }

        } catch (Exception e) {
            log.error("skuUid=" + skuUidStr, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }

    }

    public String getName() {
        return "/item/item_sku/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
