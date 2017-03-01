package com.mockuai.itemcenter.core.service.action.itemsales;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSalesSkuCountManager;
import com.mockuai.itemcenter.core.manager.ItemSalesSpuCountManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;

/**
 * 商品销量增减统计
 * 
 * @author csy
 *
 */
@Service
public class ItemSalesCountMsgAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(ItemSalesCountMsgAction.class);
	
	@Resource
	private ItemSalesSpuCountManager itemSalesSpuCountManager;
	
	@Resource
	private ItemSalesSkuCountManager itemSalesSkuCountManager;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemRequest request = context.getRequest();
		PaySuccessMsg paySuccessMsg = (PaySuccessMsg) request.getParam("paySuccessMsg");
		String salesParam = (String) request.getParam("salesParam");
		String appKey =  (String) request.getParam("appKey");
		
		if(null == paySuccessMsg){
			return null;
		}
		
		if(null == salesParam){
			return null;
		}
		
		if(null == appKey){
			return null;
		}
		
		//判断定义增量
		if(!"add".equals(salesParam) && !"red".equals(salesParam)){
			log.error("商品销量增量错误类型："+salesParam);
			return null;
		}
		
		//商品spu销量变动
		itemSalesSpuCountManager.updateItemSalesSpuCount(paySuccessMsg, salesParam);
		
		//商品sku销量变动
		itemSalesSkuCountManager.updateItemSalesSkuCount(paySuccessMsg, salesParam);
		
		return ResponseUtil.getSuccessResponse(true);
	}
	
	
	@Override
	public String getName() {
		return ActionEnum.ITEM_SALESCOUNT.getActionName();
	}
}
