//package com.mockuai.deliverycenter.core.service.action.express;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressPropertyManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.TransAction;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class DeleteExpress extends TransAction {
//	@Resource
//	ExpressManager expressManager;
//	@Resource
//	ExpressPropertyManager expressPropertyManager;
//
//	/**
//	 * 删除快递接口
//	 */
//	@Override
//	public DeliveryResponse doTransaction(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		Integer id = (Integer) context.getRequest().getParam("id");
//		// 根据ID查询快递属性
//		List<ExpressPropertyDTO> expressPropertyDTOList = expressPropertyManager
//				.queryByExpressId(id);
//		List<Integer> proeprtyIdList = new ArrayList();
//		for (ExpressPropertyDTO expressPropertyDTO : expressPropertyDTOList) {
//			proeprtyIdList.add(expressPropertyDTO.getId());
//		}
//		// 批量删除快递属性
//		expressPropertyManager.deleteExpressProperty(proeprtyIdList);
//		// 删除快递
//		expressManager.deleteExpress(id);
//		// 返回response对象
//		return ResponseUtil.getResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.DELETE_EXPRESS.getActionName();
//	}
//}
