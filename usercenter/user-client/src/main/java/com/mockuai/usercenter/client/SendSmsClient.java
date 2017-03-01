package com.mockuai.usercenter.client;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.SendSmsDTO;

/**
 * Created by duke on 15/8/18.
 */
public interface SendSmsClient {
    /**
     * 发送手机验证码给用户
     *
     * @param mobile
     * @param appKey
     * @return
     * */
    public Response<SendSmsDTO> sendSmsToMobile(String mobile, String appKey);
}
