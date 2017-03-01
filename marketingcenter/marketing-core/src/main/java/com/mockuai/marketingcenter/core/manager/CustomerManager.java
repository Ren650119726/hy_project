package com.mockuai.marketingcenter.core.manager;

import com.mockuai.customer.common.dto.MemberDTO;
import com.mockuai.marketingcenter.core.exception.MarketingException;

/**
 * Created by edgar.zr on 3/04/2016.
 */
public interface CustomerManager {

    /**
     * 获取会员信息
     *
     * @param userId
     * @param appKey
     * @return
     * @throws MarketingException
     */
    MemberDTO getMemberByUserId(Long userId, String appKey) throws MarketingException;
}