//package com.mockuai.deliverycenter.core.service.action.fee;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.common.qto.fee.DeliveryFeeQTO;
//import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.fee.RegionFeeManager;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class QueryDeliveryFee implements Action {
//	@Resource
//	StdFeeManager stdFeeManager;
//	@Resource
//	private RegionFeeManager regionFeeManager;
//
//	/**
//	 * 查询运费接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		DeliveryFeeQTO deliveryFeeQTO = (DeliveryFeeQTO) context.getRequest()
//				.getParam("deliveryFeeQTO");
//		if (deliveryFeeQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"deliveryFeeQTO is null");
//		}
//		StdFeeQTO stdFeeQTO = new StdFeeQTO();
//		stdFeeQTO.setPageNo(1);
//		stdFeeQTO.setPageSize(1);
//		// 根据排序查询运费标准
//		List<StdFeeDTO> stdFeeList = stdFeeManager.queryStdFee(stdFeeQTO);
//		// 没有配置运费标准返回空
//		if (stdFeeList == null || stdFeeList.size() == 0) {
//			return ResponseUtil.getResponse(0l);
//		}
//		Long deliveryFee = 0l;
//		// 默认取排序最前面的运费标准
//		StdFeeDTO useStdFeeDTO = stdFeeList.get(0);
//		if (deliveryFeeQTO.getRegionId() == null
//				&& deliveryFeeQTO.getWeight() == null) {
//			return ResponseUtil.getResponse(useStdFeeDTO.getFirstFee());
//		}
//		// 根据运费标准取区域运费配置
//		List<RegionFeeDTO> regionFeeDTOList = regionFeeManager
//				.queryByFeeId(useStdFeeDTO.getId());
//		RegionFeeDTO configRegionFee = null;
//		// 循环判断是否配置了该地区的运费
//		for (RegionFeeDTO regionFeeDTO : regionFeeDTOList) {
//			if (regionFeeDTO.getRegionId().toString()
//					.equals(deliveryFeeQTO.getRegionId().toString())) {
//				configRegionFee = regionFeeDTO;
//				break;
//			}
//		}
//		// 取该地区配置的运费
//		if (configRegionFee != null) {
//			deliveryFee = computeDeliveryFee(deliveryFeeQTO.getWeight(),
//					configRegionFee.getFirstWeight(),
//					configRegionFee.getContinueWeight(),
//					configRegionFee.getFirstFee(),
//					configRegionFee.getContinueFee());
//
//		} else {
//			// 取默认运费标准
//			if (useStdFeeDTO.getAllowDefault() == 1) {
//				deliveryFee = computeDeliveryFee(deliveryFeeQTO.getWeight(),
//						useStdFeeDTO.getFirstWeight(),
//						useStdFeeDTO.getContinueWeight(),
//						useStdFeeDTO.getFirstFee(),
//						useStdFeeDTO.getContinueFee());
//			}
//		}
//		// 如果开启了限价，则计算
//		if (deliveryFee != null && useStdFeeDTO.getFeeLimitMark() != null
//				&& useStdFeeDTO.getFeeLimitMark() == 1) {
//			if (deliveryFee < useStdFeeDTO.getMinFee()) {
//				deliveryFee = useStdFeeDTO.getMinFee();
//			}
//			if (deliveryFee > useStdFeeDTO.getMaxFee()) {
//				deliveryFee = useStdFeeDTO.getMaxFee();
//			}
//		}
//		return ResponseUtil.getResponse(deliveryFee);
//	}
//
//	private Long computeDeliveryFee(Long weight, Long firstWeight,
//			Long continueWeight, Long firstFee, Long continueFee) {
//		Long deliveryFee = null;
//		if (weight <= firstWeight) {
//			return firstFee;
//		}
//		if (continueWeight != 0) {
//			deliveryFee = firstFee + (weight - firstWeight) * continueFee
//					/ continueWeight;
//		} else {
//			deliveryFee = firstFee;
//		}
//		return deliveryFee;
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.QUERY_DELIVERYFEE.getActionName();
//	}
//}
