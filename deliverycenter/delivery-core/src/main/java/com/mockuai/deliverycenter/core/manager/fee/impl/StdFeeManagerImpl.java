//package com.mockuai.deliverycenter.core.manager.fee.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
//import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.fee.StdFee;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class StdFeeManagerImpl extends BaseManager implements StdFeeManager {
//
//	@Override
//	public StdFeeDTO addStdFee(StdFeeDTO stdFeeDTO) throws DeliveryException {
//		if (stdFeeDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"stdFeeDTO is null");
//		}
//		// 如果开启了限价，校验价格上下限
//		if (stdFeeDTO.getFeeLimitMark() != null
//				&& stdFeeDTO.getFeeLimitMark() == 1) {
//			if (stdFeeDTO.getMinFee() != null && stdFeeDTO.getMaxFee() != null
//					&& stdFeeDTO.getMinFee() > stdFeeDTO.getMaxFee()) {
//				throw new DeliveryException(
//						RetCodeEnum.PARAMETER_ERROR.getCode(),
//						"minfee can't greater than maxfee");
//			}
//
//		}
//		// 创建一个DO
//		BaseDo stdFee = new StdFee();
//		// DTO转换成DO
//		stdFee = TransUtil.trans2Do(stdFeeDTO, stdFee);
//		// 执行新增操作
//		stdFee = getBaseDao().insert(stdFee);
//		// DO转换成DTO
//		stdFeeDTO = (StdFeeDTO) TransUtil.trans2Dto(stdFeeDTO, stdFee);
//		return stdFeeDTO;
//	}
//
//	@Override
//	public int updateStdFee(StdFeeDTO stdFeeDTO) throws DeliveryException {
//		if (stdFeeDTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"stdFeeDTO is null");
//		}
//		// 创建一个DO
//		BaseDo stdFee = new StdFee();
//		// DTO转换成DO
//		stdFee = TransUtil.trans2Do(stdFeeDTO, stdFee);
//		// 执行更新操作
//		int row = getBaseDao().update(stdFee);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.UPDATE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public int deleteStdFee(Integer id) throws DeliveryException {
//		if (id == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"id is null");
//		}
//		// 创建一个DO
//		StdFee stdFee = new StdFee();
//		// DO赋值逻辑删除
//		stdFee.setId(id);
//		stdFee.setDeleted(1);
//		// 执行更新操作
//		int row = getBaseDao().update(stdFee);
//		if (row != 1) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return row;
//	}
//
//	@Override
//	public List queryStdFee(StdFeeQTO stdFeeQTO) throws DeliveryException {
//		if (stdFeeQTO == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"stdFeeQTO is null");
//		}
//		List<BaseDo> stdFeeList = query(stdFeeQTO);
//
//		List<StdFeeDTO> stdFeeDTOList = new ArrayList<StdFeeDTO>();
//		for (BaseDo stdFee : stdFeeList) {
//			// 创建一个DtO
//			StdFeeDTO stdFeeDTO = new StdFeeDTO();
//			// DO转换成DTO
//			stdFeeDTO = (StdFeeDTO) TransUtil.trans2Dto(stdFeeDTO, stdFee);
//			stdFeeDTOList.add(stdFeeDTO);
//		}
//		return stdFeeDTOList;
//	}
//
//	@Override
//	public StdFee getStdFee(Integer feeId) throws DeliveryException {
//		if (feeId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"feeId is null");
//		}
//		StdFee stdFee = new StdFee();
//		stdFee.setId(feeId);
//		return (StdFee) getBaseDao().get(stdFee);
//	}
//
//}
