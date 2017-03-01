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
//import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.fee.RegionFeeManager;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.TransAction;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class AddStdFee extends TransAction {
//	@Resource
//	StdFeeManager stdFeeManager;
//	@Resource
//	RegionFeeManager regionFeeManager;
//
//	/**
//	 * 新增运费标准接口
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public DeliveryResponse doTransaction(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		StdFeeDTO stdFeeDTO = (StdFeeDTO) context.getRequest().getParam(
//				"stdFeeDTO");
//		// 新增运费标准
//		stdFeeManager.addStdFee(stdFeeDTO);
//		for (RegionFeeDTO regionFeeDTO : stdFeeDTO.getRegionFeeDTOList()) {
//			regionFeeDTO.setFeeId(stdFeeDTO.getId());
//		}
//		// 新增运费标准属性
//		List<RegionFeeDTO> regionFeeDTOList = regionFeeManager
//				.addRegionFee(stdFeeDTO.getRegionFeeDTOList());
//		// 属性设置到运费标准DTO中
//		stdFeeDTO.setRegionFeeDTOList(regionFeeDTOList);
//		// 返回response对象
//		return ResponseUtil.getResponse(stdFeeDTO);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.ADD_STDFEE.getActionName();
//	}
//}
