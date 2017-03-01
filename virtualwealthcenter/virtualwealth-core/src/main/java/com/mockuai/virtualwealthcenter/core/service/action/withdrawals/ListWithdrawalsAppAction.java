package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWdBankInfoAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.DistributeManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsConfigManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.service.action.authon.AuthonResult;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;


@Service
public class ListWithdrawalsAppAction extends TransAction{
	private static final Logger LOGGER = LoggerFactory.getLogger(ListWithdrawalsAppAction.class.getName());
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Autowired
	private WithdrawalsConfigManager withdrawalsItemConfig;
	
	@Autowired
	private WealthAccountManager wealthAccountManager;

	@Autowired 
	private DistributeManager distributeManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.LIST_WD_BANK.getActionName();
	}

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
        }else{
        	//还未绑定手机
            if(userclient.getModule().getMobile() == null || userclient.getModule().getMobile().trim().equals("")){
            	return new VirtualWealthResponse(ResponseCode.NOBIND_MOBILE_USER);
            }
        	//Long.valueOf();
        	if((Long)userclient.getModule().getRoleMark() == 1){//2是卖家，判断必须是卖家 1是买家
        		return new VirtualWealthResponse(ResponseCode.ROLE_MARK_USER);
        	}
        }
	    
	    // 未同意嗨客协议  TODO
        
	    if(!distributeManager.getUserAgreeProcotolRecord(userId, appInfo.getAppKey())){
	    	return new VirtualWealthResponse(ResponseCode.NOT_AGREE_PROTOCOL);
	    }
      //获取提现配置
        List<WithdrawalsConfigDTO> withdrawalsConfigDTOs = withdrawalsItemConfig.queryList(new WithdrawalsConfigQTO());
        
        WithdrawalsConfigDTO withdrawalsConfigDTO = withdrawalsConfigDTOs.get(0);
        
        if(withdrawalsConfigDTO != null){
        	if(withdrawalsConfigDTO.getWdIsflag() == 2){
        		//配置不允许提现
        		return new VirtualWealthResponse(32076,withdrawalsConfigDTO.getWdConfigText());
        	}
        }
        
        UserAuthonAppDO userAuthonAppDO =  userAuthonAppManager.selectUserAuton(userId);
        //判断是否先实名认证
        if(userAuthonAppDO == null || userAuthonAppDO.getAuthonStatus()!=1){
        	 return new VirtualWealthResponse(ResponseCode.NOT_AUTON);
        }
//        LOGGER.error("USERID:"+userId);        
        List<BankInfoAppDO> bankinfolist = bankInfoAppManager.getWithdrawalsItem(userId);
//        LOGGER.error("GET BANK SIZE:"+bankinfolist.size());
	    //没有绑定过银行卡
	    if(bankinfolist == null || bankinfolist.size() == 0){
	    	return new VirtualWealthResponse(ResponseCode.NO_EXIST_BANKINFO,userAuthonAppDO.getAuthonRealname());
	    }
	    
	    
	    WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, 1, null);
	   
	    List<MopWdBankInfoAppDTO> mopWdBankInfoAppDTOs = new ArrayList<MopWdBankInfoAppDTO>();
	    for (BankInfoAppDO bankInfoAppDO : bankinfolist) {
//	    	LOGGER.error("BANKSTATUS:"+bankInfoAppDO.getBankIsdefault());
//	    	LOGGER.error("wealthAccountDO IS NULL"+(wealthAccountDO == null));
			if(bankInfoAppDO.getBankIsdefault() == 0){
				MopWdBankInfoAppDTO mopwdbankinfo = new MopWdBankInfoAppDTO();
				mopwdbankinfo.setWd_config_text(withdrawalsConfigDTO.getWdConfigText());
				if(wealthAccountDO == null){
					mopwdbankinfo.setWd_config_maxnum("0.00");
				}else{
					//Long maxnum = withdrawalsConfigDTO.getWdConfigMaximum() <= wealthAccountDO.getAmount()?withdrawalsConfigDTO.getWdConfigMaximum():wealthAccountDO.getAmount();
					//String num = AuthonResult.changeF2Y(maxnum);
					String num = AuthonResult.changeF2Y(wealthAccountDO.getAmount());
					mopwdbankinfo.setWd_config_maxnum(num.indexOf(".") < 0?num+".00":num);
				}
				
//				if(wealthAccountDO.getAmount() < withdrawalsConfigDTO.getWdConfigMinimum()){
//					mopwdbankinfo.setWd_config_mininum("0.00");
//					mopwdbankinfo.setWd_config_maxnum("0.00");
//				}else{
					String num = AuthonResult.changeF2Y(withdrawalsConfigDTO.getWdConfigMinimum());
					mopwdbankinfo.setWd_config_mininum(num.indexOf(".") < 0?num+".00":num);
//				}
				mopwdbankinfo.setId(bankInfoAppDO.getId());
				mopwdbankinfo.setUser_id(bankInfoAppDO.getUserId());
				mopwdbankinfo.setGmt_modified(bankInfoAppDO.getGmtModified());
				mopwdbankinfo.setGmt_created(bankInfoAppDO.getGmtCreated());
				mopwdbankinfo.setDelete_mark(bankInfoAppDO.getDeleteMark());
				mopwdbankinfo.setBank_type(bankInfoAppDO.getBankType());
				mopwdbankinfo.setBank_single_quota(bankInfoAppDO.getBankSingleQuota());
				mopwdbankinfo.setBank_remark(bankInfoAppDO.getBankRemark());
				mopwdbankinfo.setBank_realname(bankInfoAppDO.getBankRealname());
				mopwdbankinfo.setBank_oneday_quota(bankInfoAppDO.getBankOnedayQuota());
				mopwdbankinfo.setBank_no(bankInfoAppDO.getBankNo());
				mopwdbankinfo.setBank_name(bankInfoAppDO.getBankName());
				mopwdbankinfo.setBank_lastno(bankInfoAppDO.getBankLastno());
				mopwdbankinfo.setBank_isdefault(bankInfoAppDO.getBankIsdefault());
				mopwdbankinfo.setBank_bindtime(bankInfoAppDO.getBankBindtime());
				
				mopWdBankInfoAppDTOs.add(mopwdbankinfo);
				break;
			}
		}
		return new VirtualWealthResponse(mopWdBankInfoAppDTOs);
	}

}
