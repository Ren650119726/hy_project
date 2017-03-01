package com.mockuai.giftscenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.domain.dto.ActionGiftDTO;
import com.mockuai.giftscenter.common.domain.qto.ActionGiftQTO;
import com.mockuai.giftscenter.core.exception.GiftsException;

/**
 * Created by guansheng on 2016/7/15.
 */
public interface ActionGiftManager {


    void update(ActionGiftQTO actionGiftDTO) throws GiftsException;


    long save(ActionGiftQTO actionGiftDTO) throws GiftsException;

    ActionGiftDTO queryByActionType(int actionType, String appKey) throws GiftsException;





}
