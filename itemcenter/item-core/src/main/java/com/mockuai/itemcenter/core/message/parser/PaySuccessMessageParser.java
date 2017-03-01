package com.mockuai.itemcenter.core.message.parser;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;

/**
 * Created by csy on 16/5/27.
 */
@Service
public class PaySuccessMessageParser {
    private static final Logger log = LoggerFactory.getLogger(PaySuccessMessageParser.class);

    @Resource
    private AppManager appManager;
    @Resource
    private OrderClient orderClient;
    
    public PaySuccessMsg parse(Message msg, String tag) {
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        JSONObject json = JSONObject.parseObject(msgStr);
        Long orderId = json.getLong("id");
        Long userId = json.getLong("userId");
        String bizCode = json.getString("bizCode");       
        String appKey;
        
		try {
			appKey = appManager.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP).getAppKey();
		} catch (ItemException e) {
			log.error("get appKey error, bizCode: {}", bizCode);
            return null;
		}
		
		if(null != tag && tag.equals("refundSuccess")){
			orderId = json.getLong("orderId");
		}
       
		//查询订单信息
        Response<OrderDTO>  orderDtoRe = orderClient.getOrder(orderId, userId, appKey);        
        if(null == orderDtoRe || false == orderDtoRe.isSuccess() || null == orderDtoRe.getModule()){
        	log.error("get order by orderId: {}, buyerId: {} error", orderId, userId);
            return null;
        }
        
        //判断订单是否有商品
        OrderDTO orderDTO = orderDtoRe.getModule();
        List<OrderItemDTO> orderItems = orderDTO.getOrderItems();
        
        if(null == orderDTO || orderItems.isEmpty()){
        	return null;
        }        
        
        //初始化PaySuccessMsg
        PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
        paySuccessMsg.setAppKey(appKey);
        paySuccessMsg.setMsg(msg);

        
        List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs = new ArrayList<ItemSalesSpuCountDTO>();
        List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs = new ArrayList<ItemSalesSkuCountDTO>();
        
        for(OrderItemDTO orderItemDTO:orderItems){
        	//赋值销量spu
        	ItemSalesSpuCountDTO itemSalesSpuCountDTO = new ItemSalesSpuCountDTO();        	
            itemSalesSpuCountDTO.setItemId(orderItemDTO.getItemId());//商品id
            itemSalesSpuCountDTO.setSellerId(orderItemDTO.getSellerId());
            itemSalesSpuCountDTO.setSpuSalesCount(1L);//增量
            itemSalesSpuCountDTO.setBizCode(bizCode);
            itemSalesSpuCountDTOs.add(itemSalesSpuCountDTO);
            
            //赋值销量sku
            ItemSalesSkuCountDTO itemSalesSkuCountDTO = new ItemSalesSkuCountDTO();            
            itemSalesSkuCountDTO.setItemId(orderItemDTO.getItemId());//商品id
            itemSalesSkuCountDTO.setSkuId(orderItemDTO.getItemSkuId());//商品skuid
            itemSalesSkuCountDTO.setSkuSalesCount(1L);//增量
            itemSalesSkuCountDTOs.add(itemSalesSkuCountDTO);
        }
        
        paySuccessMsg.setItemSalesSpuCountDTOs(itemSalesSpuCountDTOs);//spu       
        paySuccessMsg.setItemSalesSkuCountDTOs(itemSalesSkuCountDTOs);//sku
        
        return paySuccessMsg;
    }
}
