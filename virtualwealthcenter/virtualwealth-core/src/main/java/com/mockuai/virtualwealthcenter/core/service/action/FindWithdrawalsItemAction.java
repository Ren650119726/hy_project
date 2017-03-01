package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找提现记录
 * <p/>
 * Created by 冠生 on 5/25/15.
 */
@Service
public class FindWithdrawalsItemAction implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindWithdrawalsItemAction.class.getName());

    @Resource
    private WithdrawalsItemManager withdrawalsItemManager;

    @Override
    public VirtualWealthResponse execute(RequestContext context) throws VirtualWealthException {
        Long userId = (Long) context.getRequest().getParam("userId");
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
        

        try {
            VirtualWealthResponse response ;

            WithdrawalsItemQTO withdrawalsItemQTO = (WithdrawalsItemQTO) context.getRequest().getObject("withdrawalsItem");
            //输入都是0
           /* if(withdrawalsItemQTO.getStartAmount() == 0 && withdrawalsItemQTO.getEndAmount() == 0){
                response = new VirtualWealthResponse(ResponseCode.SUCCESS);
                response.setTotalCount(0L);
                return response;
            }*/
            int count =    withdrawalsItemManager.count(withdrawalsItemQTO);
            List<WithdrawalsItemDTO> result = withdrawalsItemManager.findList(withdrawalsItemQTO);
            if(null==result){
            	response = new VirtualWealthResponse(new WithdrawalsItemDTO());
            	response.setTotalCount(0);
            	return response;
            }
            List<WithdrawalsItemDTO> res = new ArrayList<WithdrawalsItemDTO>();
            
            //分别取出并处理银行卡号
            for(WithdrawalsItemDTO withdrawalsItemDTO:result){
            	StringBuffer bankNo = new StringBuffer(withdrawalsItemDTO.getWithdrawalsNo());
            	bankNo = bankNo.replace(4,bankNo.length()-4,"********");
            	withdrawalsItemDTO.setWithdrawalsNo(bankNo.toString());
            	res.add(withdrawalsItemDTO);
            }
            response =   new VirtualWealthResponse(res);
            response.setTotalCount(count);
            return response;
        } catch (VirtualWealthException e) {
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());

            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.FIND_WITHDRAWALS_ITEM.getActionName();
    }
}