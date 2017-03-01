package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CompositeItemManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 *
 * @author chen.huang
 *  按skuId 查找itemId
 */
@Service
public class QueryCompositeBySkuIdAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(QueryCompositeBySkuIdAction.class);

    @Resource
    private CompositeItemManager compositeItemManager;




    public ItemResponse doTransaction(final RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        ItemSkuQTO itemSkuQTO  = (ItemSkuQTO) request.getParam("itemSkuQTO");
        if(itemSkuQTO == null){
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING);
        }
        List<CompositeItemDTO> compositeItemDTOList = compositeItemManager.queryCompositeItemByItemSkuQTO(itemSkuQTO);
        //TODO 删除商品和删除索引事务性保证逻辑;删除索引异步化
        //删除商品在搜索引擎中的索引
        response = ResponseUtil.getSuccessResponse(compositeItemDTOList);
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_COMPOSITE_SKU_ID.getActionName();
    }
}
