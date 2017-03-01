//package com.mockuai.deliverycenter.core.manager.express.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
//import com.mockuai.deliverycenter.common.qto.express.ExpressQTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.express.Express;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.express.ExpressManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class ExpressManagerImpl extends BaseManager implements ExpressManager {
//
//	@Override
//	public ExpressDTO addExpress(ExpressDTO expressDTO)
//			throws DeliveryException {
//		if (expressDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressDTO is null");
//		}
//		// 创建一个DO
//		BaseDo express = new Express();
//		// DTO转换成DO
//		express = TransUtil.trans2Do(expressDTO, express);
//		// 执行新增操作
//		express = getBaseDao().insert(express);
//		// DO转换成DTO
//		expressDTO = (ExpressDTO) TransUtil.trans2Dto(expressDTO, express);
//		return expressDTO;
//	}
//
//	@Override
//	public int updateExpress(ExpressDTO expressDTO) throws DeliveryException {
//		if (expressDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressDTO is null");
//		}
//		// 创建一个DO
//		BaseDo express = new Express();
//		// DTO转换成DO
//		express = TransUtil.trans2Do(expressDTO, express);
//		// 执行更新操作
//		int row = getBaseDao().update(express);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.UPDATE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public int deleteExpress(Integer id) throws DeliveryException {
//		if (id == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"id is null");
//		}
//		// 创建一个DO
//		Express express = new Express();
//		// DO赋值逻辑删除
//		express.setId(id);
//		express.setDeleted(1);
//		// 执行更新操作
//		int row = getBaseDao().update(express);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public List<ExpressDTO> queryExpress(ExpressQTO expressQTO)
//			throws DeliveryException {
//		if (expressQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressQTO is null");
//		}
//		List<BaseDo> expressList = query(expressQTO);
//
//		List<ExpressDTO> expressDTOList = new ArrayList<ExpressDTO>();
//		for (BaseDo express : expressList) {
//			// 创建一个DtO
//			ExpressDTO expressDTO = new ExpressDTO();
//			// DO转换成DTO
//			expressDTO = (ExpressDTO) TransUtil.trans2Dto(expressDTO, express);
//			expressDTOList.add(expressDTO);
//		}
//		return expressDTOList;
//	}
//
//	@Override
//	public Express getExpress(Integer expressId) throws DeliveryException {
//		if (expressId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"expressId is null");
//		}
//		Express express = new Express();
//		express.setId(expressId);
//		return (Express) getBaseDao().get(express);
//	}
//
//}
