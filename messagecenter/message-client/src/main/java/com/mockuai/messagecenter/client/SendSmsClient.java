package com.mockuai.messagecenter.client;

import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;

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
