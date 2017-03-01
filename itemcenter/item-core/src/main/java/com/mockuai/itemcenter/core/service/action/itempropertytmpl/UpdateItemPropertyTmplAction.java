package com.mockuai.itemcenter.core.service.action.itempropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyValueManager;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.core.manager.ItemPropertyTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 更新商品属性模板Action
 * 
 * @author chen.huang
 *
 */

@Service
public class UpdateItemPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(UpdateItemPropertyTmplAction.class);
	@Resource
	private ItemPropertyTmplManager itemPropertyTmplManager;


    @Resource
    private ItemPropertyValueManager itemPropertyValueManager;


	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();

        String bizCode = (String) context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemPropertyTmplDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemPropertyTmplDTO is null");
		}
		ItemPropertyTmplDTO itemPropertyTmplDTO = (ItemPropertyTmplDTO) request.getParam("itemPropertyTmplDTO");
        itemPropertyTmplDTO.setBizCode(bizCode);
		try {
			Boolean isSuccessfullyUpdated = itemPropertyTmplManager.updateItemPropertyTmpl(itemPropertyTmplDTO);

			Long propertyTmplId = itemPropertyTmplDTO.getId();


            ItemPropertyValueDO valueDO = new ItemPropertyValueDO();
            valueDO.setItemPropertyTmplId(propertyTmplId);

            itemPropertyValueManager.deleteItemPropertyValues(valueDO);

            if(itemPropertyTmplDTO.getPropertyValues() != null){
                List<ItemPropertyValueDO> itemPropertyValueDOs =
                        ModelUtil.genItemPropertyValueDOList(itemPropertyTmplDTO.getPropertyValues());

                //给属性值列表填充itemPropertyTmplId和bizCode信息
                for(ItemPropertyValueDO itemPropertyValueDO: itemPropertyValueDOs){
                    itemPropertyValueDO.setItemPropertyTmplId(propertyTmplId);
                    itemPropertyValueDO.setBizCode(bizCode);
                }

                if(itemPropertyValueDOs!=null && itemPropertyValueDOs.isEmpty()==false){
                    itemPropertyValueManager.addItemPropertyValues(itemPropertyValueDOs);
                }
            }

			response = ResponseUtil.getSuccessResponse(isSuccessfullyUpdated);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}catch (Exception e){
            return ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR);
		}

	}

	@Override
	public String getName() {
		return ActionEnum.UPDATE_ITEM_PROPERTY_TMPL.getActionName();
	}
}
