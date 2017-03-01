package com.mockuai.messagecenter.client;

import com.mockuai.messagecenter.common.api.Response;
import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.common.qto.VerifySmsQTO;

public interface SmsServiceClient {

    /**
     * 发送手机验证码服务
     * @param verifySmsQTO
     * @param appKey
     * @return
     */
    Response<String> smsMobileVerify(VerifySmsQTO verifySmsQTO, String appKey);

    /**
     * 获取本地缓存的验证码，测试用
     * @param mobile
     * @param appKey
     * @return
     */
    Response<SendSmsDTO> getMobileVerifyCode(String mobile, String appKey);

    /**
     * 删除本地缓存的验证码，测试用
     * @param mobile
     * @param appKey
     * @return
     */
    Response<Boolean> deleteMobileVerifyCode(String mobile, String appKey);
}
