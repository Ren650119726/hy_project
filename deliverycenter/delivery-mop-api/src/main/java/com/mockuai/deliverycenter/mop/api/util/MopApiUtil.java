package com.mockuai.deliverycenter.mop.api.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.OrderUidDTO;
import com.mockuai.mop.common.service.action.MopResponse;

public class MopApiUtil {
	
	private static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);
	
	private static final String UID_SEPERATOR = "_";
	
	
	public static <T> MopResponse<T> transferResp(Response<T> deliveryResp){
        //TODO refactor the code
		System.out.println(deliveryResp.getCode());
		System.out.println(deliveryResp.getMessage());
		System.out.println(deliveryResp.getModule());
		Long successCode = Long.valueOf(RetCodeEnum.SUCCESS.getCode());
        if(deliveryResp.getCode() != RetCodeEnum.SUCCESS.getCode()){
        	//TODO 封装错误信息
        	int code = Integer.valueOf(deliveryResp.getCode());
        	return new MopResponse(code,deliveryResp.getMessage());
            //return new MopResponse<T>(MopRespCode.S_E_SERVICE_ERROR);
        }else{
            return new MopResponse<T>(deliveryResp.getModule());
        }
    }

	public static List<MopDeliveryDetailDTO> genMopDeliveryDetailList(List<DeliveryDetailDTO> deliveryDetailDTOs){
		if(deliveryDetailDTOs == null){
			return null;
		}
		List<MopDeliveryDetailDTO> mopDeliveryDetailDTOs = new ArrayList<MopDeliveryDetailDTO>();
		for(DeliveryDetailDTO deliveryDetailDTO: deliveryDetailDTOs){
			mopDeliveryDetailDTOs.add(genMopDeliveryDetail(deliveryDetailDTO));
		}
		return mopDeliveryDetailDTOs;
	}

	public static MopDeliveryDetailDTO genMopDeliveryDetail(DeliveryDetailDTO deliveryDetailDTO){
		MopDeliveryDetailDTO mopDeliveryDetailDTO = new MopDeliveryDetailDTO();
		mopDeliveryDetailDTO.setDeliveryDetailUid(deliveryDetailDTO.getUserId()+"_"+deliveryDetailDTO.getId());
		mopDeliveryDetailDTO.setOpTime(DATE_FORMAT.format(deliveryDetailDTO.getOpTime()));
		mopDeliveryDetailDTO.setContent(deliveryDetailDTO.getContent());

		return mopDeliveryDetailDTO;
	}

	public static OrderUidDTO parseOrderUid(String orderUid) {
		if(orderUid == null){
			return null;
		}

		String[] strs = orderUid.split("_");
		if (strs.length != 3) {
			return null;
		}

		OrderUidDTO orderUidDTO = new OrderUidDTO();
		long sellerId = Long.parseLong(strs[0]);
		long buyerId = Long.parseLong(strs[1]);
		long orderId = Long.parseLong(strs[2]);

		orderUidDTO.setSellerId(Long.valueOf(sellerId));
		orderUidDTO.setUserId(Long.valueOf(buyerId));
		orderUidDTO.setOrderId(Long.valueOf(orderId));
		return orderUidDTO;
	}
	
	/**
	 * @param dateStr
	 * @param isMandatory
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr)throws ParseException{
		Date date = DATE_FORMAT.parse(dateStr);
		return date;
	}
	
	public static void main(String args[])throws Exception{
		Date date =parseDate("2015-3-5 11:11:11");
		System.out.println(date);
	}
	
	
}
