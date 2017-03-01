package com.mockuai.itemcenter.core.service.action.skupropertytmpl;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyTmplManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyValueManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 增加商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddSkuPropertyTmplAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddSkuPropertyTmplAction.class);
	@Resource
	private SkuPropertyTmplManager skuPropertyTmplManager;

	@Resource
	private SkuPropertyValueManager skuPropertyValueManager;

	@Resource
	private ItemManager itemManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String) context.get("bizCode");

		// 验证DTO是否为空
		if (request.getParam("skuPropertyTmplDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyTmplDTO is null");
		}
		SkuPropertyTmplDTO skuPropertyTmplDTO = (SkuPropertyTmplDTO) request.getParam("skuPropertyTmplDTO");
		try {
			skuPropertyTmplDTO.setBizCode(bizCode);//填充bizCode
			Long skuPropertyTmplId = skuPropertyTmplManager.addSkuPropertyTmpl(skuPropertyTmplDTO);// 新增加的skuPropertyTmplDO
			//如果可选值列表不为空，则添加
			if(skuPropertyTmplDTO.getPropertyValues() != null){
				List<SkuPropertyValueDO> skuPropertyValueDOs =
						ModelUtil.genSkuPropertyValueDOList(skuPropertyTmplDTO.getPropertyValues());
				//给属性值列表填充skuPropertyTmplId和bizCode信息
				for(SkuPropertyValueDO skuPropertyValueDO: skuPropertyValueDOs){
					skuPropertyValueDO.setSkuPropertyTmplId(skuPropertyTmplId);
					skuPropertyValueDO.setBizCode(bizCode);//填充bizCode
				}

				if(skuPropertyValueDOs!=null && skuPropertyValueDOs.isEmpty()==false){
					skuPropertyValueManager.addSkuPropertyValues(skuPropertyValueDOs);
				}
			}


			// 相关商品下架
			ItemQTO itemQTO = new ItemQTO();
			itemQTO.setBizCode(bizCode);
			itemQTO.setCategoryId(skuPropertyTmplDTO.getCategoryId());
			itemQTO.setSellerId(0L);

			List<ItemDTO> itemDTOList = itemManager.queryItem(itemQTO);

			if(!CollectionUtils.isEmpty(itemDTOList)){

				for(ItemDTO itemDTO : itemDTOList){
					itemManager.skuInvalidItem(itemDTO.getId(), itemDTO.getSellerId(), bizCode);
				}
			}

			response = ResponseUtil.getSuccessResponse(skuPropertyTmplId);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SKU_PROPERTY_TMPL.getActionName();
	}
}
