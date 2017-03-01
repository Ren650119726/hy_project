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
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.fee.RegionFeeManager;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class QueryStdFee implements Action {
//	@Resource
//	StdFeeManager stdFeeManager;
//	@Resource
//	RegionFeeManager regionFeeManager;
//
//	/**
//	 * 查询运费标准接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		StdFeeQTO stdFeeQTO = (StdFeeQTO) context.getRequest().getParam(
//				"stdFeeQTO");
//		// 根据QTO查询条件进行分页查询
//		List<StdFeeDTO> modelList = stdFeeManager.queryStdFee(stdFeeQTO);
//		// 设置地域运费属性
//		for (StdFeeDTO stdFeeDTO : modelList) {
//			List regionFeeDTOList = regionFeeManager.queryByFeeId(stdFeeDTO
//					.getId());
//			stdFeeDTO.setRegionFeeDTOList(regionFeeDTOList);
//		}
//		return ResponseUtil.getResponse(modelList, stdFeeQTO.getTotalCount());
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.QUERY_STDFEE.getActionName();
//	}
//}
