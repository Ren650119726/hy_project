package com.mockuai.itemcenter.core.service.action.hotname;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HotNameManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import java.util.List;

/**
 * 添加热搜词
 *
 * @author huangsiqian
 * @version 2016年9月19日 下午5:29:32
 */
@Service
public class AddHotNameAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddHotNameAction.class);
    @Resource
    private HotNameManager hotNameManager;

    @Override
    public String getName() {
        return ActionEnum.ADD_HOTNAME.getActionName();
    }

    @Override
    protected ItemResponse doTransaction(RequestContext context)
            throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        HotNameDTO hotNameDTO = (HotNameDTO) request.getParam("hotNameDTO");
        try {
            Long count = hotNameManager.hotNameCount();
            if (count >= 12) {
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_INVALID, "热门关键词个数超过限制");
            }
            HotNameDTO name= hotNameManager.getHotNameByName(hotNameDTO.getHotName());
            if(null!=name.getHotName()){
                return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_INVALID, "热门关键词重复");
            }
            Boolean flag = hotNameManager.insertHotName(hotNameDTO);
            return ResponseUtil.getSuccessResponse(flag);
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

}
