package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
public interface UserGuaranteeManager {
    UserGuaranteeDTO addUserGuarantee(UserGuaranteeDTO userGuaranteeDTO) throws UserException;

    UserGuaranteeDTO getUserGuaranteeById(Long id) throws UserException;

    Integer updateUserGuarantee(UserGuaranteeDTO userGuaranteeDTO) throws UserException;

    List<UserGuaranteeDTO> queryUserGuarantee(UserGuaranteeQTO userGuaranteeQTO);

    Long totalUserGuarantee(UserGuaranteeQTO userGuaranteeQTO);

    Integer deleteUserGuarantee(Long id) throws UserException;
}
