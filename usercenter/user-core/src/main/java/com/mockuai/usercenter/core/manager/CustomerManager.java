package com.mockuai.usercenter.core.manager;

import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.usercenter.core.exception.UserException;

/**
 * Created by duke on 15/12/17.
 */
public interface CustomerManager {
    /**
     * 获得客户信息
     * @param userId
     * @param appKey
     * */
    MemberDTO getMemberByUserId(Long userId, String appKey) throws UserException;
}
