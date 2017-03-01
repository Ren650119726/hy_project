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
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressPropertyManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.TransAction;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class DeleteExpressProperty extends TransAction {
//	@Resource
//	private ExpressPropertyManager expressPropertyManager;
//
//	/**
//	 * 删除快递属性接口
//	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
//	public DeliveryResponse doTransaction(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		List<Integer> expressPropertyIdList = (List<Integer>) context
//				.getRequest().getParam("expressPropertyIdList");
//		expressPropertyManager.deleteExpressProperty(expressPropertyIdList);
//		// 返回response对象
//		return ResponseUtil.getResponse(true);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.DELETE_EXPRESS_PROPERTY.getActionName();
//	}
//
//}
