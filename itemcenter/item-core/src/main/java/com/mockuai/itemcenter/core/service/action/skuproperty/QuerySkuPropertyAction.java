package com.mockuai.itemcenter.core.service.action.skuproperty;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyValueQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询增加商品属性列表Action
 *
 * @author chen.huang
 */
@Service
public class QuerySkuPropertyAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QuerySkuPropertyAction.class);
    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        if (request.getParam("skuPropertyQTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyQTO is null");
        }

        SkuPropertyQTO skuPropertyQTO = (SkuPropertyQTO) request.getParam("skuPropertyQTO");

        try {
            List<SkuPropertyDTO> skuPropertyDTOList = skuPropertyManager.querySkuProperty(skuPropertyQTO);
            response = ResponseUtil.getSuccessResponse(skuPropertyDTOList, skuPropertyDTOList.size());


            return response;

        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SKU_PROPERTY.getActionName();
    }
}
