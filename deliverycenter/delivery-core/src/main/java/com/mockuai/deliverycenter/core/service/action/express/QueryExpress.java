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
//import com.mockuai.deliverycenter.common.qto.express.ExpressQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressPropertyManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class QueryExpress implements Action {
//	@Resource
//	ExpressManager expressManager;
//	@Resource
//	ExpressPropertyManager expressPropertyManager;
//
//	/**
//	 * 查询快递接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		ExpressQTO expressQTO = (ExpressQTO) context.getRequest().getParam(
//				"expressQTO");
//		// 根据QTO查询条件进行分页查询
//		List<ExpressDTO> modelList = expressManager.queryExpress(expressQTO);
//		// 设置快递属性
//		for (ExpressDTO expressDTO : modelList) {
//			List expressPropertyDTOList = expressPropertyManager
//					.queryByExpressId(expressDTO.getId());
//			expressDTO.setExpressPropertyDTOList(expressPropertyDTOList);
//		}
//		return ResponseUtil.getResponse(modelList, expressQTO.getTotalCount());
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.QUERY_EXPRESS.getActionName();
//	}
//}
