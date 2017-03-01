package com.mockuai.itemcenter.core.service.action.itemspec;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SpecManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guansheng on 15/12/7.
 */
@Service
public class AddSpecAction extends TransAction{


    @Resource
    private SpecManager specManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {

        ItemRequest request = context.getRequest();


        SpecDTO specDTO = (SpecDTO) request.getObject("specDTO");
        if(specDTO==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"specDTO不能为空");
        }
        SpecQTO specQTO = new SpecQTO();
        specQTO.setSpecName(specDTO.getSpecName());
        Long countSpec =    specManager.countSpec(specQTO);
        if(countSpec.longValue() > 0){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"商品参数已存在");
        }
        Long id = specManager.addSpec(specDTO);
        return ResponseUtil.getSuccessResponse(id);

    }



    @Override
    public String getName() {
        return ActionEnum.ADD_SPEC.getActionName();
    }
}
