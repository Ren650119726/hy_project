package com.mockuai.headsinglecenter.core.message.msg.parser;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.ons.api.Message;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleInfoDTO;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleUserDTO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.AppManager;
import com.mockuai.headsinglecenter.core.message.msg.PaySuccessMsg;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;

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
    
    public PaySuccessMsg parse(Message msg) {
        String msgStr = new String(msg.getBody(), Charset.forName("UTF-8"));
        JSONObject json = JSONObject.parseObject(msgStr);
        Long orderId = json.getLong("id");
        Long userId = json.getLong("userId");
        String bizCode = json.getString("bizCode");       
        String appKey;
        
		try {
			appKey = appManager.getAppKeyByBizCode(bizCode);
		} catch (HeadSingleException e) {
			log.error("get appKey error, bizCode: {}", bizCode);
            return null;
		}        
       
		//查询订单信息
        Response<OrderDTO>  orderDtoRe = orderClient.getOrder(orderId, userId, appKey);        
        if(null == orderDtoRe || false == orderDtoRe.isSuccess() || null == orderDtoRe.getModule()){
        	log.error("get order by orderId: {}, buyerId: {} error", orderId, userId);
            return null;
        }
        
        OrderDTO orderDTO = orderDtoRe.getModule();
        
        //判断订单活动类型
        List<OrderDiscountInfoDTO> OrderDiscountInfoDTOs = orderDTO.getOrderDiscountInfoDTOs();
        
        if(null == OrderDiscountInfoDTOs){
        	return null;
        }
        
        //循环判断是否存在首单立减的订单
        List<String> codeList = new ArrayList<String>();
        
        for(OrderDiscountInfoDTO orderDiscountInfo:OrderDiscountInfoDTOs){
        	if("FirstOrderDiscount".equals(orderDiscountInfo.getDiscountCode())){
        		codeList.add(orderDiscountInfo.getDiscountCode());
        	}	
        }
        
        if(null == codeList || codeList.isEmpty()){
    		return null;
    	}
        
        //初始化PaySuccessMsg
        PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
        paySuccessMsg.setAppKey(appKey);
        paySuccessMsg.setMsg(msg);

        //赋值享受首单立减用户信息
        HeadSingleUserDTO headSingleUserDTO = new HeadSingleUserDTO();
        headSingleUserDTO.setUserId(orderDTO.getUserId());
        headSingleUserDTO.setOrderCount(1L);        
        paySuccessMsg.setHeadSingleUserDTO(headSingleUserDTO);
        
        //赋值首单立减订单信息
        HeadSingleInfoDTO headSingleInfoDTO = new HeadSingleInfoDTO();
        headSingleInfoDTO.setOrderId(orderDTO.getId());
        headSingleInfoDTO.setUserId(orderDTO.getUserId());
        headSingleInfoDTO.setOrderType(orderDTO.getOrderStatus());
        headSingleInfoDTO.setPayTime(orderDTO.getPayTime());
        headSingleInfoDTO.setTerminalType(orderDTO.getAppType());
        paySuccessMsg.setHeadSingleInfoDTO(headSingleInfoDTO);
        
        return paySuccessMsg;
    }
}
