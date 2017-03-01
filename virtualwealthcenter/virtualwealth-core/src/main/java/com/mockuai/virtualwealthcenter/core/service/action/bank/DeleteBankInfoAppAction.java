package com.mockuai.virtualwealthcenter.core.service.action.bank;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_BANK_INFO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopBankInfoAppDTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;


@Service
public class DeleteBankInfoAppAction extends TransAction{

	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DEL_BANK_INFO.getActionName();
	}
	
	
	/**
	 * 逻辑删除银行卡
	 */
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		 	Long userId = (Long) context.getRequest().getParam("userId");
	        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
	        VirtualWealthPreconditions.checkNotNull(userId, "userId");
	        
	        Long id  = (Long) context.getRequest().getParam("id");
	        VirtualWealthPreconditions.checkNotNull(id, "id");
	        
	        BankInfoAppDO bankinfoappdo = bankInfoAppManager.selectDetailsBankInfo(id, userId);
	        if(bankinfoappdo.getBankIsdefault() == 0){
	        	 return new VirtualWealthResponse(ResponseCode.DEL_IS_DEFAULT);
	        }
//	        List<BankInfoAppDO> listBankInfoAppDo = bankInfoAppManager.getWithdrawalsItem(userId);
//	        if(listBankInfoAppDo != null &&listBankInfoAppDo.size()== 1){
//	        	 //判断如果只剩要一个卡的时候记得转为默认
//	        	 bankInfoAppManager.updateBankInfoIsDefalut(id, userId);
//	        	 return new VirtualWealthResponse(ResponseCode.DEL_IS_DEFAULT);
//			}
	        bankInfoAppManager.deleteBankInfo(id, userId);
	        
	        return VirtualWealthUtils.getSuccessResponse();
	}

}
