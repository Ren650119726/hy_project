package com.mockuai.shopcenter.core.service.action.shop;

import com.google.common.base.Strings;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.constant.BizTypeEnum;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.AppManager;
import com.mockuai.shopcenter.core.manager.RItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopItemGroupManager;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.Action;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 添加分组;
 * Created by luliang on 15/7/28.
 */
@Service
public class AddShopItemGroupAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(AddShopItemGroupAction.class);
    @Resource
    private ShopItemGroupManager shopItemGroupManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private AppManager appManager;

    @Resource
    private RItemGroupManager rItemGroupManager;

    @Override
    public ShopResponse execute(RequestContext context) throws ShopException {
        ShopResponse response = null;
        ShopRequest request = context.getRequest();
        String bizCode = (String) context.get("bizCode");
        ShopItemGroupDTO shopItemGroupDTO = (ShopItemGroupDTO) request.getParam("shopItemGroupDTO");
        if (shopItemGroupDTO == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "shopItemGroupDTO is null");
        }
        Long sellerId = shopItemGroupDTO.getSellerId();

        BizInfoDTO bizInfoDTO = appManager.getBizInfo(bizCode);
        BizPropertyDTO bizPropertyDTO = bizInfoDTO.getBizPropertyMap().get(BizPropertyKey.IS_MULTI_MALL);


        if (bizPropertyDTO != null && bizPropertyDTO.getValue().equals("1")) {  //多店铺时才填充店铺id;
            if (sellerId != null) {
                // 去反查shopId,设计的字段填充进去;
                ShopDTO shopDTO = shopManager.getShop(sellerId);
                shopItemGroupDTO.setShopId(shopDTO.getId());
            }
        }else{
            shopItemGroupDTO.setShopId(0L);
        }

        try {
            shopItemGroupDTO.setBizCode(bizCode);
            Long id = shopItemGroupManager.addShopItemGroup(shopItemGroupDTO);


            shopItemGroupDTO.setId(id);


            ShopItemGroupQTO query = new ShopItemGroupQTO();
            query.setGroupName(shopItemGroupDTO.getGroupName());


            if(shopItemGroupDTO.getItemIdList()!=null){
                for(Long itemId : shopItemGroupDTO.getItemIdList()){
                    RItemGroupDTO rItemGroupDTO = new RItemGroupDTO();
                    rItemGroupDTO.setItemId(itemId);
                    rItemGroupDTO.setSellerId(sellerId);
                    rItemGroupDTO.setGroupId(id);
                    rItemGroupDTO.setBizCode(bizCode);

                    rItemGroupManager.addRItemGroup(rItemGroupDTO);
                }
            }

            response = ResponseUtil.getSuccessResponse(shopItemGroupDTO);
            return response;
        } catch (ShopException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_SHOP_ITEM_GROUP.getActionName();
    }
}
