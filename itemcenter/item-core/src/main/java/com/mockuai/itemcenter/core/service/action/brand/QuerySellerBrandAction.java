package com.mockuai.itemcenter.core.service.action.brand;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.common.domain.dto.RBrandCategoryDTO;
import com.mockuai.itemcenter.core.domain.RBrandCategoryDO;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.RBrandCategoryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SellerBrandManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 查询商品品牌列表Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QuerySellerBrandAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QuerySellerBrandAction.class);
	
	@Resource
	private SellerBrandManager sellerBrandManager;

	@Resource
	private ItemCategoryManager itemCategoryManager;

	@Resource
	private RBrandCategoryManager rBrandCategoryManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		if (request.getParam("sellerBrandQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerBrandQTO is null");
		}
		SellerBrandQTO sellerBrandQTO = (SellerBrandQTO) request.getParam("sellerBrandQTO");
		sellerBrandQTO.setBizCode(bizCode);

		try {

            Long categoryId = sellerBrandQTO.getCategoryId();

			if(categoryId!=null){
				RBrandCategoryDO rBrandCategoryDO = new RBrandCategoryDO();
				rBrandCategoryDO.setCategoryId(categoryId);

				List<Long> idList = rBrandCategoryManager.queryRBrandCategoryByCateId(categoryId,bizCode);
                sellerBrandQTO.setIdList(idList);
			}

			List<SellerBrandDTO> sellerBrandDTOList = sellerBrandManager.querySellerBrand(sellerBrandQTO);
			response = ResponseUtil.getSuccessResponse(sellerBrandDTOList, sellerBrandQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SELLER_BRAND.getActionName();
	}
}
