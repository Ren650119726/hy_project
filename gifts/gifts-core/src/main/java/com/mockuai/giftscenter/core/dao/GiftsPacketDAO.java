package com.mockuai.giftscenter.core.dao;

import java.util.List;

import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;

/**
 * Created by edgar.zr on 12/4/15.1
 */
public interface GiftsPacketDAO {

    Long addGiftsPacket(GiftsPacketDO giftsPacketDO);

    GiftsPacketDO getGiftsPacket(GiftsPacketDO giftsPacketDO);
    
    int updateGiftsPacket(GiftsPacketDO giftsPacketDO);
    
    List<GiftsPacketDO> queryGiftsPacket(GiftsPacketQTO giftsPacketQTO);
    
    List<GiftsPacketDO> appQueryGiftsPacket();
}