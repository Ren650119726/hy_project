//package com.mockuai.deliverycenter.core.manager.express.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressRegionDTO;
//import com.mockuai.deliverycenter.common.qto.express.ExpressRegionQTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressRegionManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class ExpressRegionImpl extends BaseManager implements
//		ExpressRegionManager {
//
//	@Override
//	public List<ExpressRegionDTO> queryExpressRegion(
//			ExpressRegionQTO expressRegionQTO) throws DeliveryException {
//		if (expressRegionQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressRegionQTO is null");
//		}
//		List<BaseDo> expressRegionList = query(expressRegionQTO);
//
//		List<ExpressRegionDTO> expressRegionDTOList = new ArrayList<ExpressRegionDTO>();
//		for (BaseDo expressRegion : expressRegionList) {
//			// 创建一个DtO
//			ExpressRegionDTO expressRegionDTO = new ExpressRegionDTO();
//			// DO转换成DTO
//			expressRegionDTO = (ExpressRegionDTO) TransUtil.trans2Dto(
//					expressRegionDTO, expressRegion);
//			expressRegionDTOList.add(expressRegionDTO);
//		}
//		return expressRegionDTOList;
//	}
//
//}
