package com.mockuai.tradecenter.core.service.action.order;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

public class ReplyComment implements Action {

    private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);

    @Resource
    private OrderManager orderManager;

    @Resource
    private ItemManager itemManager;

    @SuppressWarnings("unchecked")
	public TradeResponse<Boolean> execute(RequestContext context) throws TradeException {
        Request request = context.getRequest();
        String appKey = (String)context.get("appKey");
        TradeResponse<Boolean> response = null;
        
        if(request.getParam("userId") == null ){
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
		}else if(request.getParam("orderId") == null){
			log.error("orderId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderId is null");
		}else if(request.getParam("itemId") ==null){
			log.error("itemId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"itemId is null");
		}else if(request.getParam("replyUserId")==null){
			log.error("replyUserId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"replyUserId is null");
		}else if(request.getParam("content")==null){
			log.error("content is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"reply content is null");
		}else if(request.getParam("sellerId")==null){
			log.error("sellerId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sellerId is null");
		}else if(request.getParam("commentId")==null){
			log.error("commentId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"commentId is null");
		}
        
        //TODO 校验评论列表中的订单，确保是同一个订单

        	Long orderId = (Long) request.getParam("orderId");
			Long userId = (Long) request.getParam("userId");
            Long sellerId = (Long)request.getParam("sellerId");
            Long replyUserId = (Long)request.getParam("replyUserId");
            String replyContent = (String)request.getParam("content");
            Long itemId = (Long)request.getParam("itemId");
        OrderDO order = this.orderManager.getActiveOrder(orderId, userId);
        
        if(order == null){
            log.error("order doesn't exist");
            return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
        }
        
        


        try {
        	com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO replyComment = new com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO();
        	
        	replyComment.setId((Long)request.getParam("commentId"));
        	replyComment.setOrderId(orderId);
        	replyComment.setUserId(userId);
        	replyComment.setReplyUserId(replyUserId);
        	replyComment.setReplyContent(replyContent);
        	replyComment.setSellerId(order.getSellerId());
        	replyComment.setItemId(itemId);
        	replyComment.setSkuId(428l);
            //将评论内容添加到相关商品上面
            boolean addResult = itemManager.replyComment(replyComment, appKey);
            if(addResult == false){
            	response = ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"评论内容添加失败");
            	return response;
            }
            response = ResponseUtils.getSuccessResponse(true);


        } catch (TradeException e) {
            log.error("db error： " ,e);
            response = ResponseUtils.getFailResponse(e.getResponseCode());
        }
        return response;
    }

    @Override
    public String getName() {
        return ActionEnum.REPLY_COMMENT.getActionName();
    }

}
