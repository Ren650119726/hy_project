package com.mockuai.itemcenter.core.filter.impl;

import com.google.common.collect.Maps;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.filter.Filter;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.NotifyManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class GiftsFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(GiftsFilter.class);

    @Override
    public boolean isAccept(RequestContext ctx) {
        return true;
    }

    @Override
    public ItemResponse before(RequestContext ctx) throws ItemException {

        //捕获所有异常，防止消息推送流程出现的问题影响到正常的流程
        try {


            String command = ctx.getRequest().getCommand();

            String action = ActionEnum.getActionEnum(command).getActionName();

            if (action.equals(ActionEnum.UPDATE_ITEM.getActionName())) {

                ItemDTO itemDTO = (ItemDTO) ctx.getRequest().getObject("itemDTO");


                if (itemDTO.getItemType() != null && itemDTO.getItemType().equals(ItemType.GIFT_PACKS.getType())) {

                    if (itemDTO.getItemSkuDTOList() != null) {
                        for (ItemSkuDTO itemSkuDTO : itemDTO.getItemSkuDTOList()) {

                            if (itemSkuDTO.getId() != 0) {
                                itemSkuDTO.setPromotionPrice(null);
                                itemSkuDTO.setMarketPrice(null);
                            }
                        }
                    }
                }

                //删除商品对应的分佣

            }

        } catch (Exception e) {
            log.error("", e);
            return new ItemResponse(ResponseCode.SYS_E_DEFAULT_ERROR, "礼包商品修改失败");
        }

        return new ItemResponse(ResponseCode.SUCCESS);
    }

    @Override
    public ItemResponse after(RequestContext ctx) throws ItemException {
        return new ItemResponse(ResponseCode.SUCCESS);
    }
}
