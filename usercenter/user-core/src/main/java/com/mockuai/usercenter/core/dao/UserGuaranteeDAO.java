package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
import com.mockuai.usercenter.core.domain.UserGuaranteeDO;

import java.util.List;

/**
 * Created by yeliming on 16/1/23.
 */
public interface UserGuaranteeDAO {
    Long addUserGuarantee(UserGuaranteeDO userGuaranteeDO);

    UserGuaranteeDO getUserGuaranteeById(Long id);

    Integer updateUserGuarantee(UserGuaranteeDO userGuaranteeDO);

    List<UserGuaranteeDO> queryUserGuarantee(UserGuaranteeQTO userGuaranteeQTO);

    Long totalUserGuarantee(UserGuaranteeQTO userGuaranteeQTO);

    Integer deleteUserGuarantee(Long id);
}
