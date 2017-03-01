//package com.mockuai.deliverycenter.core.service.action.express;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
//import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressPropertyManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.TransAction;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class AddExpress extends TransAction {
//	@Resource
//	ExpressManager expressManager;
//	@Resource
//	ExpressPropertyManager expressPropertyManager;
//
//	/**
//	 * 新增快递接口
//	 *
//	 * @throws DeliveryException
//	 */
//	@Override
//	public DeliveryResponse doTransaction(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		ExpressDTO expressDTO = (ExpressDTO) context.getRequest().getParam(
//				"expressDTO");
//		// 新增快递
//		expressManager.addExpress(expressDTO);
//		if (expressDTO.getExpressPropertyDTOList() != null) {
//			for (ExpressPropertyDTO expressPropertyDTO : expressDTO
//					.getExpressPropertyDTOList()) {
//				expressPropertyDTO.setExpressId(expressDTO.getId());
//			}
//			// 新增快递属性
//			List<ExpressPropertyDTO> expressPropertyDTOList = expressPropertyManager
//					.addExpressProperty(expressDTO.getExpressPropertyDTOList());
//			// 属性设置到快递DTO中
//			expressDTO.setExpressPropertyDTOList(expressPropertyDTOList);
//		}
//		// 返回response对象
//		return ResponseUtil.getResponse(expressDTO);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.ADD_EXPRESS.getActionName();
//	}
//}
