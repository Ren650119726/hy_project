package com.mockuai.rainbowcenter.core.service.action.duiba;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.CreditAutoLoginDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.DuiBaManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


/**
 * Created by lizg on 2016/9/19.
 */

@Controller
public class CreditAutoLoginAction implements Action {

    @Resource
    private DuiBaManager duiBaManager;

    private static final Logger log = LoggerFactory.getLogger(CreditAutoLoginAction.class);


    @Override
    public RainbowResponse execute(RequestContext context) {
        Request request = context.getRequest();
        String uid = (String) request.getParam("uid");
        String credits = (String) request.getParam("credits");
        String loginUrl;

        try {
            loginUrl = duiBaManager.creditAutoLogin(uid,credits);

        } catch (RainbowException e) {
            return new RainbowResponse(e.getResponseCode(), e.getMessage());
        }

        CreditAutoLoginDTO creditAutoLoginDTO = new CreditAutoLoginDTO();
        creditAutoLoginDTO.setLoginUrl(loginUrl);

        return new RainbowResponse(creditAutoLoginDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.DUIBA_CREDIT_AUTO_LOGIN.getActionName();
    }
}
