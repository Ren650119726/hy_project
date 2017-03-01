package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.client.DeliveryDetailClient;
import com.mockuai.deliverycenter.client.DeliveryInfoClient;
import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.tradecenter.common.domain.DeliveryDetailDTO;
import com.mockuai.tradecenter.common.domain.OrderDeliveryInfoDTO;
import com.mockuai.tradecenter.core.dao.OrderStoreDAO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DeliveryManager;
import com.mockuai.tradecenter.core.util.DateUtil;

public class DeliveryManagerImpl implements DeliveryManager{
	private static final Logger log = LoggerFactory.getLogger(DeliveryManagerImpl.class);

	@Resource
	private DeliveryInfoClient deliveryInfoClient;
	@Resource
	private DeliveryDetailClient deliveryDetailClient;
	@Resource
	private RegionClient regionClient;
	@Resource
	private OrderStoreDAO orderStoreDAO;

	@Override
	public long getDeliveryFee(Long addressId, Long weight){
		//TODO 这里先写死，上线前务必修改掉
		long result =0L;
		return result;
	}


	@Override
	public boolean isCodSupported(long regionId) {
		return true;	
	}

	@Override
	public Long addDeliveryInfo(OrderDeliveryInfoDTO orderDeliveryInfoDTO,String appkey) throws TradeException{
		//TODO 入参校验
		DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
		deliveryInfoDTO.setOrderId(orderDeliveryInfoDTO.getOrderId());
		deliveryInfoDTO.setUserId(orderDeliveryInfoDTO.getUserId());
		deliveryInfoDTO.setExpress(orderDeliveryInfoDTO.getDeliveryCompany());
		deliveryInfoDTO.setExpressNo(orderDeliveryInfoDTO.getDeliveryCode());
		deliveryInfoDTO.setExpressCode(orderDeliveryInfoDTO.getExpressCode());
		deliveryInfoDTO.setSkuIdList(orderDeliveryInfoDTO.getSkuIdList());
		Response<DeliveryInfoDTO> deliveryResp = this.deliveryInfoClient.addDeliveryInfo(deliveryInfoDTO,appkey);
		if(deliveryResp.getCode() == RetCodeEnum.SUCCESS.getCode()){
			return deliveryResp.getModule().getId();
		}else{
			//TODO error handle
			log.error("addDeliveryInfo error, orderId:{}, userId:{}",
					orderDeliveryInfoDTO.getOrderId(), orderDeliveryInfoDTO.getUserId(), deliveryResp.getMessage());
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION,
					"addDeliveryInfo error");
		}
	}
	
	@Override
	public boolean batchAddDeliveryInfo(List<OrderDeliveryInfoDTO> list, String appkey) throws TradeException {
		List<DeliveryInfoDTO> deliveryInfoDTOList = new ArrayList<DeliveryInfoDTO>();
		for(OrderDeliveryInfoDTO orderDeliveryInfoDTO:list){
			DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
			deliveryInfoDTO.setOrderId(orderDeliveryInfoDTO.getOrderId());
			deliveryInfoDTO.setUserId(orderDeliveryInfoDTO.getUserId());
			deliveryInfoDTO.setExpress(orderDeliveryInfoDTO.getDeliveryCompany());//物流公司名称
			deliveryInfoDTO.setExpressNo(orderDeliveryInfoDTO.getDeliveryCode());//快递单号
			deliveryInfoDTO.setExpressCode(orderDeliveryInfoDTO.getExpressCode());//物流公司code
			deliveryInfoDTOList.add(deliveryInfoDTO);
		}
		log.info(" deliveryInfoDTOList : "+JSONObject.toJSONString(deliveryInfoDTOList));
		Response<Boolean> response = this.deliveryInfoClient.batchAddDeliveryInfo(deliveryInfoDTOList, appkey);
		if(response.getCode() == RetCodeEnum.SUCCESS.getCode()){
			return response.getModule();
		}else{
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION,
					"addDeliveryInfo error");
		}
	}

	@Override
	public List<OrderDeliveryInfoDTO> queryDeliveryInfo(long orderId, long userId,String appkey) throws TradeException{
		try{
			DeliveryInfoQTO deliveryInfoQTO = new DeliveryInfoQTO();
			deliveryInfoQTO.setOrderId(orderId);
			deliveryInfoQTO.setUserId(userId);
			//查询订单发货信息
			Response<List<DeliveryInfoDTO>> response = deliveryInfoClient.queryDeliveryInfo(deliveryInfoQTO,appkey);
			if(response.getCode() == RetCodeEnum.SUCCESS.getCode()){
				List<DeliveryInfoDTO> deliveryInfoDTOs = response.getModule();
				List<OrderDeliveryInfoDTO> orderDeliveryInfoDTOs = new ArrayList<OrderDeliveryInfoDTO>();
				//查询物流详情
				for(DeliveryInfoDTO deliveryInfoDTO: deliveryInfoDTOs){
					DeliveryDetailQTO deliveryDetailQTO = new DeliveryDetailQTO();
					deliveryDetailQTO.setOrderId(orderId);
					deliveryDetailQTO.setUserId(userId);
					deliveryDetailQTO.setDeliveryCode(deliveryInfoDTO.getExpressNo());
					//如果是第三方、目前deliveryDetail应该没数据
					Response<List<com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO>> detailResponse =
							deliveryDetailClient.queryDeliveryDetail(deliveryDetailQTO,appkey);

					OrderDeliveryInfoDTO orderDeliveryInfoDTO = null;
					if(RetCodeEnum.SUCCESS.getCode() == detailResponse.getCode()){
						orderDeliveryInfoDTO = genOrderDeliveryInfo(deliveryInfoDTO,
								detailResponse.getModule());
					}else{
						orderDeliveryInfoDTO = genOrderDeliveryInfo(deliveryInfoDTO, null);
					}
					//增加第三方物流
					List<ThirdpartyExpressDetailDTO> thirdpartyExpressDTOs =  deliveryInfoDTO.getThirdpartyList();
					if(thirdpartyExpressDTOs!=null&&thirdpartyExpressDTOs.isEmpty()==false){
						
						List<DeliveryDetailDTO> orderDeliveryDetailList  = orderDeliveryInfoDTO.getDeliveryDetailDTOs();
						if(null==orderDeliveryDetailList)
							orderDeliveryDetailList = new ArrayList<DeliveryDetailDTO>();
						for(ThirdpartyExpressDetailDTO thridpartyExpressDetailDTO:thirdpartyExpressDTOs){
							DeliveryDetailDTO thirdpartyDeliveryInfoDTO = new DeliveryDetailDTO();
							thirdpartyDeliveryInfoDTO.setContent(thridpartyExpressDetailDTO.getContext());
							String opTime = thridpartyExpressDetailDTO.getTime();
							if(StringUtils.isNotBlank(opTime)){
								thirdpartyDeliveryInfoDTO.setOpTime(DateUtil.strToDate(opTime,"yyyy-MM-dd HH:mm:ss"));
							}
							
							
							orderDeliveryDetailList.add(thirdpartyDeliveryInfoDTO);
						}
						orderDeliveryInfoDTO.setDeliveryDetailDTOs(orderDeliveryDetailList);
						
						
					}
					
					orderDeliveryInfoDTOs.add(orderDeliveryInfoDTO);
				}
				return orderDeliveryInfoDTOs;
			}else{
				//TODO log
				log.error("remoting call delivery failed");
				throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		}catch(Exception e){
			log.error("queryDeliveryInfo error",e);
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
	}

	@Override
	public List<RegionDTO> queryRegion(List<String> regionCodes,String appkey) throws TradeException {
		RegionQTO regionQTO = new RegionQTO();
		regionQTO.setRegionCodes(regionCodes);
		try{
			Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTO,appkey);
			if(response.isSuccess()){
				return response.getModule();
			}else{
				//TODO error handle
			}
		}catch(Exception e){
			//TODO error handle
		}
		return Collections.EMPTY_LIST;
	}

	private OrderDeliveryInfoDTO genOrderDeliveryInfo(DeliveryInfoDTO deliveryInfoDTO,
													  List<com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO> deliveryDetailDTOs){
		OrderDeliveryInfoDTO orderDeliveryInfoDTO = new OrderDeliveryInfoDTO();
		orderDeliveryInfoDTO.setDeliveryInfoId(deliveryInfoDTO.getId());
		orderDeliveryInfoDTO.setDeliveryType(deliveryInfoDTO.getDeliveryId());//发货方式
		orderDeliveryInfoDTO.setDeliveryCode(deliveryInfoDTO.getExpressNo());
		orderDeliveryInfoDTO.setUserId(deliveryInfoDTO.getUserId());
		orderDeliveryInfoDTO.setDeliveryCompany(deliveryInfoDTO.getExpress());

		if(deliveryDetailDTOs != null){
			List<DeliveryDetailDTO> orderDeliveryDetailList = new ArrayList<DeliveryDetailDTO>();
			for(com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO deliveryDetailDTO: deliveryDetailDTOs){
				DeliveryDetailDTO orderDeliveryDetail = new DeliveryDetailDTO();
				orderDeliveryDetail.setDetailId(deliveryDetailDTO.getId());
				orderDeliveryDetail.setUserId(deliveryDetailDTO.getUserId());
				orderDeliveryDetail.setOpTime(deliveryDetailDTO.getOpTime());
				orderDeliveryDetail.setContent(deliveryDetailDTO.getContent());
				orderDeliveryDetailList.add(orderDeliveryDetail);
			}
			orderDeliveryInfoDTO.setDeliveryDetailDTOs(orderDeliveryDetailList);
		}

		return orderDeliveryInfoDTO;
	}


	@Override
	public Boolean deliveryGoodsByPickup(Long orderId, Long userId, String pickupCode) throws TradeException {
		OrderStoreDO orderStore = orderStoreDAO.getOrderStore(orderId);
		if(null==orderStore){
			log.error("deliveryGoodsByPickup orderStore is null");
			throw new TradeException("deliveryGoodsByPickup orderStore is null");
		}
		if(null==orderStore.getPickupCode()){
			throw new TradeException("请先通知用户提货");
		}
		if(!orderStore.getPickupCode().equalsIgnoreCase(pickupCode)){
			throw new TradeException("提货码不匹配");
		}
		
		
		return true;
	}


	
	
}
