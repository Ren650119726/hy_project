package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.usercenter.common.dto.UserDTO;

import java.util.List;

/**
 * Created by linyue on 16/10/11.
 */
public interface DistributeManager {
    
	/**
     * 根据用户id获取用户的统一嗨客协议记录
     */
    Boolean getUserAgreeProcotolRecord(Long userId, String appKey) throws VirtualWealthException;


}
