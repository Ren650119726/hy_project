//package com.mockuai.deliverycenter.core.service.action.storage;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.api.DeliveryResponse;
//import com.mockuai.deliverycenter.common.constant.ActionEnum;
//import com.mockuai.deliverycenter.common.dto.storage.StorageDTO;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.storage.StorageManager;
//import com.mockuai.deliverycenter.core.service.RequestContext;
//import com.mockuai.deliverycenter.core.service.action.Action;
//import com.mockuai.deliverycenter.core.util.ResponseUtil;
//
//@Service
//public class AddStorage implements Action {
//	@Resource
//	StorageManager storageManager;
//
//	/**
//	 * 新增仓库接口
//	 */
//	@Override
//	public DeliveryResponse execute(RequestContext context)
//			throws DeliveryException {
//
//		// 获取参数
//		StorageDTO storageDTO = (StorageDTO) context.getRequest().getParam(
//				"storageDTO");
//		// 新增仓库
//		storageManager.addStorage(storageDTO);
//		// 返回response对象
//		return ResponseUtil.getResponse(storageDTO);
//	}
//
//	@Override
//	public String getName() {
//		// TODO Auto-generated method stub
//		return ActionEnum.ADD_STORAGE.getActionName();
//	}
//}
