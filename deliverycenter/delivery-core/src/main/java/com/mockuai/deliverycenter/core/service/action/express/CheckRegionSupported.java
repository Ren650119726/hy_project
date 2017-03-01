//package com.mockuai.deliverycenter.core.service.action.express;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressRegionDTO;
//import com.mockuai.deliverycenter.common.qto.express.ExpressRegionQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressRegionManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class CheckRegionSupported implements Action {
//	@Resource
//	ExpressRegionManager expressRegionManager;
//
//	/**
//	 * 校验快递是否支持配送接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		ExpressRegionDTO expressRegionDTO = (ExpressRegionDTO) context
//				.getRequest().getParam("expressRegionDTO");
//		// 校验参数
//		if (expressRegionDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressRegionDTO is null");
//		}
//		if (StringUtils.isEmpty(expressRegionDTO.getExpressCode())) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressCode is null");
//		}
//		if (StringUtils.isEmpty(expressRegionDTO.getProvince())) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"province is null");
//		} else {
//			expressRegionDTO.setProvince(expressRegionDTO.getProvince()
//					.replace("省", ""));
//		}
//		if (StringUtils.isEmpty(expressRegionDTO.getCity())) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"city is null");
//		} else {
//			expressRegionDTO.setCity(expressRegionDTO.getCity()
//					.replace("市", ""));
//		}
//		if (StringUtils.isEmpty(expressRegionDTO.getArea())) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"area is null");
//		} else {
//			expressRegionDTO.setArea(expressRegionDTO.getArea()
//					.replace("区", "").replace("县", ""));
//		}
//		if (StringUtils.isNotEmpty(expressRegionDTO.getTown())) {
//			expressRegionDTO.setTown(expressRegionDTO.getTown()
//					.replace("镇", "").replace("街道", "").replace("街", "")
//					.replace("乡", ""));
//		}
//		// 创建QTO
//		ExpressRegionQTO expressRegionQTO = new ExpressRegionQTO();
//		expressRegionQTO.setPageNo(1);
//		expressRegionQTO.setPageSize(50);
//		expressRegionQTO = (ExpressRegionQTO) TransUtil.trans2Qto(
//				expressRegionDTO, expressRegionQTO);
//		expressRegionQTO.setOpenstatus("全境");
//		List<ExpressRegionDTO> expressRegionDTOList = expressRegionManager
//				.queryExpressRegion(expressRegionQTO);
//		if (expressRegionDTOList.size() > 0) {
//			// 街，镇为空时,校验区下面的所有镇是否支持配送
//			if (StringUtils.isEmpty(expressRegionDTO.getTown())) {
//				if (StringUtils.isEmpty(expressRegionDTO.getDetailAddress())) {
//					throw new DeliveryException(
//							RetCodeEnum.PARAMETER_NULL.getCode(),
//							"detail Address is null");
//				}
//				for (ExpressRegionDTO dto : expressRegionDTOList) {
//					// 镇为空时区下面所有镇都支持配送
//					if (StringUtils.isEmpty(dto.getTown())) {
//						return ResponseUtil.getResponse(true);
//					}
//					// 详细地址中包含镇则支持配送
//					if (expressRegionDTO.getDetailAddress().contains(
//							dto.getTown().replace("镇", "").replace("街道", "")
//									.replace("街", "").replace("乡", ""))) {
//						return ResponseUtil.getResponse(true);
//
//					}
//				}
//				return ResponseUtil.getResponse(false);
//			}
//			// 街，镇不空时，存在全境的记录支持配送
//			else {
//				return ResponseUtil.getResponse(true);
//			}
//		} else {
//			return ResponseUtil.getResponse(false);
//		}
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.CHECK_REGION_SUPPORTED.getActionName();
//	}
//}
