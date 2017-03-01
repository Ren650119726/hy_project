package com.mockuai.tradecenter.core.service.action.dataCenter;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.datacenter.client.DataClient;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DataDTO;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.service.action.order.ConfirmReceipt;
import com.mockuai.tradecenter.core.util.DateUtil;

public class GetData implements Action{

    private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);
	
	@Resource
	private DataManager dataManager;
	
	@Resource
	private DataClient dataClient;

	@Override
	public TradeResponse<DataDTO> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		DataDTO dataDTO = new DataDTO();
		DataQTO dataQTO = (DataQTO) request.getParam("dataQTO");
		String appKey = (String) context.get("appKey");
		if(dataQTO==null){
			log.error("dataQTO is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"orderQTO is null");
		}
		
		int type = dataQTO.getData_type();
		if(type==-1){
			log.error("data type is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"data type is null");
		}
		String bizCode = (String) context.get("bizCode");
		dataQTO.setBizCode(bizCode);
		Date endTIme = dataQTO.getTimeEnd();
		if(endTIme!=null)
			dataQTO.setTimeEnd(DateUtil.getRelativeDate(endTIme,1));
		
		switch (type){
		case 1:{
			dataDTO.setTotalAmount(dataManager.getTotalAmount(dataQTO));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 2:{
			dataDTO.setTotalOrderCount(dataManager.getTotalOrderCount(dataQTO));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 3:{
			dataDTO.setPaidOrderCount(dataManager.getPaidOrderCount(dataQTO));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 4:{
			dataDTO.setTotalUserCount(dataManager.getTotalUserCount(dataQTO));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 5:{
			dataDTO.setPaidUserCount(dataManager.getPaidUserCount(dataQTO));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 6:{
			long paidUserCount = dataManager.getPaidUserCount(dataQTO);
			if(paidUserCount==0)
				dataDTO.setPriceOfUserAverage(0);
			else
				dataDTO.setPriceOfUserAverage((long)dataManager.getTotalAmount(dataQTO)/paidUserCount);
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
//		case 7:{
//			PageViewQTO pageViewQTO =  new PageViewQTO();
//			pageViewQTO.setSellerId(dataQTO.getSeller_id());
//			pageViewQTO.setDeviceType(dataQTO.getDevice_type());
//			pageViewQTO.setStartTime(dataQTO.getTimeStart());
//			pageViewQTO.setEndTime(dataQTO.getTimeEnd());
//			long totalUserDTO = dataManager.getTotalUserCount(dataQTO);
//			int UV =  (dataClient.countShopUvTotal(pageViewQTO,appKey)).getModule();
//			if(UV==0)
//				dataDTO.setOrderConversionRate(0);
//			else
////				dataDTO.setOrderConversionRate((totalUserDTO*0.1)/0.1/UV);
//				dataDTO.setPaidConversionRate(MoneyUtil.div(totalUserDTO+"", UV+""));
//			return ResponseUtils.getSuccessResponse(dataDTO);
//		}
//		case 8:{
//			PageViewQTO pageViewQTO =  new PageViewQTO();
//			pageViewQTO.setSellerId(dataQTO.getSeller_id());
//			pageViewQTO.setDeviceType(dataQTO.getDevice_type());
//			pageViewQTO.setStartTime(dataQTO.getTimeStart());
//			pageViewQTO.setEndTime(dataQTO.getTimeEnd());
//			long paidUserDTO = dataManager.getPaidUserCount(dataQTO);
//			int UV =  (dataClient.countShopUvTotal(pageViewQTO,appKey)).getModule();
//			if(UV==0)
//				dataDTO.setPaidConversionRate(0d);
//			else
////				dataDTO.setPaidConversionRate(Double.valueOf((paidUserDTO*0.1)/0.1/UV));
//				dataDTO.setPaidConversionRate(MoneyUtil.div(paidUserDTO+"", UV+""));
//			return ResponseUtils.getSuccessResponse(dataDTO);
//		}
		case 9:{
			DataQTO tempDataQTO = new DataQTO();
			tempDataQTO.setSeller_id(dataQTO.getSeller_id());
			tempDataQTO.setDevice_type(dataQTO.getDevice_type());
			Date tempDate = dataQTO.getTimeStart();
			Date endTime = dataQTO.getTimeEnd();
			long oldUser=0;
			while(tempDate.before(endTime)){
				tempDataQTO.setTimeStart(tempDate);
				oldUser = oldUser + dataManager.getOldUserCount(tempDataQTO);
				tempDate = DateUtil.getRelativeDate(tempDate, 1);
			}
			long totalUserCount = dataManager.getTotalUserCount(dataQTO);
			if(totalUserCount==0)
				dataDTO.setRepeatPurchaseRate(null);
			else
				dataDTO.setRepeatPurchaseRate(Double.valueOf(oldUser/totalUserCount));
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 20:{
			if(dataQTO.getUserId()==null){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"userId is null");
			}
			if(dataQTO.getItemSkuId()==null){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"ItemSkuId is null");
			}
			
			Long itemCount = dataManager.getItemCount(dataQTO);
			
			Long paidItemCount = dataManager.getPaidItemCount(dataQTO);
			
			dataDTO.setItemCount(itemCount);
			dataDTO.setPaidItemCount(paidItemCount);
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 21:{
			if(dataQTO.getItemSkuId()==null){
				return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"ItemSkuId is null");
			}
			Long paidOrderCount = dataManager.getPaidOrderCountBySkuId(dataQTO.getItemSkuId());
			dataDTO.setPaidOrderCount(paidOrderCount);
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		case 22:{
					
			Long orderTotalPrice = dataManager.getSumOrderTotalPrice(dataQTO);
			dataDTO.setTotalPrice(orderTotalPrice);
			return ResponseUtils.getSuccessResponse(dataDTO);
		}
		default :{
			log.error("data type is illegal");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_ILLEGAL,"data type is illegal");
		}
		}
	}

	@Override
	public String getName() {
		return ActionEnum.GET_DATA.getActionName();
	}

}
