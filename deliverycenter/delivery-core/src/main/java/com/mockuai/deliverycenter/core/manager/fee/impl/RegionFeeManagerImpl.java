//package com.mockuai.deliverycenter.core.manager.fee.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
//import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
//import com.mockuai.deliverycenter.core.domain.BaseDo;
//import com.mockuai.deliverycenter.core.domain.fee.Region;
//import com.mockuai.deliverycenter.core.domain.fee.RegionFee;
//import com.mockuai.deliverycenter.core.domain.fee.StdFee;
//import com.mockuai.deliverycenter.core.exception.DeliveryException;
//import com.mockuai.deliverycenter.core.manager.BaseManager;
//import com.mockuai.deliverycenter.core.manager.fee.RegionFeeManager;
//import com.mockuai.deliverycenter.core.manager.fee.RegionManager;
//import com.mockuai.deliverycenter.core.manager.fee.StdFeeManager;
//import com.mockuai.deliverycenter.core.util.TransUtil;
//
//@Service
//public class RegionFeeManagerImpl extends BaseManager implements
//		RegionFeeManager {
//	@Resource
//	RegionManager regionManager;
//	@Resource
//	StdFeeManager stdFeeManager;
//
//	@Override
//	public List<RegionFeeDTO> addRegionFee(List<RegionFeeDTO> regionFeeDTOList)
//			throws DeliveryException {
//		if (regionFeeDTOList == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"regionFeeDTOList is null");
//		}
//		List<RegionFeeDTO> resultList = new ArrayList();
//		for (RegionFeeDTO regionFeeDTO : regionFeeDTOList) {
//			// 校验地区是否存在
//			Region region = regionManager.getRegion(regionFeeDTO.getRegionId());
//			if (region == null) {
//				throw new DeliveryException(
//						RetCodeEnum.PARAMETER_ERROR.getCode(),
//						"region_id not exist");
//			}
//			// 校验运费标准是否存在
//			StdFee stdFee = stdFeeManager.getStdFee(regionFeeDTO.getFeeId());
//			if (stdFee == null) {
//				throw new DeliveryException(
//						RetCodeEnum.PARAMETER_ERROR.getCode(),
//						"fee_id not exist");
//			}
//			// 创建一个DO
//			BaseDo regionFee = new RegionFee();
//			// DTO转换成DO
//			regionFee = TransUtil.trans2Do(regionFeeDTO, regionFee);
//			// 执行新增操作
//			regionFee = getBaseDao().insert(regionFee);
//			// DO转换成DTO
//			regionFeeDTO = (RegionFeeDTO) TransUtil.trans2Dto(regionFeeDTO,
//					regionFee);
//			resultList.add(regionFeeDTO);
//		}
//		return regionFeeDTOList;
//	}
//
//	@Override
//	public int deleteRegionFee(List<Integer> regionFeeIdList)
//			throws DeliveryException {
//		if (regionFeeIdList == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"regionFeeIdList is null");
//		}
//		int deleteCount = 0;
//		for (Integer propertyId : regionFeeIdList) {
//			// 创建一个DO
//			RegionFee regionFee = new RegionFee();
//			// DO赋值逻辑删除
//			regionFee.setId(propertyId);
//			regionFee.setDeleted(1);
//			// 执行更新操作
//			int row = getBaseDao().update(regionFee);
//			deleteCount += row;
//		}
//		if (deleteCount != regionFeeIdList.size()) {
//			throw new DeliveryException(RetCodeEnum.DELETE_ERROR);
//		}
//		return deleteCount;
//	}
//
//	@Override
//	public List<RegionFeeDTO> queryByFeeId(Integer feeId)
//			throws DeliveryException {
//		if (feeId == null) {
//			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
//					"feeId is null");
//		}
//		List<RegionFee> regionFeeList = getBaseDao().queryForList(
//				"REGIONFEE.queryByFeeId", feeId);
//
//		List<RegionFeeDTO> regionFeeDTOList = new ArrayList<RegionFeeDTO>();
//		for (RegionFee regionFee : regionFeeList) {
//			// 创建一个DtO
//			RegionFeeDTO regionFeeDTO = new RegionFeeDTO();
//			// DO转换成DTO
//			regionFeeDTO = (RegionFeeDTO) TransUtil.trans2Dto(regionFeeDTO,
//					regionFee);
//			regionFeeDTOList.add(regionFeeDTO);
//		}
//		return regionFeeDTOList;
//	}
//
//}
