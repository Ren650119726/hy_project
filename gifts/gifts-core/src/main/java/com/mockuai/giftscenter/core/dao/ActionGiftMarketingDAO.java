package com.mockuai.giftscenter.core.dao;

import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.domain.ActionGiftMarketingDO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;

import java.util.List;

/**
 * Created by guansheng  15/7/16
 */
public interface ActionGiftMarketingDAO {

   void insert(List<ActionGiftMarketingDO> data);

    List<ActionGiftMarketingDO> queryByActionId(long actionId);

    void deleteByActionId(long actionId);
}