package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.usercenter.common.dto.UserDTO;

/**
 * Created by yindingyu on 15/11/25.
 */
public interface UserManager {

    public UserDTO getUserById(Long id,String appKey) throws ItemException;


    Boolean isHiKe(Long id, String appKey) throws ItemException;
}
