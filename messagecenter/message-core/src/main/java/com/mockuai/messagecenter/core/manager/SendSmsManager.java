package com.mockuai.messagecenter.core.manager;

import com.mockuai.messagecenter.common.dto.SendSmsDTO;

/**
 * Created by duke on 15/8/18.
 */
public interface SendSmsManager {
    /**
     * 发送手机验证码给用户
     *
     * @param sendSmsDTO
     * @return
     * */
    public SendSmsDTO sendSms(SendSmsDTO sendSmsDTO);
}
