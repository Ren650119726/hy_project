//package com.mockuai.deliverycenter.core.service.action.storage;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.storage.StorageManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class QueryStorage implements Action {
//	@Resource
//	StorageManager storageManager;
//
//	/**
//	 * 查询仓库接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		StorageQTO storageQTO = (StorageQTO) context.getRequest().getParam(
//				"storageQTO");
//		// 根据QTO查询条件进行分页查询
//		List<StorageDTO> modelList = storageManager.queryStorage(storageQTO);
//		return ResponseUtil.getResponse(modelList, storageQTO.getTotalCount());
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.QUERY_STORAGE.getActionName();
//	}
//}
