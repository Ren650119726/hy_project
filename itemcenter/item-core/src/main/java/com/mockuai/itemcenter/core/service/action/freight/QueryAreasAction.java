package com.mockuai.itemcenter.core.service.action.freight;


import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.RegionManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/10/19.
 */
@Service
public class QueryAreasAction extends TransAction{

    @Resource
    private RegionManager regionManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {


        String appKey = (String) context.getRequest().getParam("appKey");

        List<AreaDTO> areaDTOs = regionManager.queryAreas(appKey);


        return ResponseUtil.getSuccessResponse(areaDTOs);


    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_AREAS_ACTION.getActionName();
    }


}
