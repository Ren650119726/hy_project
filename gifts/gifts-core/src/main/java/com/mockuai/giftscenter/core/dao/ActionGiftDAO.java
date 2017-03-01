package com.mockuai.giftscenter.core.dao;

import com.mockuai.giftscenter.core.domain.ActionGiftDO;

/**
 * Created by guansheng  15/7/16
 */
public interface ActionGiftDAO {

   Long insert(ActionGiftDO actionGiftDO);

   ActionGiftDO  getByActionType(int actionType);

   void update(ActionGiftDO actionGiftDO);
}