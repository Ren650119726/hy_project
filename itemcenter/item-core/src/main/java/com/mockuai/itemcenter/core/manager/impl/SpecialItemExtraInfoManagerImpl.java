package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Maps;
//import com.mockuai.auctioncenter.client.AuctionClient;
//import com.mockuai.auctioncenter.common.domain.dto.AuctionForMopDTO;
//import com.mockuai.groupbuycenter.client.GroupBuyClient;
//import com.mockuai.groupbuycenter.common.domain.dto.GroupBuyForMopDTO;
//import com.mockuai.groupbuycenter.common.domain.qto.GroupBuyQTO;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpecialItemExtraInfoManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
//import com.mockuai.seckillcenter.client.SeckillClient;
//import com.mockuai.seckillcenter.common.domain.dto.SeckillForMopDTO;
//import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import com.mockuai.seckillcenter.client.SeckillClient;
import com.mockuai.seckillcenter.common.domain.dto.SeckillForMopDTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/12/15.
 */
@Service
public class SpecialItemExtraInfoManagerImpl implements SpecialItemExtraInfoManager {

    //    @Resource
//    private AuctionClient auctionClient;
//
    @Resource
    private SeckillClient seckillClient;
//
//    @Resource
//    private GroupBuyClient groupBuyClient;

