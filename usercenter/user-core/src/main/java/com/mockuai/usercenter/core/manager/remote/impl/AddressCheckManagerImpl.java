package com.mockuai.usercenter.core.manager.remote.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.remote.AddressCheckManager;

public class AddressCheckManagerImpl implements AddressCheckManager {

	@Override
	public ResponseCode addressCheck(UserConsigneeDTO userConsigneeDto)
			throws UserException {

		/*if (consigneeDto == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"consigneeDto is null");
		}

		// 国家的验证
		if (consigneeDto.getCountry() == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user consignee country is null");
		}

		Response<RegionDTO> response = feeClient.getRegion(consigneeDto
				.getCountry());

		// 得到的response的module不能为空
		if (response.getModule() == null) {

			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee country is not exist");
		}

		// 获取國家的id，用来验证省份的归属
		int countryId = response.getModule().getId();

		// 得到的response中的module的等级必须为国家等级
		if (response.getModule().getRegionLevel().intValue() != RegionLevel.COUNTRY
				.getValue().intValue()) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee country level error");
		}

		// 省份的验证
		if (consigneeDto.getProvince() == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user consignee province is null");
		}

		response = feeClient.getRegion(consigneeDto.getProvince());

		// 得到的response的module不能为空
		if (response.getModule() == null) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee province is not exist");
		}

		// 获取省份的id，用来验证城市的归属
		int provinceId = response.getModule().getId();

		if (response.getModule().getRegionLevel().intValue() != RegionLevel.PROVINCE
				.getValue().intValue()) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee province level error");
		}

		// 省份必须和国家的对应
		if (response.getModule().getParentId() != countryId) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"country and province not correspond");
		}

		// 城市的验证
		if (consigneeDto.getCity() == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user consignee city is null");
		}

		response = feeClient.getRegion(consigneeDto.getCity());

		// 得到的response的module不能为空
		if (response.getModule() == null) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee city is not exist");
		}

		// 获取城市的id，用来验证区县的归属
		int cityId = response.getModule().getId();

		if (response.getModule().getRegionLevel().intValue() != RegionLevel.CITY
				.getValue().intValue()) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee city level error");
		}

		// 城市必须和省份对应
		if (response.getModule().getParentId() != provinceId) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"province and city not correspond");
		}

		// 区/县的验证
		if (consigneeDto.getArea() == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user consignee area is null");
		}

		response = feeClient.getRegion(consigneeDto.getArea());

		// 得到的response的module不能为空
		if (response.getModule() == null) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee area is not exist");
		}

		// 获取区县的id，用来判断乡镇的归属
		int areaId = response.getModule().getId();

		if (response.getModule().getRegionLevel().intValue() != RegionLevel.AREA
				.getValue().intValue()) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"user consignee area level error");
		}

		// 区县必须和城市对应
		if (response.getModule().getParentId() != cityId) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"city and area not correspond");
		}

		// 乡镇的验证
		if (consigneeDto.getTown() != null) {
			response = feeClient.getRegion(consigneeDto.getTown());

			// 得到的response的module不能为空
			if (response.getModule() == null) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"user consignee town is not exist");
			}

			if (response.getModule().getRegionLevel().intValue() != RegionLevel.TOWN
					.getValue().intValue()) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"user consignee town level error");
			}

			// 乡镇必须和区县对应
			if (response.getModule().getParentId() != areaId) {
				throw new UserException(ResponseCode.P_PARAM_ERROR,
						"area and town not correspond");
			}

		}*/

		return null;
	}
}
