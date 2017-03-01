package com.mockuai.tradecenter.core.service.action.order;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderConsigneeDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemQTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.manager.OrderConsigneeManager;
import com.mockuai.tradecenter.core.manager.OrderItemManager;
import com.mockuai.tradecenter.core.manager.OrderManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.ModelUtil;

/**
 * 查询订单的评价列表
 * @author hzmk
 *
 */
public class QuerySellerComment implements Action {

    private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);

    @Resource
    private OrderManager orderManager;

    @Resource
    private ItemManager itemManager;
    
    @Resource
    private ItemClient itemClient;
    
    @Resource
    private DozerBeanService  dozerBeanService;
    
    @Resource
    private OrderConsigneeManager orderConsigneeManager;

    @Resource
	private OrderItemManager orderItemManager;
    
    @SuppressWarnings("unchecked")
	public TradeResponse<List<com.mockuai.tradecenter.common.domain.ItemCommentDTO>> execute(RequestContext context) throws TradeException {
    	Request request = context.getRequest();
		String appKey = (String)context.get("appKey");
        TradeResponse<List<com.mockuai.tradecenter.common.domain.ItemCommentDTO>> response = null;
        
        if(request.getParam("score")!=null){
	        if((Integer)request.getParam("score") !=1 & (Integer)request.getParam("score") !=3 & (Integer)request.getParam("score") !=5 ){
				log.error("score is illegal");
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_ILLEGAL,"score is illegal");
			}
        }
        
        Long seller_id = (Long) request.getParam("sellerId"); 
        Long item_id = (Long) request.getParam("itemId"); 
        Integer score = (Integer) request.getParam("score"); 
        
        Integer pageSize = (Integer)request.getParam("pageSize"); 
        Integer offset = (Integer)request.getParam("offset"); 
        
        if(null==offset){
        	offset = 1;
        }
        
        if(null==pageSize){
        	pageSize = 20;
        }
        
		ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
		itemCommentQTO.setOffset(offset);
		itemCommentQTO.setPageSize(pageSize);
		itemCommentQTO.setSellerId(seller_id);
		itemCommentQTO.setItemId(item_id);
		if(null!=score){
			itemCommentQTO.setScore(score.longValue());
		}
		
		itemCommentQTO.setNeedPaging(true);
		request.setParam("itemCommentQTO", itemCommentQTO);
		//TODO 这里需要重构，action不要直接依赖外部client
		Response<List<ItemCommentDTO>> itemResponse = itemClient.queryItemCommentGrade(itemCommentQTO, appKey);
		List<com.mockuai.tradecenter.common.domain.ItemCommentDTO> responseList = new ArrayList<com.mockuai.tradecenter.common.domain.ItemCommentDTO>();
		if(itemResponse.getCode()==10000){
			List<ItemCommentDTO> itemDTOList = itemResponse.getModule();
			
			if(null!=itemDTOList&&itemDTOList.size()>0){
				
				
				for(ItemCommentDTO itemCommentDTO :itemDTOList){
					
					com.mockuai.tradecenter.common.domain.ItemCommentDTO commentDTO = new com.mockuai.tradecenter.common.domain.ItemCommentDTO();
					BeanUtils.copyProperties(itemCommentDTO, commentDTO);	
					 
					commentDTO.setCommentTimes(DateUtil.getFormatDate(itemCommentDTO.getCommentTime(), "yyyy-MM-dd HH:mm:ss"));
					
					Long orderId = itemCommentDTO.getOrderId();
					Long userId = itemCommentDTO.getUserId();
					Long itemId = itemCommentDTO.getItemId();
					
					OrderDO orderDO = orderManager.getOrder(orderId, userId);
					if(null!=orderDO){
						OrderDTO orderDTO = dozerBeanService.cover(orderDO,
								OrderDTO.class);
						commentDTO.setOrderDTO(orderDTO);
						OrderConsigneeDO consignessDO = orderConsigneeManager.getOrderConsignee(orderId, userId);
						
						
						OrderConsigneeDTO orderConsigneeDTO = dozerBeanService.cover(consignessDO,
								com.mockuai.tradecenter.common.domain.OrderConsigneeDTO.class);
						
						
						commentDTO.setOrderConsigneeDTO(orderConsigneeDTO);
						
						
						OrderItemQTO orderItemQTO = new OrderItemQTO();
						orderItemQTO.setOrderId(orderDTO.getId());
						orderItemQTO.setUserId(orderDTO.getUserId());
						orderItemQTO.setItemId(itemCommentDTO.getItemId());
						orderItemQTO.setItemSkuId(itemCommentDTO.getSkuId());
//						itemCommentDTO.get
						List<OrderItemDO> orderItemDOs = this.orderItemManager.queryOrderItem(orderItemQTO);
//						OrderItemDO  orderItem = this.orderItemManager.getOrderItem(orderItemQTO);
						if(orderItemDOs!=null&&orderItemDOs.size()>0){
							OrderItemDO orderItemDO = orderItemDOs.get(0);
							commentDTO.setOrderItemDTO(ModelUtil.convert2OrderItemDTO(orderItemDO));
						}
//						OrderItemDTO orderitemDTO = ModelUtil.convert2OrderItemDTO(orderItem);
						
//						orderItemQTO.setOrderItemId(orderItemId);
//						OrderItemDO  orderItem = this.orderItemManager.getOrderItem(orderItemQTO);
//						OrderItemDTO orderitemDTO = ModelUtil.convert2OrderItemDTO(orderItem);
//						commentDTO.setOrderItemDTO(orderitemDTO);
					}
					
					
					
					
					
					responseList.add(commentDTO);
				}
				
			}
			
			response = ResponseUtils.getSuccessResponse(responseList);
			response.setTotalCount(itemResponse.getTotalCount()); // 总记录数
			
			
		}else{
			throw new TradeException(itemResponse.getMessage());
		}
		return response;
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_COMMENT.getActionName();
    }

}
