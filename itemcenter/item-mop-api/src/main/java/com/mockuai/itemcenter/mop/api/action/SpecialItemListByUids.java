package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.io.EOFException;
import java.util.*;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class SpecialItemListByUids extends BaseAction {

    public MopResponse execute(Request request) {

        String itemTypeStr = (String) request.getParam("item_type");
        String itemUidsStr = (String) request.getParam("item_uids");
        String appKey = (String) request.getParam("app_key");

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setSellerId(0L);

        if (StringUtils.isNotEmpty(itemTypeStr) && StringUtils.isNumeric(itemTypeStr)) {
            itemQTO.setItemType(Integer.parseInt(itemTypeStr));
        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "item_type的值不合法");
        }

        Map<Long, Integer> itemIdMap = Maps.newHashMap();

        if (StringUtils.isNotEmpty(itemUidsStr)) {

            try {

                List<String> itemUids = JsonUtil.parseJson(itemUidsStr, new TypeToken<List<String>>() {
                }.getType());

                List<Long> itemIds = Lists.newArrayListWithCapacity(itemUids.size());

                if (itemUids != null && itemUids.size() > 0) {

                    int index = 0;
                    for (String itemUid : itemUids) {
                        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUid);
                        itemIds.add(itemUidDTO.getItemId());
                        itemIdMap.put(itemUidDTO.getItemId(), index);
                        index++;
                    }

                    itemQTO.setIdList(itemIds);

                } else {
                    return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "item_uids的值不合法");
                }

            } catch (Exception e) {
                return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "item_uids的值不合法");
            }

        } else {
            return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "item_uids的值不合法");
        }

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        itemReq.setParam("itemQTO", itemQTO);
        itemReq.setParam("needExtraInfo", "1");
        itemReq.setParam("appKey", appKey);
        Response<List<ItemDTO>> itemResp = this.getItemService().execute(itemReq);

        if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
            List<ItemDTO> itemDTOs = itemResp.getModule();


            //根据入参顺序返回
            Collections.sort(itemDTOs, new NaturalComparator(itemIdMap));

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("item_list", MopApiUtil.genMopItemList(itemDTOs));
            data.put("total_count", itemResp.getTotalCount());
            data.put("time", System.currentTimeMillis());

            return new MopResponse(data);
        } else {
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }


    }

    private class NaturalComparator implements Comparator<ItemDTO> {

        private Map<Long, Integer> itemIdMap;

        public NaturalComparator(Map<Long, Integer> itemIdMap) {
            this.itemIdMap = itemIdMap;
        }

        public int compare(ItemDTO o1, ItemDTO o2) {
            return itemIdMap.get(o1.getId()) - itemIdMap.get(o2.getId());
        }
    }

    public String getName() {
        return "/item/special/list_by_uid";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
