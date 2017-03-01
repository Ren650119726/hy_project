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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.plaf.synth.Region;
import java.util.List;

/**
 * Created by duke on 15/11/6.
 */
@Service
public class AddConsigneeFromMapAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(AddConsigneeFromMapAction.class);
    private static final int MAX_CONSIGNEE = 20;

    private static final String [] DIRECT_CITYS = {"北京", "重庆", "上海", "天津"};

    @Resource
    private UserConsigneeManager userConsigneeManager;

    @Resource
    private RegionManager regionManager;

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

    @Override
    protected UserResponse doTransaction(RequestContext context) throws UserException {
        log.info("Enter Action [{}]", getName());
        UserRequest request = context.getRequest();
        UserConsigneeDTO userConsigneeDTO = (UserConsigneeDTO) request.getParam("consigneeDTO");
        String bizCode = (String) context.get("bizCode");
        String appKey = (String) context.get("appKey");
        if (userConsigneeDTO == null) {
            log.error("consigneeDTO is null, bizCode: {}", bizCode);
            return new UserResponse(ResponseCode.P_PARAM_NULL, "consigneeDTO is null");
        }

        userConsigneeDTO.setBizCode(bizCode);

        Long userId = userConsigneeDTO.getUserId();
        Integer totalCount = userConsigneeManager.getConsigneeCountByUserId(userId);

        if (totalCount >= MAX_CONSIGNEE) {
            throw new UserException(ResponseCode.B_ADD_ERROR, "user consignee can't greater than 20");
        }

        // 如果新增的地址为默认地址，则将该用户的默认地址改为非默认
        if (userConsigneeDTO.getIsDefault() != null && userConsigneeDTO.getIsDefault() == 1) {
            userConsigneeManager.updateUserDefaultConsignee(userId);
        }

        if (StringUtils.isBlank(userConsigneeDTO.getProvince())
                || StringUtils.isBlank(userConsigneeDTO.getCity())
                || StringUtils.isBlank(userConsigneeDTO.getLatitude())
                || StringUtils.isBlank(userConsigneeDTO.getLongitude())) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "区域相关信息不能为空");
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

        userConsigneeDTO = userConsigneeManager.addConsignee(userConsigneeDTO);

        log.info("Exit Action [{}]", getName());
        return new UserResponse(userConsigneeDTO);
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
        return ActionEnum.ADD_CONSIGNEE_BY_MAP.getActionName();
    }
}
