package com.mockuai.itemcenter.core.service.action.skupropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyTmplDAO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
import com.mockuai.itemcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 删除商品属性模板Action
 *
 * @author chen.huang
 */
@Service
public class DeleteSkuPropertyTmplAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(DeleteSkuPropertyTmplAction.class);
    @Resource
    private SkuPropertyTmplManager skuPropertyTmplManager;

    @Resource
    private ItemManager itemManager;

    @Override
    public ItemResponse execute(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();

        String bizCode = (String) context.get("bizCode");

        // 验证ID
        if (request.getLong("skuPropertyTmplId") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplID is missing");
        }

        Long skuPropertyTmplId = request.getLong("skuPropertyTmplId");// 商品品牌ID
        try {
            //TODO 确认属性模板如果被使用的情况下是否可以直接删除

            SkuPropertyTmplDTO skuPropertyTmplDTO = skuPropertyTmplManager.getSkuPropertyTmpl(skuPropertyTmplId);

            Boolean numOfDeleted = skuPropertyTmplManager.deleteSkuPropertyTmpl(skuPropertyTmplId);
            response = ResponseUtil.getSuccessResponse(numOfDeleted);


            // 相关商品下架
            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setBizCode(bizCode);
            itemQTO.setCategoryId(skuPropertyTmplDTO.getCategoryId());
            itemQTO.setSellerId(0L);

            List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

            if (!CollectionUtils.isEmpty(itemDTOList)) {

                for (ItemDTO itemDTO : itemDTOList) {
                    itemManager.skuInvalidItem(itemDTO.getId(), itemDTO.getSellerId(), bizCode);
                }
            }

            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }

    }

    @Override
    public String getName() {
        return ActionEnum.DELETE_SKU_PROPERTY_TMPL.getActionName();
    }
}
