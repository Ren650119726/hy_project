package com.mockuai.itemcenter.mop.api.action;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.domain.PropertyDTO;
import com.mockuai.itemcenter.mop.api.util.JsonUtil;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.marketingcenter.common.domain.dto.MopCombineDiscountDTO;
import com.mockuai.marketingcenter.common.domain.dto.mop.MopDiscountInfo;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class GetItem extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(GetItem.class);

    private static final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public MopResponse execute(Request request) {


        DateTime start = DateTime.now();
        DateTime point1 = start;
        DateTime point2 = start;
        DateTime point3 = start;

        //开始记录的时间点

        String itemUidStr = (String) request.getParam("item_uid");
        String appKey = (String) request.getParam("app_key");

        Long userId = (Long) request.getAttribute("user_id");
        //Long distributorId = null;

        /* String distributorIdStr = (String) request.getParam("distributor_id");

       if (StringUtils.isBlank(distributorIdStr) == false) {
            distributorId = Long.valueOf(distributorIdStr);
        }*/

        ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUidStr);
        if (itemUidDTO == null) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "item_uid is invalid");
        }

        /*if (distributorId == null) {
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "distributor_id不能为空");
        }*/

        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.GET_ITEM.getActionName());
        itemReq.setParam("id", itemUidDTO.getItemId());
        itemReq.setParam("supplierId", itemUidDTO.getSellerId());
        itemReq.setParam("userId", userId);
        itemReq.setParam("needDetail", true);
        //itemReq.setParam("distributorId", distributorId);
        itemReq.setParam("appKey", appKey);

        try {
            Response<ItemDTO> itemResp = this.getItemService().execute(itemReq);

            if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
                Map<String, Object> data = new HashMap<String, Object>();
                MopItemDTO mopItemDTO = MopApiUtil.genMopItem(itemResp.getModule());

                //第二个时间点,请求商品详情
                point1 = DateTime.now();

                try {

                    ItemDTO itemDTO = itemResp.getModule();

                    ItemDTO query = new ItemDTO();
                    query.setId(itemDTO.getId());
                    query.setSellerId(itemDTO.getSellerId());
                    query.setItemType(itemDTO.getItemType());
                    query.setPromotionPrice(itemDTO.getPromotionPrice());
                    query.setVirtualMark(itemDTO.getVirtualMark());

                    //查询优惠信息
                    com.mockuai.itemcenter.common.api.Request itemReqx1 = new BaseRequest();
                    itemReqx1.setParam("itemDTO", query);
                    itemReqx1.setParam("appKey", appKey);
                    itemReqx1.setCommand(ActionEnum.QUERY_ITEM_DISCOUNT_INFO.getActionName());
                    Response<MopCombineDiscountDTO> response = getItemService().execute(itemReqx1);

                    if (response.getModule() != null) {
                        BeanUtils.copyProperties(response.getModule(), mopItemDTO);
                    }

                    //第三个时间点,优惠信息
                    point2 = DateTime.now();


                    //兼容老版本
                    List<MopDiscountInfo> discountInfoList = Lists.newArrayList();
                    MopCombineDiscountDTO mopCombineDiscountDTO = response.getModule();
                    if( !CollectionUtils.isEmpty(mopCombineDiscountDTO.getCompositeActivityList())){
                        discountInfoList.addAll(mopCombineDiscountDTO.getCompositeActivityList());
                    }
                    if( !CollectionUtils.isEmpty(mopCombineDiscountDTO.getDiscountInfoList())){
                        discountInfoList.addAll(mopCombineDiscountDTO.getDiscountInfoList());
                    }
                    if( !CollectionUtils.isEmpty(mopCombineDiscountDTO.getTimeLimitList())){
                        discountInfoList.addAll(mopCombineDiscountDTO.getDiscountInfoList());
                    }
                    mopItemDTO.setDiscountInfoList(discountInfoList);


                    //暂不开放的
                    mopItemDTO.setBarterList(Collections.EMPTY_LIST);
                    mopItemDTO.setSuitList(Collections.EMPTY_LIST);
                    mopItemDTO.setCompositeActivityList(Collections.EMPTY_LIST);

                } catch (Exception e) {
                    //降级处理
                    log.error("itemUid=" + itemUidStr, e);
                }

                ItemDTO itemDTO = itemResp.getModule();
                Integer itemType = itemDTO.getItemType();


                data.put("time", System.currentTimeMillis());
                data.put("item", mopItemDTO);

                //TODO 收藏状态判断逻辑，性能待优化
                //如果用户已登录，则需要判断商品的收藏状态
                boolean isCollected = false;
                if (userId != null) {
                    ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
                    itemCollectionQTO.setUserId(userId);
                    itemCollectionQTO.setItemId(itemResp.getModule().getId());
                   // itemCollectionQTO.setDistributorId(distributorId);
                    itemReq = new BaseRequest();
                    itemReq.setCommand(ActionEnum.QUERY_ITEM_COLLECTION.getActionName());
                    itemReq.setParam("itemCollectionQTO", itemCollectionQTO);
                    itemReq.setParam("appKey", appKey);
                    Response<List<ItemDTO>> queryItemCollectResp = this.getItemService().execute(itemReq);
                    List<PropertyDTO> bizPropertyDTOList = mopItemDTO.getBizPropertyList();
                    if (bizPropertyDTOList == null) {
                        bizPropertyDTOList = new ArrayList<PropertyDTO>(1);
                        mopItemDTO.setBizPropertyList(bizPropertyDTOList);
                    }
                    PropertyDTO bizProperty = new PropertyDTO();
                    bizPropertyDTOList.add(bizProperty);
                    bizProperty.setName("collect_status");
                    bizProperty.setCode("IC_SYS_P_BIZ_000001");
                    //TODO valueType取值列表要明确定下来
                    bizProperty.setValueType(1);
                    if (queryItemCollectResp.getCode() == ResponseCode.SUCCESS.getCode()
                            && queryItemCollectResp.getTotalCount() > 0) {
                        bizProperty.setValue("1");
                    } else {
                        bizProperty.setValue("0");
                    }
                }

                //第四个时间点,商品收藏
                point3 = DateTime.now();
//
//                com.mockuai.itemcenter.common.api.Request itemReqx = new BaseRequest();
//                itemReqx.setCommand(ActionEnum.GET_STORE_SUPPORT_CONFIG.getActionName());
//                itemReqx.setParam("key", "support_store");
//                itemReqx.setParam("sellerId", itemUidDTO.getSellerId());
//                itemReqx.setParam("appKey", appKey);
//
//                Response<String> itemResponse = getItemService().execute(itemReqx);
//
//                if (itemResponse.getCode() == ResponseCode.SUCCESS.getCode()) {
//                    data.put("support_store", itemResponse.getModule());
//                }

                log.info("item get time statistics start {} point1 {} point2 {} point3 {} interval1 {} interval2 {} interval 3 {}",
                new Object[]{start.toString(format),point1.toString(format),point2.toString(format),point3.toString(format),
                point1.getMillis()-start.getMillis(),point2.getMillis()-point1.getMillis(),point3.getMillis()-point2.getMillis()});
                return new MopResponse(data);
            } else {
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }

        } catch (Exception e) {
            log.error("itemUid=" + itemUidStr, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }

    }

    public String getName() {
        return "/item/get";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

    public static void main(String[] args) {
        MopItemDTO mopItemDTO = new MopItemDTO();
        List<PropertyDTO> bizPropertyList = mopItemDTO.getBizPropertyList();
        bizPropertyList = new ArrayList<PropertyDTO>();
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setCode("123");
        bizPropertyList.add(propertyDTO);
        mopItemDTO.setBizPropertyList(bizPropertyList);
        System.out.println(JsonUtil.toJson(mopItemDTO));
    }

}
