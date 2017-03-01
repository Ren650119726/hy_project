package com.mockuai.deliverycenter.core.service.action.fee;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.fee.RegionManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.Action;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

@Service
public class QueryRegion implements Action {
	@Resource
	RegionManager regionManager;

	/**
	 * 查询区域接口
	 */
	@Override
	public DeliveryResponse execute(RequestContext context)
			throws DeliveryException {
		// 获取参数
		RegionQTO regionQTO = (RegionQTO) context.getRequest().getParam("regionQTO");
		// 根据QTO查询条件进行分页查询
		List<RegionDTO> modelList = regionManager.queryRegion(regionQTO);
		return ResponseUtil.getResponse(modelList, regionQTO.getTotalCount());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.QUERY_REGION.getActionName();
	}
}
