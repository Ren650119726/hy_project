package com.mockuai.shopcenter.core.service.action.support;

import com.google.common.base.Strings;
import com.mockuai.shopcenter.api.ShopResponse;
import com.mockuai.shopcenter.constant.ActionEnum;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RegionManager;
import com.mockuai.shopcenter.core.service.RequestContext;
import com.mockuai.shopcenter.core.service.ShopRequest;
import com.mockuai.shopcenter.core.service.action.TransAction;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class QueryRegionAction extends TransAction{

    @Resource
    private RegionManager regionManager;

    @Override
    protected ShopResponse doTransaction(RequestContext context) throws ShopException {

        String bizCode = (String) context.get("BizCode");

        ShopRequest request = context.getRequest();

        String appKey = (String) request.getParam("appKey");

        String parentId = (String) request.getParam("parentId");

        if(Strings.isNullOrEmpty(parentId)){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"parentId不能为空");
        }


        List<RegionDTO> regionDTOs = regionManager.queryRegion(parentId,appKey);

        return ResponseUtil.getSuccessResponse(regionDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_REGION.getActionName();
    }
}
