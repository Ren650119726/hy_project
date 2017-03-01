package com.mockuai.shopcenter.core.dao;


import com.mockuai.shopcenter.core.domain.StoreDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.qto.StoreQTO;

import java.util.List;

public interface StoreDAO {

    Long addStore(StoreDO storeDO) throws ShopException;

    Long UpdateStore(StoreDO storeDO) throws ShopException;

    StoreDO getStore(StoreDO query) throws ShopException;

    Long deleteStore(StoreDO query) throws ShopException;

    List<StoreDO> queryStore(StoreQTO storeQTO) throws ShopException;

    Long countQuery(StoreQTO storeQTO);
}