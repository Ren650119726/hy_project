package com.mockuai.itemcenter.core.filter.impl;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.filter.Filter;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.manager.NotifyManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NotifyMsgFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(NotifyMsgFilter.class);

	@Override
	public boolean isAccept(RequestContext ctx) {
		return true;
	}

	@Override
	public ItemResponse before(RequestContext ctx) throws ItemException {
		return new ItemResponse(ResponseCode.SUCCESS);
	}

	@Override
	public ItemResponse after(RequestContext ctx) throws ItemException {

		//捕获所有异常，防止消息推送流程出现的问题影响到正常的流程
		try{

			//如果接口处理失败，则直接返回
			if(ctx.getResponse().isSuccess() == false){
				return new ItemResponse(ResponseCode.SUCCESS);
			}


			String command = ctx.getRequest().getCommand();
			String bizCode = (String)ctx.get("bizCode");



			NotifyManager notifyManager = (NotifyManager)ctx.getAppContext().getBean("notifyManager");

			ActionEnum action = ActionEnum.getActionEnum(command);
			switch (action){
				case ADD_ITEM:{
					ItemDTO retItemDTO = (ItemDTO)ctx.getResponse().getModule();
					notifyManager.notifyAddItemMsg(retItemDTO.getId(), retItemDTO.getSellerId(), bizCode);
					break;
				}

				case UPDATE_ITEM:{
					ItemDTO itemDTO = (ItemDTO) ctx.getRequest().getParam("itemDTO");
					Long itemId = itemDTO.getId();
					Long sellerId = itemDTO.getSellerId();
					notifyManager.notifyUpdateItemMsg(itemId, sellerId, bizCode);
					break;
				}

				case UP_ITEM:{
					Long itemId = ctx.getRequest().getLong("itemId");// 商品品牌ID
					Long sellerId = ctx.getRequest().getLong("supplierId");//卖家ID
					notifyManager.notifyUpItemMsg(itemId, sellerId, bizCode);
					break;
				}

				case WITHDRAW_ITEM:{
					Long itemId = ctx.getRequest().getLong("itemId");// 商品品牌ID
					Long sellerId = ctx.getRequest().getLong("supplierId");//卖家ID
					notifyManager.notifyDownItemMsg(itemId, sellerId, bizCode);
					break;
				}
			default:
				break;
			}
		}catch(Exception e){
			log.error("", e);
		}

		return new ItemResponse(ResponseCode.SUCCESS);
	}
}
