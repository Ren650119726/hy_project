package com.mockuai.usercenter.core.service.action.consignee;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.Request;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserConsigneeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.service.action.useropeninfo.QueryUserOpenInfoAction;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yeliming on 16/7/11.
 */
@Service
public class QueryAllConsigneeAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserOpenInfoAction.class);

    @Resource
    private UserConsigneeManager userConsigneeManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {
        Request request = context.getRequest();
        UserConsigneeQTO userConsigneeQTO = (UserConsigneeQTO) request.getParam("userConsigneeQTO");
        String bizCode = (String)context.get("bizCode");

        if(userConsigneeQTO == null){
            log.error("userQTO is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"userQTO is null");
        }
        if(StringUtils.isBlank(bizCode)){
            log.error("bizCode is null");
            throw new UserException(ResponseCode.P_PARAM_NULL,"bizCode is null");
        }
        userConsigneeQTO.setBizCode(bizCode);
        List<UserConsigneeDTO> userConsigneeDTOList = this.userConsigneeManager.queryAllConsignee(userConsigneeQTO);
        Long totalCount = this.userConsigneeManager.totalAllCount(userConsigneeQTO);

        return new UserResponse(userConsigneeDTOList,totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_ALL_CONSIGNEE.getActionName();
    }
}
