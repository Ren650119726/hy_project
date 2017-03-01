package com.mockuai.shopcenter.core.service.action.shop;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.RItemGroupQTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by luliang on 15/7/31.
 */
@Service
public class QueryShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryShopItemGroupAction.class);

    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private RItemGroupManager rItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {

        ShopResponse shopResponse = null;
        ShopRequest shopRequest = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ShopItemGroupQTO shopItemGroupQTO = (ShopItemGroupQTO) shopRequest.getParam("shopItemGroupQTO");
        if(shopItemGroupQTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopItemGroupQTO is null");
        }

        List<ShopItemGroupDTO> shopItemGroupDTOList = null;
        try {
            shopItemGroupQTO.setBizCode(bizCode);
            shopItemGroupDTOList = shopItemGroupManager.queryShopItemGroup(shopItemGroupQTO);

            if(!CollectionUtils.isEmpty(shopItemGroupDTOList)){

                List<Long> groupIdList = Lists.newArrayList();
                for(ShopItemGroupDTO shopItemGroupDTO : shopItemGroupDTOList){
                    groupIdList.add(shopItemGroupDTO.getId());
                }

                Long sellerId = shopItemGroupQTO.getSellerId()== null ? 0L : shopItemGroupQTO.getSellerId();

                Map<Long,ShopItemGroupDTO> map = rItemGroupManager.queryGroupItemCount(groupIdList,shopItemGroupQTO.getSellerId(),bizCode);

                for(ShopItemGroupDTO shopItemGroupDTO : shopItemGroupDTOList){

                    ShopItemGroupDTO dto = map.get(shopItemGroupDTO.getId());
                    shopItemGroupDTO.setCount(dto==null?0:dto.getCount());
                }
            }

        } catch (ShopException e) {
            shopResponse = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + shopRequest.getCommand() + " occur Exception:" + e.getMessage(), e);
            return shopResponse;
        }
        shopResponse = ResponseUtil.getSuccessResponse(shopItemGroupDTOList, shopItemGroupQTO.getTotalCount());
        return shopResponse;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_SHOP_ITEM_GROUP.getActionName();
    }
}
