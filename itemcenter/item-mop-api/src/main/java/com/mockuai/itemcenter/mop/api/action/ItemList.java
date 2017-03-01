
package com.mockuai.itemcenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemGroupUidDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class ItemList extends BaseAction {


    private static final Logger log = LoggerFactory.getLogger(ItemList.class);

    public MopResponse execute(Request request) {
        String keyword = (String) request.getParam("keyword");
        String categoryIdStr = (String) request.getParam("category_id");
        String brandIdStr = (String) request.getParam("brand_id");
        String orderByStr = (String) request.getParam("order_by");
        String groupUidsStr = (String) request.getParam("item_group_uid");
        String brandUidStr = (String) request.getParam("item_brand_uid");
        String shopIdStr = (String) request.getParam("shop_id");

        String asc = (String) request.getParam("asc");
        String offset = (String) request.getParam("offset");
        String count = (String) request.getParam("count");
        String appKey = (String) request.getParam("app_key");
        String itemUidStr = (String) request.getParam("item_uids");

        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        if (StringUtils.isNotEmpty(offset) && StringUtils.isNumeric(offset)) {
            itemSearchQTO.setOffset(Integer.parseInt(offset));
        }

        if (StringUtils.isNotEmpty(count) && StringUtils.isNumeric(count)) {
            itemSearchQTO.setCount(Integer.parseInt(count));
        }

        if (StringUtils.isNotEmpty(categoryIdStr) && StringUtils.isNumeric(categoryIdStr)) {
            itemSearchQTO.setCategoryId(Long.parseLong(categoryIdStr));
        }

        if (StringUtils.isNotEmpty(brandIdStr) && StringUtils.isNumeric(brandIdStr)) {
            itemSearchQTO.setBrandId(Long.parseLong(brandIdStr));
        }

        if (StringUtils.isNotEmpty(shopIdStr) && StringUtils.isNumeric(shopIdStr)) {
            itemSearchQTO.setShopId(Long.parseLong(shopIdStr));
        }
        if (StringUtils.isNotEmpty(brandUidStr) && StringUtils.isNumeric(brandUidStr)) {
            itemSearchQTO.setBrandId(Long.parseLong(brandUidStr));
        }

        if (StringUtils.isNotEmpty(groupUidsStr)) {
            ItemGroupUidDTO itemGroupUidDTO = MopApiUtil.parseGroupUid(groupUidsStr);

            if (itemGroupUidDTO != null) {

                //通过分组搜索先要找到分组下的所有商品
                com.mockuai.itemcenter.common.api.Request groupRequest = new BaseRequest();
                groupRequest.setCommand(ActionEnum.GET_ITEM_GROUP.getActionName());
                groupRequest.setParam("sellerId", itemGroupUidDTO.getSellerId());
                groupRequest.setParam("groupId",itemGroupUidDTO.getGroupId());
                groupRequest.setParam("appKey",appKey);

                Response<List<Long>> itemResp = this.getItemService().execute(groupRequest);

                if(itemResp.isSuccess()){

                    List<Long> itemIdList = itemResp.getModule();

                    if(CollectionUtils.isEmpty(itemIdList)){
                        Map<String, Object> data = new HashMap<String, Object>();
                        data.put("item_list",Collections.EMPTY_LIST);
                        data.put("total_count", 0L);
                        return new MopResponse(data);
                    }else {

                        List<String> itemUidList = Lists.newArrayList();

                        for(Long itemId : itemIdList){
                            itemUidList.add(MopApiUtil.genItemUid(itemId,itemGroupUidDTO.getSellerId()));
                        }

                        itemSearchQTO.setItemUids(itemUidList);
                    }

                }else {
                    return new MopResponse(itemResp.getCode(),itemResp.getMessage());
                }

            }
        }

        Map<String, Integer> sort = null;

        if (StringUtils.isNotEmpty(itemUidStr)) {

            try {
                List<String> itemUids = JsonUtil.parseJson(itemUidStr, new TypeToken<List<String>>() {
                }.getType());

                if (itemUids != null && itemUids.size() > 0) {
                    itemSearchQTO.setItemUids(itemUids);

                    sort = Maps.newHashMapWithExpectedSize(itemUids.size());

                    for (int i = 0; i < itemUids.size(); i++) {
                        sort.put(itemUids.get(i), i);
                    }
                }
            } catch (Exception e) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("item_list", Collections.EMPTY_LIST);
                data.put("total_count", 0);
                return new MopResponse(data);
            }
        }

        if (StringUtils.isNotEmpty(orderByStr) && StringUtils.isNumeric(orderByStr)) {
            itemSearchQTO.setOrderBy(Integer.parseInt(orderByStr));
        }

        if (StringUtils.isNotEmpty(asc) && StringUtils.isNumeric(asc)) {
            itemSearchQTO.setAsc(Integer.parseInt(asc));
        }


        itemSearchQTO.setKeyword(keyword);





        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.SEARCH_ITEM.getActionName());
        itemReq.setParam("itemSearchQTO", itemSearchQTO);
        itemReq.setParam("appKey", appKey);
        Response<List<ItemSearchDTO>> itemResp = this.getItemService().execute(itemReq);
        if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {

            try {
                List<ItemSearchDTO> itemSearchDTOs = itemResp.getModule();

                if (!CollectionUtils.isEmpty(sort) && !CollectionUtils.isEmpty(itemSearchDTOs)) {
                    final Map<String, Integer> finalSort = sort;
                    Collections.sort(itemSearchDTOs, new Comparator<ItemSearchDTO>() {
                        public int compare(ItemSearchDTO o1, ItemSearchDTO o2) {
                            return (finalSort.get(o1.getItemUid())) - (finalSort.get(o2.getItemUid()));
                        }
                    });
                }
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("item_list", MopApiUtil.genMopItemListForSearchDTO(itemSearchDTOs));
                data.put("total_count", itemResp.getTotalCount());
                //读取品牌的数据
                if (StringUtils.isNotEmpty(brandUidStr) && StringUtils.isNumeric(brandUidStr)) {
                    com.mockuai.itemcenter.common.api.Request itemBrandReq = new BaseRequest();
                    itemBrandReq.setCommand(ActionEnum.GET_BRAND.getActionName());
                    itemBrandReq.setParam("brandId", Long.parseLong(brandUidStr));
                    itemBrandReq.setParam("appKey", appKey);
                    Response<SellerBrandDTO> itemBrandResp = this.getItemService().execute(itemBrandReq);
                    if(itemBrandResp.getCode() == ResponseCode.SUCCESS.getCode()) {
                        data.put("brand",MopApiUtil.genMopBrandDTO(itemBrandResp.getModule()));
                    }


                }
                return new MopResponse(data);
            }catch (Exception e){
                log.error("item list error",e);
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }
        } else {
            log.error("item list error  code {} message {}",Long.valueOf(itemResp.getCode()));
            return new MopResponse(itemResp.getCode(), itemResp.getMessage());
        }
    }

    public String getName() {
        return "/item/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}
