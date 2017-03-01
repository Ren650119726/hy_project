package com.mockuai.messagecenter.core.manager.impl;

import com.mockuai.messagecenter.common.dto.SendSmsDTO;
import com.mockuai.messagecenter.core.manager.SendSmsManager;
import com.mockuai.messagecenter.core.util.HttpUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/8/18.
 */
@Service
public class SendSmsManagerImpl implements SendSmsManager {
    private String msgUrl = "http://b.taojae.com/sendsms.php";

    @Override
    public SendSmsDTO sendSms(SendSmsDTO sendSmsDTO) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("mobile", sendSmsDTO.getMobile()));
        long verifyCode = System.currentTimeMillis() % 1000000;
        String verifyCodeStr = String.format("%06d", verifyCode);
        String content = "您好，您的验证码是 " + verifyCodeStr + " ,请不要泄漏！【魔筷科技】";
        params.add(new BasicNameValuePair("content", content));
        SendSmsDTO dto = new SendSmsDTO();
        dto.setVerifyCode(verifyCodeStr);
        HttpUtil.post(msgUrl, params);
        return dto;
    }
}
