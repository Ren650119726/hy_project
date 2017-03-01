package com.mockuai.usercenter.core.service.action.user;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.dto.UserAccountDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.TransAction;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by duke on 15/8/13.
 */
@Service
public class QueryUserByDeviceAction extends TransAction {
    private final static Logger log = LoggerFactory.getLogger(QueryUserByDeviceAction.class);

    @Resource
    private UserManager userManager;

    @Override
    public UserResponse doTransaction(RequestContext context) throws UserException {
        UserRequest request = context.getRequest();
        UserQTO userQTO= (UserQTO)request.getParam("userQTO");
        Integer appType = (Integer)request.getParam("appType");
        String bizCode = (String)context.get("bizCode");

        if (userQTO == null) {
            log.error("user info qto is null when query user by userQTO");
        }

        Map<String, Long> accountMap = new HashMap<String, Long>();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        userQTO.setSourceType(appType);
        userQTO.setBizCode(bizCode);
        List<UserDTO> userDTOs = userManager.queryUserByDevice(userQTO);
        for (UserDTO userDTO : userDTOs) {
            if (userDTO.getSourceType().intValue() == appType.intValue()) {
                String key = formater.format(userDTO.getGmtCreated());
                Long count = accountMap.get(key);
                if (count != null) {
                    count += 1;
                    accountMap.put(key, count);
                } else {
                    accountMap.put(key, 1L);
                }
            }
        }

        List<UserAccountDTO> userAccountDTOs = new ArrayList<UserAccountDTO>();
        for (String key : accountMap.keySet()) {
            UserAccountDTO dto = new UserAccountDTO();
            dto.setAppType(appType);
            dto.setNum(accountMap.get(key));
            try {
                dto.setResultDate(formater.parse(key));
            } catch (Exception err) {
                log.error("parse error with date : " + key);
            }
            userAccountDTOs.add(dto);
        }

        return new UserResponse(userAccountDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_BY_DEVICE.getActionName();
    }
}