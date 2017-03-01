package com.mockuai.distributioncenter.mop.api.action.shop;

import com.mockuai.distributioncenter.common.api.BaseRequest;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 16/5/18.
 */
public class UpdateShopAction extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateShopAction.class);

    public MopResponse execute(Request request) {
        String appKey = (String) request.getParam("app_key");
        String idStr = (String) request.getParam("id");
        String shopName = (String) request.getParam("shop_name");
        String wechatId = (String) request.getParam("wechat_id");
        String shopDesc = (String) request.getParam("shop_desc");

        if (idStr == null) {
            log.error("id is null");
            return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "id is null");
        }

        Long id = Long.parseLong(idStr);

        DistShopDTO distShopDTO = new DistShopDTO();
        distShopDTO.setShopDesc(shopDesc.equals("") ? null : shopDesc);
        distShopDTO.setShopName(shopName.equals("") ? null : shopName);
        distShopDTO.setId(id);
        if (wechatId != null) {
            SellerDTO sellerDTO = new SellerDTO();
            sellerDTO.setWechatId(wechatId.equals("") ? null : wechatId);
            distShopDTO.setSellerDTO(sellerDTO);
        }

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("shopDTO", distShopDTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.UPDATE_SHOP.getActionName());
        Response<Boolean> response = getDistributionService().execute(baseRequest);
        if (!response.isSuccess()) {
            log.error("update shop error, msg: {}", response.getMessage());
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR, response.getMessage());
        }

        return new MopResponse(response.getModule());
    }

    public String getName() {
        return "/dist/shop/update";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
