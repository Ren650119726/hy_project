package com.mockuai.itemcenter.core.service.action.brand;

import javax.annotation.Resource;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.domain.dto.RBrandCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.manager.RBrandCategoryManager;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UpdateSellerBrandAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(QuerySellerBrandAction.class);

    @Resource
    private SellerBrandManager sellerBrandManager;


    @Resource
    private RBrandCategoryManager rBrandCategoryManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();
        if (request.getParam("sellerBrandDTO") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerBrandDTO is null");
        }

        String updateCategory = (String) request.getParam("updateCategory");
        String bizCode = (String) context.get("bizCode");

        SellerBrandDTO sellerBrandDTO = (SellerBrandDTO) request.getParam("sellerBrandDTO");

        Long brandId = sellerBrandDTO.getId();
        try {

            String name = sellerBrandDTO.getBrandName();

            if(Strings.isNullOrEmpty(name)){
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "品牌名称不能为空");
            }

            if(sellerBrandDTO.getId()==null){
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "品牌id不能为空");
            }


            SellerBrandQTO query = new SellerBrandQTO();
            query.setBrandName(name);
            query.setBizCode(bizCode);

            List<SellerBrandDTO> brandDTOList = sellerBrandManager.querySellerBrand(query);

            for(SellerBrandDTO cmp : brandDTOList){

                if((cmp.getId().longValue()!=sellerBrandDTO.getId().longValue())&&(cmp.getBrandName().equals(name))){
                    throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_BRAND_NAME_DUPLICATE,"品牌名重复");
                }
            }

            int result = sellerBrandManager.updateSellerBrand(sellerBrandDTO);

            if (null != updateCategory && "1".equals(updateCategory)) {

                rBrandCategoryManager.deleteRBrandCategoryByBrandId(brandId,bizCode);

                if(!CollectionUtils.isEmpty(sellerBrandDTO.getCategoryIdList())) {
                    for (Long categoryId : sellerBrandDTO.getCategoryIdList()) {

                        RBrandCategoryDTO rBrandCategoryDTO = new RBrandCategoryDTO();
                        rBrandCategoryDTO.setBizCode(bizCode);
                        rBrandCategoryDTO.setBrandId(brandId);
                        rBrandCategoryDTO.setCategoryId(categoryId);

                        rBrandCategoryManager.addRBrandCategory(rBrandCategoryDTO);
                    }
                }
            }
            if (result > 0) {
                response = ResponseUtil.getSuccessResponse(true);
            } else {
                response = ResponseUtil.getErrorResponse(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST);
            }
            return response;
        } catch (ItemException e) {
            response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
            return response;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_SELLER_BRAND.getActionName();
    }

}
