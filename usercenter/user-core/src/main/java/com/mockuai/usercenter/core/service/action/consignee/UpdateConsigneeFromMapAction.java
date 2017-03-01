package com.mockuai.usercenter.core.service.action.consignee;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.RegionManager;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/11/10.
 */
@Service
public class UpdateConsigneeFromMapAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(UpdateConsigneeFromMapAction.class);

    private static final String [] DIRECT_CITYS = {"北京", "重庆", "上海", "天津"};

    @Resource
    private UserConsigneeManager userConsigneeManager;

    @Resource
    private RegionManager regionManager;

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());
        UserRequest userRequest = context.getRequest();
        UserConsigneeDTO userConsigneeDTO = (UserConsigneeDTO) userRequest.getParam("consigneeDTO");
        String appKey = (String) userRequest.getParam("appKey");

        if (null == userConsigneeDTO) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "consigneeDTO is null");
        }

        // 通过地址名称获得对应的区域code
        String province = userConsigneeDTO.getProvince();
        RegionDTO regionDTO = getProvinceInfo(province, appKey);
        if (regionDTO != null) {
            userConsigneeDTO.setCountryCode(regionDTO.getParentCode());
            userConsigneeDTO.setProvinceCode(regionDTO.getCode());
        } else {
            userConsigneeDTO.setProvinceCode("0");
        }

        String city = userConsigneeDTO.getCity();
        String parentCode = null;
        if (regionDTO != null) {
            parentCode = regionDTO.getCode();
        }
        regionDTO = getCityInfo(city, parentCode, appKey);
        if (regionDTO != null) {
            userConsigneeDTO.setCityCode(regionDTO.getCode());
        } else {
            userConsigneeDTO.setCityCode("0");
        }

        String area = userConsigneeDTO.getArea();
        parentCode = null;
        if (regionDTO != null) {
            parentCode = regionDTO.getCode();
        }
        regionDTO = getAreaInfo(area, parentCode, appKey);
        if (regionDTO != null) {
            userConsigneeDTO.setAreaCode(regionDTO.getCode());
        } else {
            userConsigneeDTO.setAreaCode("0");
        }

        // 如果新增的地址为默认地址，则将该用户的所有地址改为非默认
        if (userConsigneeDTO.getIsDefault() != null && userConsigneeDTO.getIsDefault() == 1) {
            userConsigneeManager.updateUserDefaultConsignee(userConsigneeDTO.getUserId());
        }

        // 修改用户的收货地址
        userConsigneeManager.updateConsignee(userConsigneeDTO);
        return new UserResponse(true);
    }

    /**
     * 判断是不是直辖市
     * */
    private boolean isDirectCitys(String name) {
        for (String city : DIRECT_CITYS) {
            if (name.startsWith(city)) {
                return true;
            }
        }
        return false;
    }

    public RegionDTO getProvinceInfo(String province, String appKey) throws UserException {
        if (province == null) {
            return null;
        }

        if (province.endsWith("省")) {
            province = province.substring(0, province.indexOf("省"));
        }
        else if (isDirectCitys(province)) {
            if (province.contains("市")) {
                province = province.substring(0, province.indexOf("市"));
            }
        }
        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setName(province);
        List<RegionDTO> regionDTOs = regionManager.queryRegion(regionQTO, appKey);
        if (!regionDTOs.isEmpty()) {
            return regionDTOs.get(0);
        } else {
            log.error("province code not exists with name: {}", province);
            return null;
        }
    }

    public RegionDTO getCityInfo(String city, String parentCode, String appKey) throws UserException {
        if (city == null) {
            return null;
        }

        if (!city.endsWith("市")) {
            city += "市";
        }

        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setParentCode(parentCode);
        regionQTO.setName(city);
        List<RegionDTO> regionDTOs = regionManager.queryRegion(regionQTO, appKey);
        if (!regionDTOs.isEmpty()) {
            return regionDTOs.get(0);
        } else {
            log.error("city code not exists with name: {}", city);
            return null;
        }
    }

    public RegionDTO getAreaInfo(String area, String parentCode, String appKey) throws UserException {
        if (area == null) {
            return null;
        }

        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setName(area);
        regionQTO.setParentCode(parentCode);
        List<RegionDTO> regionDTOs = regionManager.queryRegion(regionQTO, appKey);
        if (!regionDTOs.isEmpty()) {
            return regionDTOs.get(0);
        } else {
            log.error("area code not exists with name: {}", area);
            return null;
        }
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_CONSIGNEE_BY_MAP.getActionName();
    }
}
