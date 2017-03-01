package com.mockuai.tradecenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogDTO;
import com.mockuai.tradecenter.common.domain.TradeNotifyLogQTO;
import com.mockuai.tradecenter.core.dao.OrderDAO;
import com.mockuai.tradecenter.core.dao.TradeNotifyLogDAO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.TradeNotifyLogDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.BaseService;
import com.mockuai.tradecenter.core.manager.TradeNotifyLogManager;
import com.mockuai.tradecenter.core.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TradeNotifyLogManagerImpl extends BaseService implements TradeNotifyLogManager {
	private static final Logger log = LoggerFactory.getLogger(TradeNotifyLogManagerImpl.class);
	@Autowired
	TradeNotifyLogDAO tradeNotifyLogDAO;
	@Autowired
    OrderDAO orderDAO;

    UserManager userManager;

	@Override
	public Long addTradeNotifyLog(TradeNotifyLogDO record) throws TradeException {
		printIntoService(log, "addTradeNotifyLog", record, "");
		return tradeNotifyLogDAO.addTradeNofifyLog(record);
	}

	@Override
	public TradeNotifyLogDO getTradeNotifyLogByOutBillNo(String outBillNo,Integer type) throws TradeException {
		printIntoService(log, "getTradeNotifyLog", outBillNo, "");
		TradeNotifyLogQTO query = new TradeNotifyLogQTO();
		query.setOutBillNo(outBillNo);
		query.setType(type);
		TradeNotifyLogDO tradeNotifyLogDO = tradeNotifyLogDAO.getTradeNofityLog(query);
		printOutService(log,"getTradeNotifyLog",tradeNotifyLogDO,"");
		return tradeNotifyLogDO;
	}

	@Override
	public TradeNotifyLogDO getTradeNotifyLogByOrderId(Long orderId,Integer type) throws TradeException {
		// TODO Auto-generated method stub
		printIntoService(log, "getTradeNotifyLog", orderId, "");
		TradeNotifyLogQTO query = new TradeNotifyLogQTO();
		query.setOrderId(orderId);
		query.setType(type);
		TradeNotifyLogDO tradeNotifyLogDO = tradeNotifyLogDAO.getTradeNofityLog(query);
		printOutService(log,"getTradeNotifyLog",tradeNotifyLogDO,"");
		return tradeNotifyLogDO;
	}

    @Override
    public List<TradeNotifyLogDTO>  queryTradeNotifyLog(TradeNotifyLogQTO param) throws TradeException {

           Long userId = param.getUserId();
           try{
               List<OrderDO> orderDOList = null;
               if(userId != null || param.getPaymentId() != null ){
                   orderDOList =  orderDAO.queryOrderByUserIdAndPaymentId(param);
                   if(orderDOList == null || orderDOList.isEmpty()){
                       return Lists.newArrayList();
                   }
               }
               if(orderDOList != null){
                   List<Long> orderIds  = new ArrayList<>();
                   for(OrderDO orderDO : orderDOList){
                       orderIds.add(orderDO.getId());
                   }
                   param.setOrderIds(orderIds);
               }
               List<TradeNotifyLogDO> tradeNotifyLogDOList = tradeNotifyLogDAO.queryTradeNotifyLog(param);
               if(tradeNotifyLogDOList == null  || tradeNotifyLogDOList.isEmpty()){
                   return Lists.newArrayList();
               }
               if(orderDOList == null){
                   orderDOList =    orderDAO.queryOrderByTradeNotify(tradeNotifyLogDOList);
               }
               List<TradeNotifyLogDTO> tradeNotifyLogDTOList = new ArrayList<>(tradeNotifyLogDOList.size());
               for(TradeNotifyLogDO item : tradeNotifyLogDOList){
                   TradeNotifyLogDTO tradeNotifyLogDTO = new TradeNotifyLogDTO();
                   BeanUtils.copyProperties(item,tradeNotifyLogDTO);
                   OrderDO orderDO =  getOrderDO(orderDOList,tradeNotifyLogDTO.getOrderId());
                   tradeNotifyLogDTO.setPaymentId(orderDO.getPaymentId());
                   tradeNotifyLogDTO.setUserId(orderDO.getUserId());
                   tradeNotifyLogDTOList.add(tradeNotifyLogDTO);
               }
               return tradeNotifyLogDTOList;
           }catch (Exception e){
               log.error("queryTradeNotifyLog",e);
               throw  new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,e);
           }

    }

    private OrderDO getOrderDO(List<OrderDO>  orderDOList, Long orderId){
        for(OrderDO orderDO : orderDOList){
            if(orderDO.getId() == orderId.longValue()){
                return orderDO;
            }
        }
        return null;
    }

}
