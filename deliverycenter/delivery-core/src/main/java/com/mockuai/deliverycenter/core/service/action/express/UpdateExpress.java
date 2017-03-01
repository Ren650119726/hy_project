//package com.mockuai.deliverycenter.core.service.action.express;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class UpdateExpress implements Action {
//	@Resource
//	ExpressManager expressManager;
//
//	/**
//	 * 修改快递接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		ExpressDTO expressDTO = (ExpressDTO) context.getRequest().getParam(
//				"expressDTO");
//		expressManager.updateExpress(expressDTO);
//		// 返回response对象
//		return ResponseUtil.getResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.UPDATE_EXPRESS.getActionName();
//	}
//}
