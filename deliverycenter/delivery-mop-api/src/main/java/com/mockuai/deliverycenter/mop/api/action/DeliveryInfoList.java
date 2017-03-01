package com.mockuai.deliverycenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.mockuai.deliverycenter.common.api.BaseRequest;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryDetailDTO;
import com.mockuai.deliverycenter.mop.api.dto.MopDeliveryInfoDTO;
import com.mockuai.deliverycenter.mop.api.dto.OrderUidDTO;
import com.mockuai.deliverycenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
/**
* 提供接口用于查询物信息
* @author cwr
*/
public class DeliveryInfoList extends BaseAction{


	public MopResponse execute(Request request) {
		if(StringUtils.isBlank((String)request.getParam("order_uid"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "order_uid["+(String)request.getParam("order_uid")+"]为空");			
    	}
    	if(StringUtils.isBlank((String)request.getParam("app_key"))){
    		return new MopResponse(MopRespCode.P_E_PARAM_ISNULL, "app_key["+(String)request.getParam("app_key")+"]为空");			
    	}
        String uidStr = (String)request.getParam("order_uid");
        String appKey = (String)request.getParam("app_key");
        com.mockuai.deliverycenter.common.api.Request deliveryRequest = new BaseRequest();
        
        OrderUidDTO orderUidDto = null;
        try {        	
            orderUidDto = MopApiUtil.parseOrderUid(uidStr);
            if(orderUidDto == null){
            	return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid["+(String)request.getParam("order_uid")+"]格式有误");
            }
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_IS_INVALID, "order_uid["+(String)request.getParam("order_uid")+"]格式有误");
		}
    	//获取物流单号 快递公司等信息
		deliveryRequest.setParam("deliveryDetailQTO", null);
		DeliveryInfoQTO deliveryInfoQTO =new DeliveryInfoQTO();
		deliveryInfoQTO.setUserId(orderUidDto.getUserId());
		deliveryInfoQTO.setOrderId(orderUidDto.getOrderId());
		deliveryRequest.setParam("deliveryInfoQTO", deliveryInfoQTO);
		deliveryRequest.setCommand("QueryDeliveryInfo");
		deliveryRequest.setParam("appKey", appKey);
		Response<List<DeliveryInfoDTO>> deliveryResp2 = this.getDeliveryService().execute(deliveryRequest);

		/*暂时用不到，查看物流详情表获取存库的物流信息*/
	
    	/*deliveryRequest.setCommand("QueryDeliveryDetail");
		DeliveryDetailQTO deliveryDetailQTO = new DeliveryDetailQTO();
		deliveryDetailQTO.setOrderId(orderUidDto.getOrderId());
		deliveryDetailQTO.setUserId(orderUidDto.getUserId());
		deliveryRequest.setParam("deliveryDetailQTO", deliveryDetailQTO);
		//TODO 重构以下逻辑，放到一个服务端ACTION中完成
        //获取物流明细信息

		Response<List<DeliveryDetailDTO>> deliveryResp = this.getDeliveryService().execute(deliveryRequest);
		
		Map<String,List<MopDeliveryDetailDTO>> deliveryMap = new HashMap<String,List<MopDeliveryDetailDTO>>();
		if(deliveryResp.getModule() != null){
			List<DeliveryDetailDTO> detailModule = deliveryResp.getModule();
			for (int i = 0; i < detailModule.size(); i++) {
				DeliveryDetailDTO detail = detailModule.get(i);
				String deliveryCode = detail.getDeliveryCode();//物流单号
				if(deliveryCode != null){
					List<MopDeliveryDetailDTO> list = deliveryMap.get(deliveryCode);
					MopDeliveryDetailDTO dto = MopApiUtil.genMopDeliveryDetail(detail);
					if(list == null){
						List<MopDeliveryDetailDTO> newList = new ArrayList<MopDeliveryDetailDTO>();
						newList.add(dto);
						deliveryMap.put(deliveryCode, newList);
					}else{
						list.add(dto);
					}
				}
			}
		}*/

		List<DeliveryInfoDTO> infoModule = deliveryResp2.getModule();
		List<MopDeliveryInfoDTO> deliveryInfoList = new ArrayList<MopDeliveryInfoDTO>();
		if(infoModule != null){
			for(DeliveryInfoDTO item : infoModule){
				MopDeliveryInfoDTO info =new MopDeliveryInfoDTO();
				info.setDeliveryCode(item.getExpressNo());
				info.setDeliveryCompany(item.getExpress());
				info.setOrderItemList(item.getOrderItemList());
				info.setExpressUrl(item.getExpressUrl());
				//info.setDeliveryType(deliveryType); //该字段是否需要 ？
				//info.setDeliveryFee(); //该字段是否需要?
				info.setDeliveryInfoUid(item.getDeliveryInfoUid());
				List<ThirdpartyExpressDetailDTO> thirdpartyExpressDetailList = item.getThirdpartyList();
				if(null!=thirdpartyExpressDetailList&&thirdpartyExpressDetailList.size()>0){
					List<MopDeliveryDetailDTO> mopDeliveryDetailList = new ArrayList<MopDeliveryDetailDTO>();
    				for(ThirdpartyExpressDetailDTO dto:thirdpartyExpressDetailList){
    					MopDeliveryDetailDTO mopDetailDTO = new MopDeliveryDetailDTO();
    					mopDetailDTO.setContent(dto.getContext());
    					mopDetailDTO.setOpTime(dto.getTime());
    					mopDeliveryDetailList.add(mopDetailDTO);
    				}
    				info.setDeliveryDetailList(mopDeliveryDetailList);
    			}else{
    				/*info.setDeliveryDetailList(deliveryMap.get(info.getDeliveryCode()));*/
				}
				deliveryInfoList.add(info);
			}
		}

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("delivery_info_list", deliveryInfoList);
		return new MopResponse(data);
    }
		
