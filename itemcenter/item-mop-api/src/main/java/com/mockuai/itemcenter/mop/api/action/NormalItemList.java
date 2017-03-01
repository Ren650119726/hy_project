package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemGroupUidDTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class NormalItemList extends BaseAction {

    public MopResponse execute(Request request) {

        String itemName = (String) request.getParam("item_name");
        String categoryIdStr = (String) request.getParam("category_id");
        String brandIdStr = (String) request.getParam("brand_id");
        String offset = (String) request.getParam("offset");
        String count = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");
        String itemUidListStr = (String) request.getParam("item_uid_list");
        String sellerIdStr = (String) request.getParam("seller_id");
        String itemGroupUid = (String) request.getParam("item_group_uid");

        ItemQTO itemQTO = new ItemQTO();

        if (StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)) {

            itemQTO.setOffset(Integer.parseInt(offset));
            if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
                itemQTO.setPageSize(Integer.parseInt(count));
                itemQTO.setNeedPaging(true);
            }
        }

        if (StringUtils.isNotEmpty(categoryIdStr) && StringUtils.isNumeric(categoryIdStr)) {
            itemQTO.setCategoryId(Long.parseLong(categoryIdStr));
        }

        if (StringUtils.isNotEmpty(brandIdStr) && StringUtils.isNumeric(brandIdStr)) {
            itemQTO.setItemBrandId(Long.parseLong(brandIdStr));
        }

        if (StringUtils.isNotEmpty(sellerIdStr) && StringUtils.isNumeric(sellerIdStr)) {
            itemQTO.setSellerId(Long.parseLong(sellerIdStr));
        }else {
            itemQTO.setSellerId(0L);
        }

        if (StringUtils.isNotEmpty(itemGroupUid)) {

        }

        if (StringUtils.isNotEmpty(itemName)) {
            itemQTO.setItemName(itemName);
        }

        if (StringUtils.isNotEmpty(itemGroupUid)) {

            ItemGroupUidDTO itemGroupUidDTO = MopApiUtil.parseGroupUid(itemGroupUid);

            if (itemGroupUidDTO != null) {
                itemQTO.setGroupId(itemGroupUidDTO.getGroupId());
            }
        }

        if (StringUtils.isNotEmpty(itemUidListStr)) {

            try {

                List<String> itemUids = JsonUtil.parseJson(itemUidListStr, new TypeToken<List<String>>() {
                }.getType());

                List<Long> itemIds = Lists.newArrayListWithCapacity(itemUids.size());


                for (String itemUid : itemUids) {
                    ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);
                    itemIds.add(itemUidDTO.getItemId());
                }

                if (itemIds != null && itemIds.size() > 0) {
                    itemQTO.setIdList(itemIds);
                }

            } catch (Exception e) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("item_list", Collections.EMPTY_LIST);
                data.put("total_count", 0);
                return new MopResponse(data);
            }
        }


        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        itemReq.setParam("itemQTO", itemQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<ItemDTO>> itemResp = this.getItemService().execute(itemReq);
        if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ItemDTO> itemDTOs = itemResp.getModule();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemList(itemDTOs));
            data.put("total_count", itemResp.getTotalCount());
            return new MopResponse(data);
        } else {
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/item/normal/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
