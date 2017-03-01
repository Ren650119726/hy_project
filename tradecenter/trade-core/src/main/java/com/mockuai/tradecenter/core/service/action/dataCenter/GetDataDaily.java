package com.mockuai.tradecenter.core.service.action.dataCenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.DataDTO;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.DataManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.service.action.order.ConfirmReceipt;
import com.mockuai.tradecenter.core.util.DateUtil;

public class GetDataDaily implements Action{

    private static final Logger log = LoggerFactory.getLogger(ConfirmReceipt.class);
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Resource
	private DataManager dataManager;
	


	@Override
	public TradeResponse<Map<String,DataDTO>> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		Map<String,DataDTO> result = new HashMap<String, DataDTO>();
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
		
		switch(type){
		case 10:{
			List tempResult = dataManager.getTotalAmountDaily(dataQTO);
			for(int i=0;i<tempResult.size();i++){
				DailyDataDO dataDO =  (DailyDataDO) tempResult.get(i);
				DataDTO tempDataDTO = new DataDTO();
				tempDataDTO.setTotalAmount(dataDO.getValue());
				result.put(sdf.format(dataDO.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		case 11:{
			List<DailyDataDO> tempResult = dataManager.getTotalOrderCountDaily(dataQTO);
			for(int i=0;i<tempResult.size();i++){
				DailyDataDO dataDO =  (DailyDataDO) tempResult.get(i);
				DataDTO tempDataDTO = new DataDTO();
				tempDataDTO.setTotalOrderCount(dataDO.getValue());
				result.put(sdf.format(dataDO.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		case 12:{
			List<DailyDataDO> tempResult = dataManager.getPaidOrderCountDaily(dataQTO);
			for(int i=0;i<tempResult.size();i++){
				DailyDataDO dataDO =  (DailyDataDO) tempResult.get(i);
				DataDTO tempDataDTO = new DataDTO();
				tempDataDTO.setPaidOrderCount(dataDO.getValue());
				result.put(sdf.format(dataDO.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		case 13:{
			List<DailyDataDO> tempResult = dataManager.getTotalUserCountDaily(dataQTO);
			for(int i=0;i<tempResult.size();i++){
				DailyDataDO dataDO =  (DailyDataDO) tempResult.get(i);
				DataDTO tempDataDTO = new DataDTO();
				tempDataDTO.setTotalUserCount(dataDO.getValue());
				result.put(sdf.format(dataDO.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		case 14:{
			List<DailyDataDO> tempResult = dataManager.getPaidUserCountDaily(dataQTO);
			for(int i=0;i<tempResult.size();i++){
				DailyDataDO dataDO =  (DailyDataDO) tempResult.get(i);
				DataDTO tempDataDTO = new DataDTO();
				tempDataDTO.setPaidUserCount(dataDO.getValue());
				result.put(sdf.format(dataDO.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		case 15:{
			List<DailyDataDO> paidUserCount = dataManager.getPaidUserCountDaily(dataQTO);
			List<DailyDataDO> totalAmount = dataManager.getTotalAmountDaily(dataQTO);
			for(int i=0;i<paidUserCount.size();i++){
				DataDTO tempDataDTO = new DataDTO();
				DailyDataDO paidUser = paidUserCount.get(i);
				if(paidUser.getValue()==0){
					tempDataDTO.setPriceOfUserAverage(0);
				}else{
					DailyDataDO tempTotalAmount =  (DailyDataDO) totalAmount.get(i);
					tempDataDTO.setPriceOfUserAverage(tempTotalAmount.getValue()/paidUser.getValue());
				}
				result.put(sdf.format(paidUser.getDate()),tempDataDTO);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
//		case 16:{
//			PageViewQTO pageViewQTO =  new PageViewQTO();
//			pageViewQTO.setSellerId(dataQTO.getSeller_id());
//			pageViewQTO.setDeviceType(dataQTO.getDevice_type());
//			pageViewQTO.setStartTime(dataQTO.getTimeStart());
//			pageViewQTO.setEndTime(dataQTO.getTimeEnd());
//			List<PageViewDTO> UV = (List<PageViewDTO>) dataClient.queryShopUv(pageViewQTO,appKey).getModule();
//			List<DailyDataDO> totalUser = dataManager.getTotalUserCountDaily(dataQTO);
//			for(int i=0;i<totalUser.size();i++){
//				DataDTO tempDataDTO = new DataDTO();
//				PageViewDTO tempUV = UV.get(i);
//				if(tempUV.getNum()==0){
//					tempDataDTO.setOrderConversionRate(0);
//				}else{
//					DailyDataDO tempTotalUser =  (DailyDataDO) totalUser.get(i);
//					tempDataDTO.setOrderConversionRate(tempTotalUser.getValue()/tempUV.getNum());
//				}
//				result.put(sdf.format(tempUV.getResultDate()),tempDataDTO);
//			}
//			return ResponseUtils.getSuccessResponse(result);
//		}
//		case 17:{
//			PageViewQTO pageViewQTO =  new PageViewQTO();
//			pageViewQTO.setSellerId(dataQTO.getSeller_id());
//			pageViewQTO.setDeviceType(dataQTO.getDevice_type());
//			pageViewQTO.setStartTime(dataQTO.getTimeStart());
//			pageViewQTO.setEndTime(dataQTO.getTimeEnd());
//			List<PageViewDTO> UV = (List<PageViewDTO>) dataClient.queryShopUv(pageViewQTO,appKey).getModule();
//			List<DailyDataDO> paidUser = dataManager.getPaidUserCountDaily(dataQTO);
//			for(int i=0;i<paidUser.size();i++){
//				DataDTO tempDataDTO = new DataDTO();
//				PageViewDTO tempUV = UV.get(i);
//				if(tempUV.getNum()==0){
//					tempDataDTO.setPaidConversionRate(null);
//				}else{
//					DailyDataDO tempPaidUser =  (DailyDataDO) paidUser.get(i);
//					Double b = Double.valueOf(tempPaidUser.getValue()/tempUV.getNum());
//					if(b!=null)
//						tempDataDTO.setPaidConversionRate(b);
//				}
//				result.put(sdf.format(tempUV.getResultDate()),tempDataDTO);
//			}
//			return ResponseUtils.getSuccessResponse(result);
//		}
		case 18:{
			DataQTO tempDataQTO = new DataQTO();
			tempDataQTO.setSeller_id(dataQTO.getSeller_id());
			tempDataQTO.setDevice_type(dataQTO.getDevice_type());
			List<DailyDataDO> totalUser = dataManager.getTotalUserCountDaily(dataQTO);
			Date tempDate = dataQTO.getTimeStart();
			for(int i=0;i<totalUser.size();i++){
				DataDTO tempDataDTO = new DataDTO();
				DailyDataDO tempTotalUser = totalUser.get(i);
				if(tempTotalUser.getValue()==0){
					tempDataDTO.setRepeatPurchaseRate(null);
				}else{
					tempDataQTO.setTimeStart(tempDate);
					long oldUser = dataManager.getOldUserCount(tempDataQTO);
					Double b = Double.valueOf(oldUser/tempTotalUser.getValue());
					if(b!=null)
						tempDataDTO.setRepeatPurchaseRate(b);
				}
				result.put(sdf.format(tempTotalUser.getDate()),tempDataDTO);
				tempDate = DateUtil.getRelativeDate(tempDate, 1);
			}
			return ResponseUtils.getSuccessResponse(result);
		}
		default :{
			log.error("data type is illegal");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_ILLEGAL,"data type is illegal");
		}
		}
	}

	@Override
	public String getName() {
		return ActionEnum.GET_DATA_DAILY.getActionName();
	}

}
