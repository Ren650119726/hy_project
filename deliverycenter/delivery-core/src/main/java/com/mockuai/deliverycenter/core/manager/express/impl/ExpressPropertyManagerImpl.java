//package com.mockuai.deliverycenter.core.manager.express.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.express.Express;
//import com.mockuai.deliverycenter.core.domain.express.ExpressProperty;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressPropertyManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class ExpressPropertyManagerImpl extends BaseManager implements
//		ExpressPropertyManager {
//	@Resource
//	ExpressManager expressManager;
//
//	@Override
//	public List<ExpressPropertyDTO> addExpressProperty(
//			List<ExpressPropertyDTO> expressPropertyDTOList)
//			throws DeliveryException {
//		if (expressPropertyDTOList == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressPropertyDTOList is null");
//		}
//		List<ExpressPropertyDTO> resultList = new ArrayList();
//		for (ExpressPropertyDTO expressPropertyDTO : expressPropertyDTOList) {
//			// 校验快递是否存在
//			Express express = expressManager.getExpress(expressPropertyDTO
//					.getExpressId());
//			if (express == null) {
//				throw new DeliveryException(
//						RetCodeEnum.PARAMETER_ERROR.getCode(),
//						"express_id not exist");
//			}
//			// 创建一个DO
//			BaseDo expressProperty = new ExpressProperty();
//			// DTO转换成DO
//			expressProperty = TransUtil.trans2Do(expressPropertyDTO,
//					expressProperty);
//			// 执行新增操作
//			expressProperty = getBaseDao().insert(expressProperty);
//			// DO转换成DTO
//			expressPropertyDTO = (ExpressPropertyDTO) TransUtil.trans2Dto(
//					expressPropertyDTO, expressProperty);
//			resultList.add(expressPropertyDTO);
//		}
//		return expressPropertyDTOList;
//	}
//
//	@Override
//	public int deleteExpressProperty(List<Integer> expressPropertyIdList)
//			throws DeliveryException {
//		if (expressPropertyIdList == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressPropertyIdList is null");
//		}
//		int deleteCount = 0;
//		for (Integer propertyId : expressPropertyIdList) {
//			// 创建一个DO
//			ExpressProperty expressProperty = new ExpressProperty();
//			// DO赋值逻辑删除
//			expressProperty.setId(propertyId);
//			expressProperty.setDeleted(1);
//			// 执行更新操作
//			int row = getBaseDao().update(expressProperty);
//			deleteCount += row;
//		}
//		if (deleteCount != expressPropertyIdList.size()) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return deleteCount;
//	}
//
//	@Override
//	public List<ExpressPropertyDTO> queryByExpressId(Integer expressId)
//			throws DeliveryException {
//		if (expressId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressId is null");
//		}
//		List<ExpressProperty> expressPropertyList = getBaseDao().queryForList(
//				"EXPRESSPROPERTY.queryByExpressId", expressId);
//
//		List<ExpressPropertyDTO> expressPropertyDTOList = new ArrayList<ExpressPropertyDTO>();
//		for (ExpressProperty expressProperty : expressPropertyList) {
//			// 创建一个DtO
//			ExpressPropertyDTO expressPropertyDTO = new ExpressPropertyDTO();
//			// DO转换成DTO
//			expressPropertyDTO = (ExpressPropertyDTO) TransUtil.trans2Dto(
//					expressPropertyDTO, expressProperty);
//			expressPropertyDTOList.add(expressPropertyDTO);
//		}
//		return expressPropertyDTOList;
//	}
//
//}
