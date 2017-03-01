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
//import com.mockuai.deliverycenter.common.dto.storage.StorageSendDTO;
//import com.mockuai.deliverycenter.common.qto.storage.StorageSendQTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.storage.StorageSendManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class QueryStorageSend implements Action {
//	@Resource
//	StorageSendManager storageSendManager;
//
//	/**
//	 * 查询发货仓库接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//		// 获取参数
//		StorageSendQTO storageSendQTO = (StorageSendQTO) context.getRequest()
//				.getParam("storageSendQTO");
//		// 根据QTO查询条件进行分页查询
//		List<StorageSendDTO> modelList = storageSendManager
//				.queryStorageSend(storageSendQTO);
//		return ResponseUtil.getResponse(modelList,
//				storageSendQTO.getTotalCount());
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.QUERY_STORAGE_SEND.getActionName();
//	}
//}
