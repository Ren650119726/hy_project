package com.mockuai.itemcenter.core.service.action.itemsku;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.HigoManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查看商品销售属性(ItemSku) Action
 * 
 * @author chen.huangt
 *
 */
@Service
public class GetItemSkuAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetItemSkuAction.class);


	@Resource
	private ItemManager itemManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private SkuPropertyManager skuPropertyManager;

    @Resource
    private HigoManager higoManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemSkuDTO itemSkuDTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String) context.get("bizCode");
		// 验证ID
		//TODO 入参名称重构
		if (request.getLong("ID") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemSkuID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		Long skuId = request.getLong("ID");// 商品销售属性(ItemSku)ID
		Long sellerId = request.getLong("sellerId");// 供应商ID
        String appKey = request.getString("appKey");
		try {
			itemSkuDTO = itemSkuManager.getItemSku(skuId, sellerId, bizCode);
			// 根据skuId和sellerId获取SkuPropertyList
			SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
			skuPropertyQTO.setSellerId(sellerId);
			skuPropertyQTO.setSkuId(skuId);
			List<SkuPropertyDTO> skuPropertyDTOList = skuPropertyManager.querySkuProperty(skuPropertyQTO);
			itemSkuDTO.setSkuPropertyDTOList(skuPropertyDTOList);

			ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(),itemSkuDTO.getSellerId(),bizCode);


            if(itemDTO.getHigoMark()!=null&&itemDTO.getHigoMark().intValue()==1){
                SkuHigoInfoDTO skuHigoInfoDTO = higoManager.getSkuHigoInfo(itemSkuDTO.getId(),itemSkuDTO.getSellerId(),appKey);
                itemSkuDTO.setSkuHigoExtraInfoDTO(ModelUtil.genSkuHigoExtraInfoDTO(skuHigoInfoDTO));
            }

		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
		response = ResponseUtil.getSuccessResponse(itemSkuDTO);
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.GET_ITEM_SKU.getActionName();
	}
}
