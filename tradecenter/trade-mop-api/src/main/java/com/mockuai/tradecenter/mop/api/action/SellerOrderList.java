package com.mockuai.tradecenter.mop.api.action;


import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.tradecenter.common.api.BaseRequest;
import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.mop.api.util.MopApiUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class SellerOrderList extends BaseAction {
    private static final Logger log = LoggerFactory.getLogger(SellerOrderList.class);
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        Integer orderStatus = null;
        if (request.getParam("order_status") != null) {
            String orderStatusStr = (String) request.getParam("order_status");
            orderStatus = Integer.valueOf(Integer.parseInt(orderStatusStr));
        }
        String offsetStr = (String) request.getParam("offset");
        String countStr = (String) request.getParam("count");
        String sellerIdStr = (String) request.getParam("seller_id");
        String orderTimeFrom = (String) request.getParam("order_time_from");
        String orderTimeTo = (String) request.getParam("order_time_to");
        String appKey = (String)request.getParam("app_key");



        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setOrderStatus(orderStatus);

        //下单时间区间查询
        if(StringUtils.isNotBlank(orderTimeFrom)){
            try{
                orderQTO.setOrderTimeStart(dateFormat.parse(orderTimeFrom));
            }catch(Exception e){
                log.error("error to parse order_time_from, order_time_from:{}", orderTimeFrom, e);
            }
        }
        if(StringUtils.isNotBlank(orderTimeTo)){
            try{
                orderQTO.setOrderTimeEnd(dateFormat.parse(orderTimeTo));
            }catch(Exception e){
                log.error("error to parse order_time_from, order_time_to:{}", orderTimeTo, e);
            }
        }

        //如果没传sellerId，那么查询整个商城下面的订单
        if(StringUtils.isNotBlank(sellerIdStr)){
            orderQTO.setSellerId(Long.valueOf(sellerIdStr));
        }
        orderQTO.setOffset(Integer.valueOf(Integer.parseInt(offsetStr)));
        orderQTO.setCount(Integer.valueOf(Integer.parseInt(countStr)));
        com.mockuai.tradecenter.common.api.Request tradeReq = new BaseRequest();
        tradeReq.setCommand(ActionEnum.QUERY_SELLER_ORDER.getActionName());
        tradeReq.setParam("orderQTO", orderQTO);
        tradeReq.setParam("appKey", appKey);


        Response tradeResp = getTradeService().execute(tradeReq);

        if (tradeResp.getCode() == ResponseCode.RESPONSE_SUCCESS.getCode()) {
            List<OrderDTO> orderDTOs = (List<OrderDTO>) tradeResp.getModule();
            List mopOrderDTOs = new ArrayList();
            if (orderDTOs != null) {
                for (OrderDTO orderDTO : orderDTOs) {
                    mopOrderDTOs.add(MopApiUtil.genMopOrder(orderDTO));
                }
            }

            Map data = new HashMap();
            data.put("order_list", mopOrderDTOs);
            data.put("total_count", Long.valueOf(tradeResp.getTotalCount()));

            return new MopResponse(data);
        }
        return MopApiUtil.transferResp(tradeResp);
    }

    public String getName() {
        return "/trade/seller_order/list";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_GET;
    }
}