package com.mockuai.virtualwealthcenter.core.service.action.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBalanceItemDetailDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossVirtualItemDetailDTO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;


/**
 * 客户管理 余额详情
 */
@Service
public class FindCustomerBalanceDetailAction  extends TransAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerVirtualDetailAction.class.getName());
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_BALANCE_DETAIL.getActionName();
	}

	
	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
		
	    try {
            VirtualWealthResponse response ;
            WealthAccountDO result = wealthAccountManager.findCustomerBalanceDetail(userId);

            if(result != null){
            	BossBalanceItemDetailDTO bw = new BossBalanceItemDetailDTO();
            	bw.setW_amount(result.getAmount());
            	bw.setW_tamount(result.getTransitionAmount());
            	bw.setW_total_p(result.getTotal());
            	bw.setW_total_w(result.getSumAmount());
                response = new VirtualWealthResponse(bw);
            }else{
            	response = new VirtualWealthResponse(new BossBalanceItemDetailDTO());
            }
            
            return response;
        } catch (VirtualWealthException e) {
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());

            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
	}

}
