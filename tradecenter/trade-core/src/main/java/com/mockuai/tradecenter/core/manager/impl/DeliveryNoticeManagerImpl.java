//package com.mockuai.tradecenter.core.manager.impl;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
//
//import com.mockuai.tradecenter.common.constant.ResponseCode;
//import com.mockuai.tradecenter.common.domain.DeliveryNoticeDTO;
//import com.mockuai.tradecenter.core.dao.DeliveryNoticeDao;
//import com.mockuai.tradecenter.core.domain.DeliveryNoticeDO;
//import com.mockuai.tradecenter.core.exception.TradeException;
//import com.mockuai.tradecenter.core.manager.DeliveryNoticeManager;
//import com.mockuai.tradecenter.core.service.action.cart.AddCartItem;
//
//public class DeliveryNoticeManagerImpl implements DeliveryNoticeManager{
//	private static final Logger log = LoggerFactory.getLogger(AddCartItem.class);
//
//	@Resource
//	private DeliveryNoticeDao deliveryNoticeDao;
//
//	@Override
//	public long addDeliveryNotice(DeliveryNoticeDO deliveryNotice)throws TradeException{
//		log.info("enter addDeliveryNotice ");
//		// TODO
//		//字段验证
//		/*if(deliveryNoticeDO.getOrderId() == null){
//			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"deliveryNoticeDTO.orderId is null");
//		}*/
//		/*DeliveryNoticeDO notice = new DeliveryNoticeDO();
//		notice.setOrderId(deliveryNoticeDTO.getOrderId());
//		notice.setUserId(deliveryNoticeDTO.getUserId());
//		notice.setExpress(deliveryNoticeDTO.getExpress());
//		notice.setExpressNo(deliveryNoticeDTO.getExpressNo());
//		notice.setExpressId(deliveryNoticeDTO.getExpressId());
//		notice.setMemo(deliveryNoticeDTO.getMemo());*/
//
//		long result =0;
//		try{
//			result =  this.deliveryNoticeDao.addDeliveryNotice(deliveryNotice);
//		}catch(DataAccessException e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
//		}
//		log.info("exit addDeliveryNotice result : " + result);
//		return result;
//	}
//
//	@Override
//	public int confirmReceival(long orderId, long userId, boolean isArrival)
//			throws TradeException {
//		log.info("enter updateDeliveryNotice : " + orderId + " , " + userId  + " , " + isArrival);
//		DeliveryNoticeDO deliveryNoticeDO = new DeliveryNoticeDO();
//		deliveryNoticeDO.setUserId(userId);
//		deliveryNoticeDO.setOrderId(orderId);
//		deliveryNoticeDO.setIsArrival(isArrival);  // 商品已经到达
//
//		int result =0;
//		try{
//			result = this.deliveryNoticeDao.updateDeliveryNotice(deliveryNoticeDO);
//		}catch(DataAccessException e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
//		}
//		log.info("exit updateDeliveryNotice updated: " + result);
//		return result;
//	}
//
//	public String validateFields(DeliveryNoticeDTO notice){
//		//TODO
//		return null;
//	}
//}
