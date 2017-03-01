package com.mockuai.deliverycenter.core.service.action.fee;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.fee.RegionManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.Action;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class DeleteRegion implements Action {
	@Resource
	RegionManager regionManager;

	/**
	 * 删除区域接口
	 */
	@Override
	public DeliveryResponse execute(RequestContext context)
			throws DeliveryException {
		// 获取参数
		String regionCode = (String) context.getRequest().getParam("regionCode");
		// 删除区域
		regionManager.deleteRegion(regionCode);
		// 返回response对象
		return ResponseUtil.getResponse(true);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.DELETE_REGION.getActionName();
	}
}
