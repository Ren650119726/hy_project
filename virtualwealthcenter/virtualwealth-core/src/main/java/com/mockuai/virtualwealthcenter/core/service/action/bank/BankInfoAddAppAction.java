package com.mockuai.virtualwealthcenter.core.service.action.bank;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_ADD;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.core.dao.BankInfoAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.service.action.authon.BankBinResutl;
import com.mockuai.virtualwealthcenter.core.service.action.authon.HttpBankBinUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;

@Service
public class BankInfoAddAppAction extends TransAction{

	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	
	@Autowired
	private UserClient userClient;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.ADD_BANK_INFO.getActionName();
	}

	/**
	 * 添加银行卡
	 */
	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		 
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        
        
        com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserById(userId, appInfo.getAppKey());
        if(userclient.getModule() == null){
        	//该用户不存在
        	return new VirtualWealthResponse(ResponseCode.NOT_EXIST_USER);
        }

        
        UserAuthonAppDO userAuthonAppDO =  userAuthonAppManager.selectUserAuton(userId);
        
        //判断是否先实名认证
        if(userAuthonAppDO == null){
        	 return new VirtualWealthResponse(ResponseCode.NOT_AUTON);
        }
        //发卡银行
        String bank_name = (String)context.getRequest().getParam("bank_name");
        //银行卡号
        String bank_no = (String)context.getRequest().getParam("bank_no");
        
        //银行卡卡BIN校验
        String bresult = HttpBankBinUtil.bankBinClient(bank_no);
        BankBinResutl bankBinResutl =  JsonUtil.parseJson(bresult, BankBinResutl.class);
        if(bankBinResutl == null){
        	return new VirtualWealthResponse(ResponseCode.ERROR_BANKBIN_AUTHON);
        }
        
        if(bankBinResutl.getRet_code().equals("0000")){
        	//实名认证不支持信用卡验证
        	if(bankBinResutl.getCard_type().equals("3")){
        		return new VirtualWealthResponse(ResponseCode.ERROR_CREDIT_ADDBANK);
        	}
        	

        }else{
        	return new VirtualWealthResponse(ResponseCode.ERROR_BANKBIN_AUTHON);
        }
        
      
        
        BankInfoAppDO bankInfoAppDO = new BankInfoAppDO();
        bankInfoAppDO.setUserId(userId);
        bankInfoAppDO.setBankName(bank_name);
        bankInfoAppDO.setBankLastno(bank_no.substring(bank_no.length()-4,bank_no.length()));
        bankInfoAppDO.setBankNo(bank_no);
        bankInfoAppDO.setBankType(1);
        bankInfoAppDO.setBankIsdefault(1);
        bankInfoAppDO.setBankRealname(userAuthonAppDO.getAuthonRealname());
        
        //判断一个人最多添加10张银行卡
        List<BankInfoAppDO> listBankInfoAppDo = bankInfoAppManager.getWithdrawalsItem(userId);
        if(listBankInfoAppDo == null || listBankInfoAppDo.size() == 0){
        	bankInfoAppDO.setBankIsdefault(0);
        }
        
        if(listBankInfoAppDo != null &&listBankInfoAppDo.size()== 10){
        	return new VirtualWealthResponse(ResponseCode.MAX_BANKINFO_NUM);
		}
        //如果是库里已经存在则更新
        BankInfoAppDO exists_bank = bankInfoAppManager.getBankInfoExists(bank_no);
        if(exists_bank != null){
        	if(exists_bank.getDeleteMark() == 0){
        		return new VirtualWealthResponse(ResponseCode.REPEAT_BANKINFO);
        	}else{
        		//如果已存在且是删除状态的更新成未删除
        		bankInfoAppManager.updateBankInfoDelStatus(exists_bank.getId(), userId);
        		return VirtualWealthUtils.getSuccessResponse();
        	}
        }
        

        
        bankInfoAppManager.addBankInfo(bankInfoAppDO);
        
		return VirtualWealthUtils.getSuccessResponse();
	}

	
}
