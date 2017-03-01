package com.mockuai.itemcenter.core.service.action.salesfield;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SalesFieldManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 删除专场Action
 * 
 * @author chen.huang
 *
 */
@Service
public class DeleteSalesFieldAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DeleteSalesFieldAction.class);
	@Resource
	private SalesFieldManager salesFieldManager;

	@Resource
	TransactionTemplate transactionTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public ItemResponse execute(final RequestContext context) throws ItemException {
		ItemResponse response = (ItemResponse)transactionTemplate.execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					ItemResponse response = null;
					ItemRequest request = context.getRequest();
					// 验证ID
					if (request.getInteger("ID") == null) {
						return ResponseUtil
								.getErrorResponse(ResponseCode.PARAM_E_MISSING, "salesField ID is missing");
					}
					Integer salesFieldId = request.getInteger("ID");// 商品品牌ID

					Boolean numOfDeleted = salesFieldManager.deleteSalesField(salesFieldId);
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
		return ActionEnum.DELETE_SALES_FIELD.getActionName();
	}
}
