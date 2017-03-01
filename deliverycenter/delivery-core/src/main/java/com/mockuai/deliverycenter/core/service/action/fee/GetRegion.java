package com.mockuai.deliverycenter.core.service.action.fee;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.core.domain.fee.Region;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.fee.RegionManager;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.Action;
import com.mockuai.deliverycenter.core.util.ResponseUtil;
import com.mockuai.deliverycenter.core.util.TransUtil;

@Service
public class GetRegion implements Action {
	@Resource
	RegionManager regionManager;

	/**
	 * 根据ID查询区域接口
	 */
	@Override
	public DeliveryResponse execute(RequestContext context)
			throws DeliveryException {
		// 获取参数
		String regionCode = (String) context.getRequest().getParam("regionCode");
		// 根据ID查询
		Region region = regionManager.getRegion(regionCode);
		// 创建一个DtO
		RegionDTO regionDTO = new RegionDTO();
		// DO转换成DTO
		regionDTO = (RegionDTO) TransUtil.trans2Dto(regionDTO, region);
		return ResponseUtil.getResponse(regionDTO);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.GET_REGION.getActionName();
	}
}
