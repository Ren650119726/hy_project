package com.mockuai.virtualwealthcenter.core.service.action.bank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;


@Service
public class selectDetailsBankInfoAppAction extends TransAction{

	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.SEL_BANK_INFO.getActionName();
	}

	/**
	 * 获取银行卡详情
	 */
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		
		Long userId = (Long) context.getRequest().getParam("userId");
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        
        Long id  = (Long) context.getRequest().getParam("id");
        VirtualWealthPreconditions.checkNotNull(id, "id");
		
        BankInfoAppDO bankInfoAppDO = bankInfoAppManager.selectDetailsBankInfo(id, userId);
        
        if(bankInfoAppDO == null){
        	return new VirtualWealthResponse(new BankInfoAppDO());
        }
        
		return new VirtualWealthResponse(bankInfoAppDO);
	}

}
