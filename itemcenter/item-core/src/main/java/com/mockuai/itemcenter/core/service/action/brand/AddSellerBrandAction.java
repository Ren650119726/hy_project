package com.mockuai.itemcenter.core.service.action.brand;

import java.util.List;

import javax.annotation.Resource;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.domain.dto.RBrandCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.manager.RBrandCategoryManager;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SellerBrandDTO;
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
public class AddSellerBrandAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(QuerySellerBrandAction.class);
	
	@Resource
	private SellerBrandManager sellerBrandManager;

    @Resource
    private RBrandCategoryManager rBrandCategoryManager;

	@Override
	protected ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		if (request.getParam("sellerBrandDTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerBrandDTO is null");
		}
		SellerBrandDTO sellerBrandDTO = (SellerBrandDTO) request.getParam("sellerBrandDTO");
		try {

            String name = sellerBrandDTO.getBrandName();

            if(Strings.isNullOrEmpty(name)){
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,"品牌名称不能为空");
            }


            SellerBrandQTO query = new SellerBrandQTO();
            query.setBrandName(name);
            query.setBizCode(bizCode);

            List<SellerBrandDTO> brandDTOList = sellerBrandManager.querySellerBrand(query);

            for(SellerBrandDTO cmp : brandDTOList){

                if(cmp.getBrandName().equals(name)){
                    throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_BRAND_NAME_DUPLICATE,"品牌名重复");
                }
            }


			sellerBrandDTO.setBizCode(bizCode);//填充bizCode


			Long id = sellerBrandManager.addSellerBrand(sellerBrandDTO);

			if(sellerBrandDTO.getCategoryIdList()!=null){

				for(Long categoryId : sellerBrandDTO.getCategoryIdList()){

					RBrandCategoryDTO rBrandCategoryDTO = new RBrandCategoryDTO();
					rBrandCategoryDTO.setBizCode(bizCode);
					rBrandCategoryDTO.setBrandId(id);
					rBrandCategoryDTO.setCategoryId(categoryId);

					rBrandCategoryManager.addRBrandCategory(rBrandCategoryDTO);
				}
			}

			sellerBrandDTO.setId(id);
			response = ResponseUtil.getSuccessResponse(sellerBrandDTO);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SELLER_BRAND.getActionName();
	}
}
