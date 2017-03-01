package com.mockuai.giftscenter.client;


import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.domain.dto.*;
import com.mockuai.giftscenter.common.domain.qto.ActionGiftQTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;

import java.util.List;

/**
 * Created by edgar.zr on 12/2/15.
 */
public interface GiftsClient {

    /**
     * 创建礼包
     *
     * @param giftsPacketDTO
     * @return
     */
    Response<Long> addGifts(GiftsPacketDTO giftsPacketDTO, String appKey,Long sellerId);

    /**
     * 获取礼包 
     *
     * @param itemId,itemSkuId
     * @return
     */
    Response<GiftsPacketDTO> getGiftsItem(Long  itemId,Long itemSkuId, String appKey);
    
    /**
     * 修改礼包
     *
     * @param giftsPacketDTO
     * @return
     */
    Response<Integer> updateGifts(GiftsPacketDTO giftsPacketDTO, String appKey,Long sellerId);
    
    /**
     * 删除礼包 
     *
     * @param giftsPacketDTO
     * @return
     */
    Response<Integer> deleteGifts(GiftsPacketDTO giftsPacketDTO, String appKey);
    
    /**
     * 列表查询 
     *
     * @param giftsPacketDTO
     * @return
     */
    Response<List<GiftsPacketDTO>> queryGifts(GiftsPacketQTO giftsPacketQTO, String appKey);
    
    /**
     * 获取
     *
     * @param giftsPacketDTO
     * @return
     */
    Response<GiftsPacketDTO> getGifts(Long  giftsID, String appKey);
    
    /**
     * 获取分拥比例
     *
     * @param itemId
     * @param levelId
     * @return
     */
    Response<GiftsPacketProfitDTO> giftsPoints(Long itemId,Long levelId, String appKey);

    Response<Void> grantActionGift(Long receiverId,String mobile ,int appType,int actionType, String appKey);


     Response<ActionGiftDTO> getActionGift(int actionType, String appKey) ;

    Response<Long> editActionGift(ActionGiftQTO actionGiftQTO, String appKey) ;

    Response<PageDTO<List<GrantCouponRecordDTO>>> queryGrantCouponRecord(GrantCouponRecordQTO grantCouponRecordQTO, String appKey);
     }