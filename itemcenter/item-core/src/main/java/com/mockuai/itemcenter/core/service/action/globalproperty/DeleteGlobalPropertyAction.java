package com.mockuai.itemcenter.core.service.action.globalproperty;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.GlobalPropertyManager;
import com.mockuai.itemcenter.core.manager.GlobalPropertyValueManager;
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
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除全局属性Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteGlobalPropertyAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(DeleteGlobalPropertyAction.class);
	@Resource
	private GlobalPropertyManager globalPropertyManager;

	@Resource
	private GlobalPropertyValueManager globalPropertyValueManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public ItemResponse doTransaction(final RequestContext context) throws ItemException {
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
					// 验证ID
					if (request.getLong("ID") == null) {
						return ResponseUtil
								.getErrorResponse(ResponseCode.PARAM_E_MISSING, "globalPropertyID is missing");
					}
					Long globalPropertyId = request.getLong("ID");// 商品品牌ID

					// 先删除子表的globalPropertyValue列表值
					globalPropertyValueManager.deleteGlobalPropertyValueListByGlobalPropertyId(globalPropertyId);
					Boolean numOfDeleted = globalPropertyManager.deleteGlobalProperty(globalPropertyId);
					response = ResponseUtil.getSuccessResponse(numOfDeleted);
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
		return ActionEnum.DELETE_GLOBAL_PROPERTY.getActionName();
	}
}
