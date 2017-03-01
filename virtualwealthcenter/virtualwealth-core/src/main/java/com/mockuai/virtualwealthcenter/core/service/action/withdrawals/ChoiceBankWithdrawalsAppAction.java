package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

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
public class ChoiceBankWithdrawalsAppAction extends TransAction{

	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.CHOICE_WD_BANK.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {

		//用户id
		Long userId = (Long) context.getRequest().getParam("userId");
		VirtualWealthPreconditions.checkNotNull(userId, "userId");
		
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
	    
	    //银行卡号
        String bank_no = (String)context.getRequest().getParam("bank_no");
        VirtualWealthPreconditions.checkNotNull(bank_no, "bank_no");
	    
        BankInfoAppDO bankInfoAppDO = bankInfoAppManager.getBankInfodetails(userId, bank_no);
        
        if(bankInfoAppDO == null){
        	return new VirtualWealthResponse(new BankInfoAppDO());
        }
        
		return new VirtualWealthResponse(bankInfoAppDO);
	}

}
