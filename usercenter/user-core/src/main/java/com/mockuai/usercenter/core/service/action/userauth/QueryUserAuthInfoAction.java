package com.mockuai.usercenter.core.service.action.userauth;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.common.api.UserResponse;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.dto.EnterpriseAuthExtendDTO;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.manager.UserGuaranteeManager;
import com.mockuai.usercenter.core.service.RequestContext;
import com.mockuai.usercenter.core.service.UserRequest;
import com.mockuai.usercenter.core.service.action.Action;
import com.mockuai.usercenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/7/15.
 */
@Service
public class QueryUserAuthInfoAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryUserAuthInfoAction.class);

    private final Integer DEFAULT_PAGE_SIZE =500;
    private final Long DEFAULT_PAGE_NUM = 1L;

    @Resource
    private UserAuthInfoManager userAuthInfoManager;

    @Resource
    private EnterpriseAuthExtendManager enterpriseAuthExtendManager;

    @Resource
    private UserGuaranteeManager userGuaranteeManager;

    @Override
    public UserResponse execute(RequestContext context) throws UserException {

        UserRequest userRequest = context.getRequest();
        UserAuthInfoQTO userAuthInfoQTO = (UserAuthInfoQTO) userRequest.getParam("userAuthInfoQTO");
        String bizCode = (String)context.get("bizCode");

        //入参校验
        try {
            if (userAuthInfoQTO.getPageSize() == null || userAuthInfoQTO.getPageSize().intValue() > DEFAULT_PAGE_SIZE) {
                userAuthInfoQTO.setPageSize(DEFAULT_PAGE_SIZE);
            }
            if (userAuthInfoQTO.getPageNum() == null) {
                userAuthInfoQTO.setPageNum(DEFAULT_PAGE_NUM);
            }
            userAuthInfoQTO.setBizCode(bizCode);

            // 分页查询
            userAuthInfoQTO.setOffset((userAuthInfoQTO.getPageNum().longValue() - 1) * userAuthInfoQTO.getPageSize().intValue());
            userAuthInfoQTO.setCount(userAuthInfoQTO.getPageSize().intValue());
            List<UserAuthInfoDO> authInfoDOs = userAuthInfoManager.queryAuthInfo(userAuthInfoQTO);

            List<UserAuthInfoDTO> authInfoDTOs = new ArrayList<UserAuthInfoDTO>();// = ModelUtil.convertToUserAuthInfoDTOList(authInfoDOs);
            for (UserAuthInfoDO authInfoDO : authInfoDOs) {
                UserAuthInfoDTO userAuthInfoDTO = new UserAuthInfoDTO();
                BeanUtils.copyProperties(authInfoDO,userAuthInfoDTO);

                UserGuaranteeQTO userGuaranteeQTO = new UserGuaranteeQTO();
                userGuaranteeQTO.setUserId(authInfoDO.getUserId());
                userGuaranteeQTO.setBizCode(bizCode);
                List<UserGuaranteeDTO> userGuaranteeDTOs = userGuaranteeManager.queryUserGuarantee(userGuaranteeQTO);
                if(userGuaranteeDTOs == null || userGuaranteeDTOs.size()>1){
                    log.error("query user guarantee error");
                    throw new UserException(ResponseCode.B_SELECT_ERROR,"query user guarantee error");
                }
                if(userGuaranteeDTOs.size() == 1){
                    userAuthInfoDTO.setGuaranteeAmount(userGuaranteeDTOs.get(0).getGuaranteeAmount());
                }
                authInfoDTOs.add(userAuthInfoDTO);
            }

            // 通过用户的认证信息获得相应的企业认证扩展信息
            for (UserAuthInfoDTO authInfoDTO : authInfoDTOs) {
                EnterpriseAuthExtendDO extendDO = enterpriseAuthExtendManager.getEnterpriseAuthExtend(authInfoDTO.getUserId());
                if (extendDO != null) {
                    authInfoDTO.setEnterpriseAuthExtendDTO(ModelUtil.convertToEnterpriseAuthExtendDTO(extendDO));
                }
            }
            Long count = userAuthInfoManager.getTotalCount(userAuthInfoQTO);
            return new UserResponse(authInfoDTOs, count);
        } catch (UserException e){
            return new UserResponse(e.getResponseCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_USER_AUTH_INFO.getActionName();
    }

}