    @Override
    public Object getSpecialItemExtraInfo(Long skuId, Long sellerId, Long userId, Integer itemType, String appKey) throws ItemException {

        if (skuId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuId不能为空");
        }

        if (sellerId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
        }

        if (itemType == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemType不能为空");
        }
//        else if (itemType == DBConst.ACTION_ITEM.getCode()) {
//
//
//            try {
//
//                com.mockuai.auctioncenter.common.api.Response<AuctionForMopDTO> response = auctionClient.getAuctionForMopByItem(skuId, userId, sellerId, appKey);
//
//                if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
//                    return response.getModule();
//                } else {
//                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询拍卖信息时出现问题");
//                }
//
//            } catch (Exception e) {
//                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询拍卖信息失败");
//            }
//
//        } else if (itemType == DBConst.SECKILL_ITEM.getCode()) {
//
//            try {
//
//                com.mockuai.seckillcenter.common.api.Response<SeckillForMopDTO> response = seckillClient.querySeckillByItem(skuId, userId, sellerId, appKey);
//
//                if (response.isSuccess()) {
//                    return response.getModule();
//                } else {
//                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息时出现问题");
//                }
//
//            } catch (Exception e) {
//                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息失败");
//            }
//
//        } else if (itemType == DBConst.GROUP_BUY_ITEM.getCode()) {
//
//            try {
//
//                com.mockuai.groupbuycenter.common.api.Response<GroupBuyForMopDTO> response = groupBuyClient.queryGroupBuyByItem(skuId, userId, sellerId, appKey);
//
//                if (response.isSuccess()) {
//                    return response.getModule();
//                } else {
//                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询团购信息时出现问题");
//                }
//
//            } catch (Exception e) {
//                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询团购信息失败");
//            }
//        }
        else {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "不支持的特殊商品类型");
        }


    }

    @Override
    public Map<Long, Object> querySpecialItemExtraInfo(ItemQTO itemQTO, String appKey) throws ItemException {

        if (itemQTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemQTO不能为空");
        }

        if (itemQTO.getLifecycle() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
        }

        Integer itemType = itemQTO.getItemType();
        int lifecycle = itemQTO.getLifecycle();
        int offset = itemQTO.getOffset();
        int count = itemQTO.getPageSize();

        if (itemType == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemType不能为空");
//        }
//        else if (itemType == DBConst.ACTION_ITEM.getCode()) {
//
//            try {
//
//                com.mockuai.auctioncenter.common.api.Response<List<AuctionForMopDTO>> response = auctionClient.queryAuctionForMopByItem(lifecycle, offset, count, appKey);
//
//
//                if (response.getCode() == ResponseCode.SUCCESS.getCode()) {
//
//
//                    Map<Long, Object> result = Maps.newHashMapWithExpectedSize((int) response.getTotalCount());
//
//                    for (AuctionForMopDTO auctionForMopDTO : response.getModule()) {
//
//                        String itemUid = auctionForMopDTO.getItemUid();
//                        Long itemId = Long.parseLong(itemUid.split("_")[1]);
//                        result.put(itemId, auctionForMopDTO);
//                    }
//
//                    itemQTO.setTotalCount((int) response.getTotalCount());
//
//                    return result;
//
//                } else {
//                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询拍卖信息时出现问题");
//                }
//
//            } catch (Exception e) {
//                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询拍卖信息失败");
//            }
//
        } else if (itemType == DBConst.SECKILL_ITEM.getCode()) {

            try {
                SeckillQTO seckillQTO = new SeckillQTO();
                seckillQTO.setLifecycle(lifecycle);
                seckillQTO.setOffset(offset);
                seckillQTO.setCount(count);
                com.mockuai.seckillcenter.common.api.Response<List<SeckillForMopDTO>> response =
                        seckillClient.querySeckillByItemBatch(seckillQTO, appKey);

                if (response.isSuccess()) {
                    Map<Long, Object> result = Maps.newHashMapWithExpectedSize((int) response.getTotalCount());

                    for (SeckillForMopDTO seckillForMopDTO : response.getModule()) {

                        String itemUid = seckillForMopDTO.getItemUid();
                        Long itemId = Long.parseLong(itemUid.split("_")[1]);
                        result.put(itemId, seckillForMopDTO);
                    }

                    itemQTO.setTotalCount((int) response.getTotalCount());

                    return result;
                } else {
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息时出现问题");
                }

            } catch (Exception e) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息失败");
            }
//
//        } else if (itemType == DBConst.GROUP_BUY_ITEM.getCode()) {
//
//            try {
//                GroupBuyQTO groupBuyQTO = new GroupBuyQTO();
//                groupBuyQTO.setLifecycle(lifecycle);
//                groupBuyQTO.setOffset(offset);
//                groupBuyQTO.setCount(count);
//                com.mockuai.groupbuycenter.common.api.Response<List<GroupBuyForMopDTO>> response =
//                        groupBuyClient.queryGroupBuyByItemBatch(groupBuyQTO, appKey);
//
//                if (response.isSuccess()) {
//
//                    Map<Long, Object> result = Maps.newHashMapWithExpectedSize((int) response.getTotalCount());
//                    for (GroupBuyForMopDTO groupBuyForMopDTO : response.getModule()) {
//
//                        String itemUid = groupBuyForMopDTO.getItemUid();
//                        Long itemId = Long.parseLong(itemUid.split("_")[1]);
//                        result.put(itemId, groupBuyForMopDTO);
//                    }
//
//                    itemQTO.setTotalCount((int) response.getTotalCount());
//
//
//                    return result;
//                } else {
//                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询团购信息时出现问题");
//                }
//
//            } catch (Exception e) {
//                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询团购信息失败");
//            }
        } else if (itemType == ItemType.TIME_LIMIT.getType()) {

            try {
                SeckillQTO seckillQTO = new SeckillQTO();
                seckillQTO.setLifecycle(lifecycle);
                seckillQTO.setOffset(offset);
                seckillQTO.setCount(count);
                com.mockuai.seckillcenter.common.api.Response<List<SeckillForMopDTO>> response =
                        seckillClient.querySeckillByItemBatch(seckillQTO, appKey);

                if (response.isSuccess()) {
                    Map<Long, Object> result = Maps.newHashMapWithExpectedSize((int) response.getTotalCount());

                    for (SeckillForMopDTO seckillForMopDTO : response.getModule()) {

                        String itemUid = seckillForMopDTO.getItemUid();
                        Long itemId = Long.parseLong(itemUid.split("_")[1]);
                        result.put(itemId, seckillForMopDTO);
                    }

                    itemQTO.setTotalCount((int) response.getTotalCount());

                    return result;
                } else {
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息时出现问题");
                }

            } catch (Exception e) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "查询秒杀信息失败");
            }
        }else {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "不支持的特殊商品类型");
        }
    }
}
