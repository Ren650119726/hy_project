package com.mockuai.usercenter.core.service.action.consignee;

import javax.annotation.Resource;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.manager.DeliveryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;

import java.util.*;

@Service
public class GetConsigneeByIdAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(GetConsigneeByIdAction.class);

	@Resource
	private UserConsigneeManager userConsigneeManager;
	@Resource
	private DeliveryManager deliveryManager;

	@Override
	public UserResponse execute(RequestContext context) throws UserException {
		UserRequest userRequest = context.getRequest();
		Long userId = (Long) userRequest.getParam("userId");
		String appKey = (String) userRequest.getParam("appKey");
		Long consigneeId = (Long) userRequest.getParam("consigneeId");

		UserConsigneeDTO userConsigneeDTO = userConsigneeManager.getConsigneeById(userId, consigneeId);
		if (userConsigneeDTO == null) {
			log.error("userConsigneeDTO is null, userId: {}, consigneeId: {}");
			throw new UserException(ResponseCode.P_PARAM_NULL);
		}
		//查询区域信息，并填充收货地址中的区域名称
		Set<String> regionCodes = new HashSet<String>();
		regionCodes.add(userConsigneeDTO.getCountryCode());
		regionCodes.add(userConsigneeDTO.getProvinceCode());
		regionCodes.add(userConsigneeDTO.getCityCode());
		regionCodes.add(userConsigneeDTO.getAreaCode());
		regionCodes.add(userConsigneeDTO.getTownCode());
		List<RegionDTO> regionDTOs = deliveryManager.queryRegion(new ArrayList<String>(regionCodes), appKey);

		Map<String,RegionDTO> regionDTOMap = new HashMap<String, RegionDTO>();
		for(RegionDTO regionDTO: regionDTOs){
			regionDTOMap.put(regionDTO.getCode(), regionDTO);
		}

		if(regionDTOMap.containsKey(userConsigneeDTO.getCountryCode())){
			userConsigneeDTO.setCountry(regionDTOMap.get(userConsigneeDTO.getCountryCode()).getName());
		}

		if(regionDTOMap.containsKey(userConsigneeDTO.getProvinceCode())){
			userConsigneeDTO.setProvince(regionDTOMap.get(userConsigneeDTO.getProvinceCode()).getName());
		}

		if(regionDTOMap.containsKey(userConsigneeDTO.getCityCode())){
			userConsigneeDTO.setCity(regionDTOMap.get(userConsigneeDTO.getCityCode()).getName());
		}

		if(regionDTOMap.containsKey(userConsigneeDTO.getAreaCode())){
			userConsigneeDTO.setArea(regionDTOMap.get(userConsigneeDTO.getAreaCode()).getName());
		}

		if(regionDTOMap.containsKey(userConsigneeDTO.getTownCode())){
			userConsigneeDTO.setTown(regionDTOMap.get(userConsigneeDTO.getTownCode()).getName());
		}
		return new UserResponse(userConsigneeDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_CONSIGNEE_BY_ID.getActionName();
	}

}
