//package com.mockuai.deliverycenter.core.service.action.fee;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class UpdateStdFee implements Action {
//	@Resource
//	StdFeeManager stdFeeManager;
//
//	/**
//	 * 修改运费标准接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		StdFeeDTO stdFeeDTO = (StdFeeDTO) context.getRequest().getParam(
//				"stdFeeDTO");
//		stdFeeManager.updateStdFee(stdFeeDTO);
//		// 返回response对象
//		return ResponseUtil.getResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.UPDATE_STDFEE.getActionName();
//	}
//}
