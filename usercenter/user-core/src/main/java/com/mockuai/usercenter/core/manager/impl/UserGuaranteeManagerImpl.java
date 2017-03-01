package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.dao.UserGuaranteeDAO;
import com.mockuai.usercenter.core.domain.UserGuaranteeDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserGuaranteeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
@Service
public class UserGuaranteeManagerImpl implements UserGuaranteeManager {
    private static final Logger log = LoggerFactory.getLogger(UserGuaranteeManagerImpl.class);
    @Resource
    private UserGuaranteeDAO userGuaranteeDAO;

    @Override
    public UserGuaranteeDTO addUserGuarantee(UserGuaranteeDTO userGuaranteeDTO) throws UserException {
        UserGuaranteeDO userGuaranteeDO = new UserGuaranteeDO();
        BeanUtils.copyProperties(userGuaranteeDTO, userGuaranteeDO);

        Long id = this.userGuaranteeDAO.addUserGuarantee(userGuaranteeDO);
        if (id == null) {
            log.error(userGuaranteeDO.toString());
            throw new UserException(ResponseCode.B_ADD_ERROR, "userGuaranteeDO insert error");
        }

        userGuaranteeDTO = this.getUserGuaranteeById(id);
        return userGuaranteeDTO;
    }

    @Override
    public UserGuaranteeDTO getUserGuaranteeById(Long id) throws UserException {
        UserGuaranteeDO userGuaranteeDO = this.userGuaranteeDAO.getUserGuaranteeById(id);
        UserGuaranteeDTO userGuaranteeDTO = new UserGuaranteeDTO();
        BeanUtils.copyProperties(userGuaranteeDO,userGuaranteeDTO);
        return userGuaranteeDTO;
    }

    @Override
    public Integer updateUserGuarantee(UserGuaranteeDTO userGuaranteeDTO) throws UserException {
        UserGuaranteeDO userGuaranteeDO = new UserGuaranteeDO();
        BeanUtils.copyProperties(userGuaranteeDTO, userGuaranteeDO);

        Integer result = this.userGuaranteeDAO.updateUserGuarantee(userGuaranteeDO);
        if (result != 1) {
            log.error("update error, " + userGuaranteeDO.toString());
            throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
        }
        return result;
    }

    @Override
    public List<UserGuaranteeDTO> queryUserGuarantee(UserGuaranteeQTO userGuaranteeQTO) {

        if (null == userGuaranteeQTO.getOffset() || userGuaranteeQTO.getOffset() < 0) {
            userGuaranteeQTO.setOffset(0L);
        }

        // 没传入每页显示总数或者每页显示的数量大于500的话，默认每页显示20条
        if (userGuaranteeQTO.getCount() == null || userGuaranteeQTO.getCount() > 500) {
            userGuaranteeQTO.setCount(20);
        }

        List<UserGuaranteeDO> userGuaranteeDOs = this.userGuaranteeDAO.queryUserGuarantee(userGuaranteeQTO);
        List<UserGuaranteeDTO> userGuaranteeDTOs = new ArrayList<UserGuaranteeDTO>();
        for (UserGuaranteeDO userGuaranteeDO : userGuaranteeDOs) {
            UserGuaranteeDTO userGuaranteeDTO = new UserGuaranteeDTO();
            BeanUtils.copyProperties(userGuaranteeDO, userGuaranteeDTO);
            userGuaranteeDTOs.add(userGuaranteeDTO);
        }
        return userGuaranteeDTOs;
    }

    @Override
    public Long totalUserGuarantee(UserGuaranteeQTO userGuaranteeQTO) {
//        UserGuaranteeDO userGuaranteeDO = new UserGuaranteeDO();
//        BeanUtils.copyProperties(userGuaranteeQTO, userGuaranteeDO);
        Long total = this.userGuaranteeDAO.totalUserGuarantee(userGuaranteeQTO);
        return total;
    }

    @Override
    public Integer deleteUserGuarantee(Long id) throws UserException {
        Integer result = this.userGuaranteeDAO.deleteUserGuarantee(id);
        if (result != 1) {
            log.error("delete error, id is {}",id);
            throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
        }
        return result;
    }
}
