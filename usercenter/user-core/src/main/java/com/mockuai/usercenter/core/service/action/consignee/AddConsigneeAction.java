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
import com.mockuai.usercenter.core.util.IdCardCheckUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddConsigneeAction extends TransAction {

    private static final int MAX_CONSIGNEE = 50;
    private static final int MIN_ADDRESS = 5;
    private static final int MAX_ADDRESS = 70;
    @Resource
    private UserConsigneeManager userConsigneeManager;
    @Resource
    private RegionManager regionManager;

    @Override
    public String getName() {
        return ActionEnum.ADD_CONSIGNEE.getActionName();
    }

    @Override
    protected UserResponse doTransaction(RequestContext context)
            throws UserException {

        UserRequest userRequest = context.getRequest();
        UserConsigneeDTO userConsigneeDto = (UserConsigneeDTO) userRequest
                .getParam("consigneeDTO");
        String bizCode = (String) context.get("bizCode");
        String appKey = (String) context.get("appKey");

        if (userConsigneeDto == null) {
            return new UserResponse(ResponseCode.P_PARAM_NULL, "userConsigneeDto is null");
        }

        if (!(userConsigneeDto.getAddress().length() >= 5 && userConsigneeDto.getAddress().length() <= 70)) {
            throw new UserException(ResponseCode.P_PARAM_INVALID, "address length invalid");
        }

        userConsigneeDto.setBizCode(bizCode);

        Long userId = userConsigneeDto.getUserId();
        // 查询当前用户的总的收货地址数量，如果大余50条，则不能插入
        Integer totalCount = userConsigneeManager.getConsigneeCountByUserId(userId);
        if (totalCount >= MAX_CONSIGNEE) {
            throw new UserException(ResponseCode.B_ADD_ERROR,
                    "user consignee can't greater than 50");
        }

        // 如果新增的地址为默认地址，则将该用户的默认地址改为非默认
        if (userConsigneeDto.getIsDefault() != null && userConsigneeDto.getIsDefault() == 1) {
            userConsigneeManager.updateUserDefaultConsignee(userId);
        }

        //TODO 行政区域CODE检查，townCode可以不填，其他CODE都必填，而且必须有效
        if (StringUtils.isBlank(userConsigneeDto.getCountryCode())
                || StringUtils.isBlank(userConsigneeDto.getProvinceCode())
                || StringUtils.isBlank(userConsigneeDto.getCityCode())) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "region code is null");
        }

        //校验身份证是否合法
        if (StringUtils.isNotBlank(userConsigneeDto.getIdCardNo())) {
            IdCardCheckUtil.IDCardValidate(userConsigneeDto.getIdCardNo());
        }

        //校验省市区代码
        List<String> regionCodes = new ArrayList<String>();
        regionCodes.add(userConsigneeDto.getCountryCode());
        regionCodes.add(userConsigneeDto.getProvinceCode());
        regionCodes.add(userConsigneeDto.getCityCode());
        regionCodes.add(userConsigneeDto.getAreaCode());
        if (StringUtils.isNotBlank(userConsigneeDto.getTownCode())) {
            regionCodes.add(userConsigneeDto.getTownCode());
        }

        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setRegionCodes(regionCodes);
        List<RegionDTO> regionDTOs = this.regionManager.queryRegion(regionQTO, appKey);
        if (regionDTOs.size() != regionCodes.size()) {
            throw new UserException(ResponseCode.B_SELECT_ERROR, "地址code有误");
        }

        if (userConsigneeDto.getAddress().length() > MAX_ADDRESS || userConsigneeDto.getAddress().length() < MIN_ADDRESS) {
            throw new UserException(ResponseCode.P_PARAM_INVALID, "详细地址字数长度有误");
        }

        if (userConsigneeDto.getConsignee().length() > 20) {
            throw new UserException(ResponseCode.P_PARAM_INVALID, "收货人字数长度有误");
        }

        // 添加收货地址
        userConsigneeDto = userConsigneeManager.addConsignee(userConsigneeDto);
        return new UserResponse(userConsigneeDto);
    }

}
