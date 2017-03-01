package com.mockuai.virtualwealthcenter.core.service.action;

import com.google.common.collect.Lists;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemLogManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 更新提现记录
 * <p/>
 * Created by 冠生 on 5/18/15.
 */
@Service
public class UpdateWithdrawalsItemAction extends TransAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateWithdrawalsItemAction.class.getName());

    @Autowired
    private WithdrawalsItemManager withdrawalsItemManager;
    @Autowired
    private WithdrawalsItemLogManager withdrawalsItemLogManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        WithdrawalsItemQTO withdrawalsItemQTO = (WithdrawalsItemQTO) context.getRequest().getParam("withdrawalsItem");
        List<WithdrawalsItemDTO> data =   withdrawalsItemManager.findList(withdrawalsItemQTO);
        if(data.isEmpty()){
            return  new VirtualWealthResponse(ResponseCode.PARAMETER_NULL,"can not found withdrawalsNumber ");
        }

        List<Integer> invalidStatusList = Lists.newArrayList(2,3,5);
        WithdrawalsItemDTO withdrawalsItemDTO = data.get(0);
        int status = withdrawalsItemDTO.getWithdrawalsStatus();
        //同意后可以打款。 不可以拒绝
        switch (withdrawalsItemQTO.getDotype()){
            //同意
            case 1:
                if(  invalidStatusList.contains(withdrawalsItemDTO.getWithdrawalsStatus().intValue())){
                    return  new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR,"已经执行这条打款操作记录,不可多次操作！");
                }
                break;
            //拒绝  前置条件是同意或者初始化
            case 2:
                if(status !=1 && status != 2){
                    return  new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR,"已经执行这条打款操作记录,不可多次操作！");
                }
                break;
            //打款,前置条件必须是同意
            case 3:
                if(status != 2){
                    return  new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR,"已经执行这条打款操作记录,不可多次操作！");

                }
                break;
        }





        Long userId = withdrawalsItemDTO.getUserId();

        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

        if(withdrawalsItemQTO == null){
            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "withdrawalsItemQTO is null");
        }
        if(!StringUtils.hasText( withdrawalsItemQTO.getWithdrawalsNumber()) ){
            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "withdrawalsNumber is null");
        }
        if(withdrawalsItemQTO.getDotype() == 2 && !StringUtils.hasText(withdrawalsItemQTO.getRefuse())){
            return  new VirtualWealthResponse(ResponseCode.PARAMETER_NULL,"refuse is null");
        }
        if(withdrawalsItemQTO.getDotype() == 3 && !StringUtils.hasText( withdrawalsItemQTO.getBanklog())){
            return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "banklog is null");
        }
        if(withdrawalsItemQTO.getDotype() == 3  && withdrawalsItemQTO.getBanklog().length()> 200){
            return new VirtualWealthResponse(ResponseCode.PARAMETER_ERROR, "提示语不能超过200个字！");
        }

        try {
            withdrawalsItemManager.updateRecord(withdrawalsItemQTO);
            WithdrawalsItemLogDO withdrawalsItemLogDO = new WithdrawalsItemLogDO();
            withdrawalsItemLogDO.setUserId(userId);
            switch (withdrawalsItemQTO.getDotype()){
                //同意
                case 1:
                    withdrawalsItemLogDO.setWdLogStatus(2);
                    break ;
                case 2:
                    withdrawalsItemLogDO.setWdLogStatus(5);
                    break;
                //打款
                case 3:
                    withdrawalsItemLogDO.setWdLogStatus(3);
                    break;
            }
            withdrawalsItemLogDO.setWdLogNumber(withdrawalsItemQTO.getWithdrawalsNumber());
            withdrawalsItemLogDO.setWdLogTime(new Date());
            withdrawalsItemLogManager.insert(withdrawalsItemLogDO);
        }catch (VirtualWealthException e){
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());
            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }

        return new VirtualWealthResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.UPDATE_WITHDRAWALS_ITEM.getActionName();
    }
}