package com.mockuai.itemcenter.core.service.action.skuproperty;

import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 增加商品属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddSkuPropertyAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddSkuPropertyAction.class);
	@Resource
	private SkuPropertyManager skuPropertyManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	TransactionTemplate transactionTemplate;


	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		final String bizCode = (String)context.get("bizCode");
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
					// 验证skuproperty列表
					if (request.getParam("skuPropertyDTOList") == null) {
						return ResponseUtil
								.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuPropertyDTOList is null");
					}
					// 验证skuId
					if (request.getParam("skuId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "skuId is null");
					}
					// 验证sellerId
					if (request.getParam("sellerId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is null");
					}
					// 验证itemId
					if (request.getParam("itemId") == null) {
						return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemId is null");
					}

					List<SkuPropertyDTO> skuPropertyDTOList = (List<SkuPropertyDTO>) request
							.getParam("skuPropertyDTOList");
					Long skuId = (Long) request.getParam("skuId");
					Long sellerId = (Long) request.getParam("sellerId");
					Long itemId = (Long)request.getParam("itemId");
					
					// 增加skuproperty列表
					if(skuPropertyDTOList != null){
						for(SkuPropertyDTO skuPropertyDTO: skuPropertyDTOList){
							skuPropertyDTO.setBizCode(bizCode);//填充bizCode
						}
					}
					List<SkuPropertyDTO> returnSkuPropertyDTOList = skuPropertyManager
							.addSkuProperty(itemId, skuId, sellerId, skuPropertyDTOList, bizCode);//

					// 根据skuId和sellerId去查找sku_property表，然后将里面的属性拼成code_value更新到item_sku表中
					// 比如将 红色,17码,50g拼接成 "红色-17码-50g"
					SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
					skuPropertyQTO.setSkuId(skuId);
					skuPropertyQTO.setSellerId(sellerId);
					List<SkuPropertyDTO> codeValuePropertyDTOList = skuPropertyManager
							.querySkuProperty(skuPropertyQTO);
					itemSkuManager.updateItemSkuCodeValue(skuId, sellerId, codeValuePropertyDTOList, bizCode);

					response = ResponseUtil.getSuccessResponse(returnSkuPropertyDTOList, returnSkuPropertyDTOList
							.size());
					return response;
				} catch (ItemException e) {
					status.setRollbackOnly();
					log.error(e.toString());
					return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());

				}
			}
		});
		return response;

	}

	@Override
	public String getName() {
		return ActionEnum.ADD_SKU_PROPERTY.getActionName();
	}
}
