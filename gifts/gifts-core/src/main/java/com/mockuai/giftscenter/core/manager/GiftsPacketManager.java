package com.mockuai.giftscenter.core.manager;

import java.util.List;

import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.exception.GiftsException;

/**
 * Created by edgar.zr on 12/4/15.
 */
public interface GiftsPacketManager {

    /**
     * 
     * 添加礼包
     */
    void addGifts(GiftsPacketDTO giftsPacketDTO,String appKey , Long sellerId) throws GiftsException;

    /**
     * 礼包详情
     */
    GiftsPacketDTO getGifts(Long id) throws GiftsException;


    /**
     * 修改
     */
    int updateGiftsPacket(GiftsPacketDTO giftsPacketDTO,String appKey,Long sellerId) throws GiftsException;
    
    /**
     * 删除
     */
    int deleteGiftsPacket(GiftsPacketDTO giftsPacketDTO,String appKey) throws GiftsException;
    
    
    /**
     * 礼包列表
     */
    List<GiftsPacketDTO> queryGiftsPacket(GiftsPacketQTO giftsPacketQTO) throws GiftsException;
    
    /**
     * 获取礼包分拥比例
     */
    GiftsPacketDTO itemGifts(GiftsPacketDTO giftsPacketDTO) throws GiftsException;
    
    /**
     * 前台获取有效礼包列表
     */
    List<GiftsPacketDTO> appQueryGiftsPacket(String appKey) throws GiftsException;
    
    
    /**
     * 前台获取有效礼包列表
     */
    GiftsPacketProfitDTO giftsPoints (Long itemID,Long levelId) throws GiftsException;
    
}