		//TODO 重构以下逻辑，放到一个服务端ACTION中完成
        //获取物流明细信息
		

//		List<MopDeliveryDetailDTO> detailList = new ArrayList<MopDeliveryDetailDTO>();
//		if(deliveryResp.getModule() != null){
//			List<DeliveryDetailDTO> detailModule = deliveryResp.getModule();
//			for (int i = 0; i < detailModule.size(); i++) {
//				DeliveryDetailDTO detail = detailModule.get(i);
//				String deliveryCode = detail.getDeliveryCode();//物流单号
//				if(deliveryCode != null){
//					MopDeliveryDetailDTO dto =new MopDeliveryDetailDTO();
//					dto.setOpTime(detail.getOpTime().toString());
//					dto.setContent(detail.getContent());
//					dto.setDeliveryDetailUid(detail.getDeliveryDetailUid());
//					detailList.add(dto);
//				}
//			}
//		}

//
//		// 获取物流单号 快递公司等信息
//		deliveryRequest.setParam("deliveryDetailQTO", null);
//		DeliveryInfoQTO deliveryInfoQTO = new DeliveryInfoQTO();
//		deliveryInfoQTO.setUserId(orderUidDto.getUserId());
//		deliveryInfoQTO.setOrderId(orderUidDto.getId());
//		deliveryRequest.setParam("deliveryInfoQTO", deliveryInfoQTO);
//		Response<List<DeliveryInfoDTO>> deliveryResp2 = this
//				.getDeliveryService().execute(deliveryRequest);
//		if (deliveryResp.getCode() != RetCodeEnum.SUCCESS.getCode()) {
//			int code = Integer.valueOf(deliveryResp.getCode());
//			return new MopResponse(code, deliveryResp.getMessage());
//		}
//		List<DeliveryInfoDTO> infoModule = deliveryResp2.getModule();
//		List<MopDeliveryInfoDTO> deliveryInfoList =new ArrayList<MopDeliveryInfoDTO>();
//		if(infoModule !=null){
//			for(DeliveryInfoDTO item : infoModule){
//				MopDeliveryInfoDTO info =new MopDeliveryInfoDTO();
//				info.setDeliveryCode(item.getExpressNo());
//				info.setDeliveryCompany(item.getExpress());
//				//info.setDeliveryType(deliveryType); //该字段是否需要 ？
//				//info.setDeliveryFee(); //该字段是否需要 暂时无法取?
//				info.setDeliveryInfoUid(item.getDeliveryInfoUid());
//				deliveryInfoList.add(info);
//			}
//		}
//		return new MopResponse(deliveryInfoList);

		// 如下代码考虑一个订单有多个快递单情况 按照快递单分组
		


	public String getName() {
		return "/trade/order/delivery_info/list";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}

}
