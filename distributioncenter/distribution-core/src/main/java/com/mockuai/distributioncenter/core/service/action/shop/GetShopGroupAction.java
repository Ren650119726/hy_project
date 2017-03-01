package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 冠生 on 5/20/16.
 * 查询店铺商品分组的
 */
@Service
public class GetShopGroupAction implements Action{
    private static final Logger log = LoggerFactory.getLogger(GetShopGroupAction.class);
     @Autowired
     private ShopManager shopManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        String appKey = (String) request.getParam("appKey");


        try {
            List<ShopItemGroupDTO> itemList =   shopManager.queryShopItemGroup(appKey);
            List<Map<String,Object>> resultList = new ArrayList<Map<String, Object>>();
            for(ShopItemGroupDTO shopItemGroupDTO : itemList){
                Map<String,Object> paramMap = new HashMap<String, Object>();
                paramMap.put("id",shopItemGroupDTO.getId());
                paramMap.put("sellerId",shopItemGroupDTO.getSellerId());
                paramMap.put("groupName",shopItemGroupDTO.getGroupName());
                resultList.add(paramMap);
            }
            return new DistributionResponse(resultList);
        }catch (DistributionException e){
            log.error("Action failed {}", getName());
            return new DistributionResponse(e.getCode(), e.getMessage());

        }
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_GROUP.getActionName();
    }
}
