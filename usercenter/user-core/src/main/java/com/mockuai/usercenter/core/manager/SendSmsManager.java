package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.common.dto.SendSmsDTO;

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
