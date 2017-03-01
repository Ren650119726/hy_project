package com.mockuai.itemcenter.core.service.action.itemproperty;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 查看商品属性模板Action
 * 
 * @author chen.huang
 *
 */
@Service
public class CountItemPropertyAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(CountItemPropertyAction.class);

	@Resource
	private ItemPropertyManager itemPropertyManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemPropertyQTO itemPropertyQTO = null;
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
        itemPropertyQTO = (ItemPropertyQTO) request.getObject("itemPropertyQTO");



		try {
            int count =   itemPropertyManager.countBy(itemPropertyQTO);
            response = ResponseUtil.getSuccessResponse(count);
            return response;
		} catch (Exception e) {
			response = ResponseUtil.getErrorResponse(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.COUNT_ITEM_PROPERTY.getActionName();
	}
}
