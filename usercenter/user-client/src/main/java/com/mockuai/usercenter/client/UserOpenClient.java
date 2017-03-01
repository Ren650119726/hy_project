package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserOpenInfoQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/13.
 */
public interface UserOpenClient {
    Response<List<UserOpenInfoDTO>> queryUserOpenInfo(UserOpenInfoQTO userOpenInfoQTO, String appKey);
}
