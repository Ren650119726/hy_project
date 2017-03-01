package com.mockuai.deliverycenter.mop.api.action;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.mop.api.util.ModelUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class SubRegionListAction extends BaseAction{

    public MopResponse execute(Request request) {
        String regionCode = (String)request.getParam("region_code");
        String offsetStr = (String)request.getParam("offset");
        String countStr = (String)request.getParam("count");


        com.mockuai.deliverycenter.common.api.Request deliveryRequest = new BaseRequest();
        deliveryRequest.setCommand(ActionEnum.QUERY_REGION.getActionName());
        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setParentCode(regionCode);
        //TODO 分页参数重构
        deliveryRequest.setParam("regionQTO", regionQTO);
        String appKey = (String)request.getParam("app_key");
		deliveryRequest.setParam("appKey", appKey);
        //获取物流明细信息
        Response<List<RegionDTO>> deliveryResp = this.getDeliveryService().execute(deliveryRequest);
        if(deliveryResp.isSuccess()){
            Map<String,Object> data = new HashMap<String, Object>();
            data.put("region_list", ModelUtil.genMopRegionDTOList(deliveryResp.getModule()));
            data.put("total_count", deliveryResp.getTotalCount());
            return new MopResponse(data);
        }else{
            return new MopResponse(deliveryResp.getCode(), deliveryResp.getMessage());
        }

    }

    public String getName() {
        return "/delivery/sub_region/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }

}
