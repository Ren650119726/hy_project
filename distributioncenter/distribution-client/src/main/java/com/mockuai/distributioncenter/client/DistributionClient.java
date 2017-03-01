package com.mockuai.distributioncenter.client;

import java.util.List;
import java.util.Map;

import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.common.domain.qto.*;

/**
 * Created by duke on 15/10/28.
 */
public interface DistributionClient {


    Response<Boolean> updateShopQrcodeUri(DistShopQTO qto, String appKey);


    /**
     * 添加商品级别分拥方案
     * */
    Response<ItemDistPlanDTO> addItemDistPlan(ItemDistPlanDTO itemDistPlanDTO, String appKey);

    /**
     * 通过商品ID获得分拥方案
     * */
    Response<List<ItemDistPlanDTO>> getItemDistPlanByItemId(Long itemId, String appKey);

    /**
     * 更新商品分拥方案
     * */
    Response<Boolean> updateItemDistPlan(ItemDistPlanDTO itemDistPlanDTO, String appKey);

    /**
     * 添加SKU级别的分拥方案
     * */
    Response<ItemSkuDistPlanDTO> addItemSkuDistPlan(ItemSkuDistPlanDTO itemSkuDistPlanDTO, String appKey);

    /**
     * 通过商品SKU获得分拥方案
     * */
    Response<ItemSkuDistPlanDTO> getItemSkuDistPlanBySkuId(Long skuId, String appKey);

    /**
     * 更新商品SKU级别的分拥方案
     * */
    Response<Boolean> updateItemSkuDistPlan(ItemSkuDistPlanDTO itemSkuDistPlanDTO, String appKey);

    /**
     * 通过用户ID获得卖家信息
     * */
    Response<SellerDTO> getSellerByUserId(Long userId, String appKey);

    /**
     * 通过商品ID获得SKU级别的分拥方案
     * */
    Response<List<ItemSkuDistPlanDTO>> getItemSkuDistPlanByItemId(Long itemId, String appKey);

    /**
     * 查询直接下级
     * */
    Response<List<SellerDTO>> queryChildSeller(Long userId, String appKey);

    /**
     * 查询直接上级
     * */
    Response<SellerDTO> getParentSeller(Long userId, String appKey);

    /**
     * 统计人数
     * */
    Response<TeamSummaryDTO> getTeamSummary(Long userId, String appKey);

    /**
     * 查询卖家
     * */
    Response<List<SellerDTO>> querySeller(SellerQTO sellerQTO, String appKey);

    /**
     * 通过卖家ID批量查询店铺
     * */
    Response<List<DistShopDTO>> queryShop(DistShopQTO distShopQTO, String appKey);

    /**
     * 通过卖家ID获得店铺信息
     * */
    Response<DistShopDTO> getShopBySellerId(Long sellerId, String appKey);

    /**
     * 通过用户ID获得店铺信息
     * */
    Response<DistShopDTO> getShopByUserId(Long userId, String appKey);

    /**
     * 通过卖家ID获得店铺信息
     * */
    Response<DistShopForMopDTO> getShopForMopBySellerId(Long sellerId, String appKey);

    /**
     * 创建卖家
     * */
    Response<SellerDTO> createSeller(Long userId, String realName, String mobile, String password, String wechatId, Long inviterSellerId, Integer status, String appKey);

    /**
     * 更新卖家信息
     * */
    Response<Boolean> updateSeller(SellerDTO sellerDTO, String appKey);

    /**
     * 根据邀请码获取分销商信息
     * @param inviterCode
     * @param appKey
     * @return
     */
    Response<SellerDTO> getSellerByInviterCode(String inviterCode, String appKey);

    /**
     * 通过用户ID更新卖家的真实姓名
     * */
    Response<Boolean> updateSellerRealNameByUserId(Long userId, String realName, String appKey);
    
    
    /**
     * 数据恢复
     * */
    Response<Boolean> shuju(Long orderId,String appKey,Long userId);
    
    /**
     * 数据恢复
     * */
    Response<Boolean> shuju2(Long orderId,String appKey,Long userId);
    
    /**
     * 添加级别申请
     * */
    Response<Boolean> addSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey);
    
    /**
     * 判断是否存在申请
     * */
    Response<Map<String, Boolean>> getSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey);
    
    /**
     * 级别申请列表
     * */
    Response<List<SellerLevelApplyDTO>> querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO,String appKey);
    
    /**
     * 修改申请
     * */
    Response<Boolean> updateSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO,String appKey);
    
    /**
     * 分拥统计
     * */
    Response<DistRecordDTO> fenyongTongJi(DistRecordQTO distRecordQTO,String appKey,Long userId);
    
    /**
     * 更新卖家信息
     * */
    Response<Boolean> addRelationship(Long userId,Long parentId, String appKey);
    
    Response<Boolean> userSeller(String appKey);

    /**
     * 通过itemSkuIdList获取分拥方案
     * @param itemSkuDistPlanQTO
     * @param appKey
     * @return
     */
    Response<List<ItemSkuDistPlanDTO>> getDistByItemSkuId (ItemSkuDistPlanQTO itemSkuDistPlanQTO,String appKey);



    Response<List<FansDistDTO>> queryDistListFromFans(String appKey, FansDistQTO fansDistQTO);

}
