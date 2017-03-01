package com.mockuai.tradecenter.core.service.action.order;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.constant.TradeConstants;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.TradeUtil.RefundMark;

/**
 * Created by zengzhangqiang on 6/5/15.
 */
public class AddOrderComment implements Action {

    private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);

    @Resource
    private OrderManager orderManager;

    @Resource
    private ItemManager itemManager;
    
    @Resource
    private TransactionTemplate transactionTemplate;
    
	@Resource
	private OrderItemManager orderItemManager;
	
	@Resource
	private DataManager dataManager;

    @SuppressWarnings("unchecked")
	public TradeResponse<Boolean> execute(final RequestContext context) throws TradeException {
        Request request = context.getRequest();
        final String appKey = (String)context.get("appKey");

        TradeResponse<Boolean> response = null;
        
        if (request.getParam("itemCommentList") == null) {
            return new TradeResponse(ResponseCode.PARAM_E_PARAM_MISSING, "itemCommentList is null");
        }
        final List<ItemCommentDTO> itemCommentDTOList = (List<ItemCommentDTO>) request.getParam("itemCommentList");

        for(ItemCommentDTO commentDTO : itemCommentDTOList){
        	
        	Long itemId = commentDTO.getItemId();
        	
        	if(null == itemId||0==itemId){
        		return new TradeResponse(ResponseCode.PARAM_E_PARAM_MISSING, "itemCommentList.itemId is null or itemId is not 0");
        	}
        	   //TODO 校验评论列表中的订单，确保是同一个订单
        	 OrderDO order = this.orderManager.getActiveOrder(commentDTO.getOrderId(), commentDTO.getUserId());
             
             if(order == null){
                 log.error("order doesn't exist");
                 return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order doesn't exist");
             }
             
            if( order.getRefundMark()==RefundMark.REFUNDING_MARK ){
     			return ResponseUtils.getFailResponse(ResponseCode.HAS_REFUND_ITEM_CAN_NOT_OPERATE);
     		}

             //订单状态检查
             if(order.getOrderStatus() < TradeConstants.Order_Status.SIGN_OFF  ){
                 return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ORDER_UNSIGN_OFF_CANNOT_COMMENT);
             }
             
             
             if(order.getOrderStatus() >= TradeConstants.Order_Status.COMMENTED){
             	return ResponseUtils.getFailResponse(ResponseCode.BZI_E_ORDER_UNPAID_CANNOT_DELIVERY,"已评论不能重复评论");
             }
             
             
             //TODO 。。。为了兼容洋东西
             if(null==commentDTO.getSkuId()){
            	OrderItemQTO orderItemQTO = new OrderItemQTO();
     			orderItemQTO.setOrderId(order.getId());
     			orderItemQTO.setUserId(order.getUserId());
     			List<OrderItemDO> orderItems = Collections.EMPTY_LIST;
     			orderItems = this.orderItemManager.queryOrderItem(orderItemQTO);

     			if(orderItems==null || orderItems.isEmpty()){
     				 return ResponseUtils.getFailResponse(ResponseCode.BIZ_E_RECORD_NOT_EXIST, "order item  is empty");
     			}else{
     				commentDTO.setSkuId(orderItems.get(0).getItemSkuId());
     			}
             }
             log.info("score="+commentDTO.getScore());
             
             if(StringUtils.isBlank(commentDTO.getContent())){
            	 return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "评论内容不能为空");
             }
             
        }

        
	        	   TradeResponse transResult = transactionTemplate.execute(new TransactionCallback<TradeResponse>() {
	
					@Override
					public TradeResponse doInTransaction(TransactionStatus status) {
						try {
						//将评论内容添加到相关商品上面
			            boolean addResult = itemManager.addItemComment(itemCommentDTOList, appKey);
			            if(addResult == false){
			            	return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"评论内容添加失败");
			            }
			          //更新订单评价状态
			            for(ItemCommentDTO commentDTO : itemCommentDTOList){
			            	int modifyOrderResult = orderManager.commentOrder(commentDTO.getOrderId()
			                		, commentDTO.getUserId());
			            	
			            }
			            
//			            dataManager.doOrderCommentBuriedPoint(itemCommentDTOList, appKey);
			            
			            context.put("itemCommentDTOList", itemCommentDTOList);
			            
						}catch(Exception e){
							log.error("error comment error",e);
							status.setRollbackOnly();
							return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,"评论内容添加失败");
						}
						return ResponseUtils.getSuccessResponse(true);
	        	   }});
        	
            

            

        return transResult;
    }

    @Override
    public String getName() {
        return ActionEnum.COMMENT_ORDER.getActionName();
    }

}
