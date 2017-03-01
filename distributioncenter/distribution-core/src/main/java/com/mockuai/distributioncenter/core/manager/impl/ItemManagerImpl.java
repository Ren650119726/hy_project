package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ItemManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/3/16.
 */
@Component
public class ItemManagerImpl implements ItemManager {

    final static Logger log = LoggerFactory.getLogger(ItemManagerImpl.class);

    @Autowired
    private ItemClient itemClient;

    @Override
    public List<ItemSearchDTO> searchItem(ItemSearchQTO itemSearchQTO,String appKey) throws DistributionException {
        Response<List<ItemSearchDTO>> response = itemClient.searchItem(itemSearchQTO,appKey);

        if (response.isSuccess()) {

            itemSearchQTO.setTotalCount(response.getTotalCount());
           return response.getModule();
        } else {
            log.error("search item  error, errCode: {}, errMsg: {}, query: {}",
                    response.getCode(), response.getMessage(), JsonUtil.toJson(itemSearchQTO));
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
    }

    @Override
    public ItemDTO getItem(Long id, Long sellerId, String appKey) throws DistributionException {
        Response<ItemDTO> response = itemClient.getItem(id, sellerId, false, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("get item error, errCode: {}, errMsg: {}, itemId: {}, sellerId: {}",
                    response.getCode(), response.getMessage(), id, sellerId);
            throw new DistributionException(ResponseCode.INVOKE_SERVICE_EXCEPTION);
        }
    }
}
