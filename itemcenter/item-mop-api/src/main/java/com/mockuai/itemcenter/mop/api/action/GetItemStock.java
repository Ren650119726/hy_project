package com.mockuai.itemcenter.mop.api.action;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemStockDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizg on 2016/11/1.
 * 查询商品库存
 */
public class GetItemStock extends BaseAction{

    private static final Logger log = LoggerFactory.getLogger(GetItemStock.class);

    @Override
    public MopResponse execute(Request request) {

        String itemUidStr = (String) request.getParam("item_uid");
        String appKey = (String) request.getParam("app_key");

        Long userId = (Long) request.getAttribute("user_id");

        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUidStr);
        if (itemUidDTO == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "item_uid is invalid");
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        log.info("[{}] itemId:{}",itemUidDTO.getItemId());
        itemReq.setCommand(ActionEnum.GET_ITEM_STOCK.getActionName());
        itemReq.setParam("id", itemUidDTO.getItemId());
        itemReq.setParam("supplierId", itemUidDTO.getSellerId());
        itemReq.setParam("userId", userId);
        itemReq.setParam("needDetail", true);
        itemReq.setParam("appKey", appKey);

        try {
            Response<ItemDTO> itemResp = this.getItemService().execute(itemReq);

            if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
                Map<String, Object> data = new HashMap<String, Object>();
                MopItemStockDTO mopItemStockDTO = MopApiUtil.genMopItemStock(itemResp.getModule());
                data.put("time", System.currentTimeMillis());
                data.put("item", mopItemStockDTO);
                return new MopResponse(data);
            } else {
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }

        } catch (Exception e) {
            log.error("itemUid=" + itemUidStr, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }
    }

    @Override
    public String getName() {
        return "/item/stock/get";
    }

    @Override
    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    @Override
    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
