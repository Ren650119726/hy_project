package com.mockuai.virtualwealthcenter.core.service.action.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopBankInfoAppDTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;


@Service
public class ListBankInfoAppAction extends TransAction{

	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_BANK_INFO.getActionName();
	}

	
	/**
	 * 获取银行卡列表
	 */
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
	    VirtualWealthPreconditions.checkNotNull(userId, "userId");
	    
	    List<BankInfoAppDO> bankinfolist = bankInfoAppManager.getWithdrawalsItem(userId);
	    List<MopBankInfoAppDTO> bankInfoAppDTOsList = new ArrayList<MopBankInfoAppDTO>();
	    
	    
	    com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserById(userId, appInfo.getAppKey());
        
        if(userclient.getModule() == null){
        	//该用户不存在
        	return new VirtualWealthResponse(ResponseCode.NOT_EXIST_USER);
        }else{
        	//Long.valueOf();
        	if((Long)userclient.getModule().getRoleMark() == 1){//2是卖家，判断必须是卖家
        		return new VirtualWealthResponse(ResponseCode.ERRORAUTHON_BANK_INF);
        	}
        }
	    
        UserAuthonAppDO userAuthonAppDO =  userAuthonAppManager.selectUserAuton(userId);
        //判断是否先实名认证
        if(userAuthonAppDO == null){
        	 return new VirtualWealthResponse(ResponseCode.NOT_BANK_AUTON);
        }
        
	    if(bankinfolist == null || bankinfolist.size() == 0){
	    	MopBankInfoAppDTO bankInfoDTO=new MopBankInfoAppDTO();
	    	bankInfoDTO.setBankRealname(userAuthonAppDO.getAuthonRealname());
	    	bankInfoAppDTOsList.add(bankInfoDTO);	    		
	    	return new VirtualWealthResponse(bankInfoAppDTOsList);
	    }
	    for(BankInfoAppDO bankinfo : bankinfolist){
	    	MopBankInfoAppDTO mopbankinfo = new MopBankInfoAppDTO();
	    	BeanUtils.copyProperties(bankinfo, mopbankinfo);
	    	bankInfoAppDTOsList.add(mopbankinfo);
	    }
	    return new VirtualWealthResponse(bankInfoAppDTOsList);
	}

}
