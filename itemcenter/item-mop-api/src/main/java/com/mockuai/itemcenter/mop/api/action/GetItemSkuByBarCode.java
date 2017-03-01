package com.mockuai.itemcenter.mop.api.action;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.mop.api.domain.MopItemSkuDTO;
import com.mockuai.itemcenter.mop.api.domain.SkuUidDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/4/15.
 */
public class GetItemSkuByBarCode extends BaseAction {

    private static final Logger log = LoggerFactory.getLogger(GetItemSkuByBarCode.class);

    public MopResponse execute(Request request) {
        String barCode = (String) request.getParam("bar_code");

        if (Strings.isNullOrEmpty(barCode)) {
            return new MopResponse(ResponseCode.PARAM_E_INVALID.getCode(), "bar_code is invalid");
        }

        String appKey = (String) request.getParam("app_key");

        Long userId = (Long) request.getAttribute("user_id");

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();

        itemSkuQTO.setBarCode(barCode);



        com.mockuai.itemcenter.common.api.Request itemReq = new BaseRequest();
        itemReq.setCommand(ActionEnum.QUERY_ITEM_SKU.getActionName());
        itemReq.setParam("itemSkuQTO",itemSkuQTO);
        itemReq.setParam("userId",userId);
        itemReq.setParam("appKey", appKey);

        try {

            Response<List<ItemSkuDTO>> itemResp = this.getItemService().execute(itemReq);

            List<ItemSkuDTO> itemSkuDTOList = itemResp.getModule();

            if(itemSkuDTOList == null || itemSkuDTOList.size() == 0){
                return new MopResponse(MopRespCode.B_E_RECORD_NOT_EXIST,"查询的sku不存在");
            }

            if (itemResp.getCode() == ResponseCode.SUCCESS.getCode()) {
                Map<String, Object> data = new HashMap<String, Object>();
                MopItemSkuDTO mopItemSkuDTO = MopApiUtil.genMopItemSku(itemSkuDTOList.get(0));

                data.put("item_sku", mopItemSkuDTO);


                return new MopResponse(data);
            } else {
                return new MopResponse(itemResp.getCode(), itemResp.getMessage());
            }

        } catch (Exception e) {
            log.error("barCode=" + barCode, e);
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }

    }

    public String getName() {
        return "/item/item_sku/get_by_bar_code";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
