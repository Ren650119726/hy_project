package com.mockuai.virtualwealthcenter.core.service.action.account;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBankInfoItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossVirtualItemDetailDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

/**
 * 客户管理 嗨币 详情  overTime = 当前时间-10个月
 */
@Service
public class FindCustomerVirtualDetailAction extends TransAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerVirtualDetailAction.class.getName());
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_Virtual_DETAIL.getActionName();
	}

	
	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		String overTime = (String) context.getRequest().getParam("overTime");
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
		
	    try {
            VirtualWealthResponse response ;
            WealthAccountDO result = wealthAccountManager.findCustomerVirtualDetail(userId, overTime);
            
            if(result != null){
            	BossVirtualItemDetailDTO bw = new BossVirtualItemDetailDTO();
            	bw.setV_amount(result.getAmount());
            	bw.setV_overtime(result.getOverAmount()==null?0:result.getOverAmount() );
            	bw.setV_tamount(result.getTransitionAmount());
                response =   new VirtualWealthResponse(bw);
            }else{
            	response =   new VirtualWealthResponse(new BossVirtualItemDetailDTO());
            }
            return response;
        } catch (VirtualWealthException e) {
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());

            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
	}

}
