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
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.fee.RegionFeeManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.TransAction;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class DeleteRegionFee extends TransAction {
//	@Resource
//	private RegionFeeManager regionFeeManager;
//
//	/**
//	 * 删除区域运费接口
//	 */
//	@Override
//	public DeliveryResponse doTransaction(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		List<Integer> regionFeeIdList = (List<Integer>) context.getRequest()
//				.getParam("regionFeeIdList");
//		regionFeeManager.deleteRegionFee(regionFeeIdList);
//		// 返回response对象
//		return ResponseUtil.getResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.DELETE_REGION_FEE.getActionName();
//	}
//
//}
