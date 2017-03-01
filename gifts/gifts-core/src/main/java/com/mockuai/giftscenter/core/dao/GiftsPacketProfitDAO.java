package com.mockuai.giftscenter.core.dao;

import java.util.List;

import com.mockuai.giftscenter.core.domain.GiftsPacketDO;
import com.mockuai.giftscenter.core.domain.GiftsPacketProfitDO;

/**
 * Created by edgar.zr on 12/4/15.
 */
public interface GiftsPacketProfitDAO {

    Long addGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO);

    List<GiftsPacketProfitDO> queryGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO);
    
    int updateGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO);
    
    GiftsPacketProfitDO getGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO);
}