package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsConfigManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  新增提现配置
 * <p/>
 * Created by 冠生 on 5/18/15.
 */
@Service
public class AddWithdrawalsConfigAction extends TransAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddWithdrawalsConfigAction.class.getName());

    @Autowired
    private WithdrawalsConfigManager withdrawalsConfigManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {

        WithdrawalsConfigQTO withdrawalsConfigQTO = (WithdrawalsConfigQTO) context.getRequest().getParam("withdrawalsConfig");
        Long userId = (Long) context.getRequest().getParam("userId");

        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

        if(withdrawalsConfigQTO == null){
            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "withdrawalsConfigQTO is null");
        }

        if(withdrawalsConfigQTO.getWdConfigMinimum() < 0){
            return  new VirtualWealthResponse(ResponseCode.PARAMETER_NULL,"wdconfigmininum should be  great than zero");
        }
        if(withdrawalsConfigQTO.getWdConfigMaximum() < withdrawalsConfigQTO.getWdConfigMinimum()){
            return  new VirtualWealthResponse(ResponseCode.PARAMETER_NULL,"wdconfigmaxnum should be  great than wdconfigmininum");
        }



        try {
            withdrawalsConfigManager.saveWithdrawalsConfig(withdrawalsConfigQTO);
        }catch (VirtualWealthException e){
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());
            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }

        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_WITHDRAWALS_CONFIG.getActionName();
    }
}