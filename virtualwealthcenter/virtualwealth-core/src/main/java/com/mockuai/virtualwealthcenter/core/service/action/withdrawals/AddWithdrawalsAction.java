package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.WdBankInfoDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemAppDAO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsConfigManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemAppManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemLogManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.service.action.authon.AuthonResult;
import com.mockuai.virtualwealthcenter.core.service.action.authon.UserAutonAppAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;

/**
 * 提现确认
 * @author Administrator
 *
 */
@Service
public class AddWithdrawalsAction extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(AddWithdrawalsAction.class);
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private WithdrawalsItemLogManager withdrawalsItemLogManager;
	
	@Autowired
	private WithdrawalsItemAppManager withdrawalsItemAppManager;
	
	@Autowired
	private WealthAccountManager wealthAccountManager;
	
	@Autowired
	private WithdrawalsConfigManager withdrawalsItemConfig;
	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.ADD_WD_SUBMIT.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        String withdrawals_amount  = (String) context.getRequest().getParam("withdrawals_amount");
        log.info("提现金额:"+withdrawals_amount);
        VirtualWealthPreconditions.checkNotNull(withdrawals_amount, "withdrawals_amount");
        String withdrawals_no = (String) context.getRequest().getParam("withdrawals_no");
        VirtualWealthPreconditions.checkNotNull(withdrawals_no, "withdrawals_no");
        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
        
        com.mockuai.usercenter.common.api.Response<UserDTO> userclient = userClient.getUserById(userId, appInfo.getAppKey());
        
        if(userclient.getModule() == null){
        	//该用户不存在
        	return new VirtualWealthResponse(ResponseCode.NOT_EXIST_USER);
        }
        
//        else{
//        	//Long.valueOf();
//        	if((Long)userclient.getModule().getRoleMark() == 1){//2是卖家，判断必须是卖家
//        		return new VirtualWealthResponse(ResponseCode.ROLE_MARK_USER);
//        	}
//        }
        
        UserAuthonAppDO userAuthonAppDO =  userAuthonAppManager.selectUserAuton(userId);
        //判断是否先实名认证
        if(userAuthonAppDO == null){
        	 return new VirtualWealthResponse(ResponseCode.NOT_AUTON);
        }
        
        BankInfoAppDO  bankinfoappDo =  bankInfoAppManager.selectBankInfoDelStatus(withdrawals_no,userId);
        //判断如果该账户银行卡已删除则提示已经删除
        if(bankinfoappDo != null){
        	if(bankinfoappDo.getDeleteMark() == 1){
        		return new VirtualWealthResponse(ResponseCode.DEL_ALREADY_BANKINFO);
        	}
        } else{
        	//判断该卡不存在
        	return new VirtualWealthResponse(ResponseCode.NO_EXIST_BANKINFO);
        }
        
//        double myMoney = Double.valueOf(withdrawals_amount)*100;
//
//	    BigDecimal money= new BigDecimal(Double.toString(myMoney));
//        
        //提现金额 元转分
        Long wdamount = AuthonResult.changeY2F(withdrawals_amount);
        System.out.println("提现金额元转分:"+wdamount);
        
        //获取提现配置
        List<WithdrawalsConfigDTO> withdrawalsConfigDTOs = withdrawalsItemConfig.queryList(new WithdrawalsConfigQTO());
        
        //查询余额
        WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, 1, null);
        
        if(wealthAccountDO == null || wealthAccountDO.getAmount() == null){
        	//账户余额信息不存在
        	return new VirtualWealthResponse(ResponseCode.ERROR_NULL_AMOUNT);
        }
        
        WithdrawalsConfigDTO withdrawalsConfigDTO = withdrawalsConfigDTOs.get(0);
        
        if(withdrawalsConfigDTO != null){
        	//元转分
        	Long minnum = withdrawalsConfigDTO.getWdConfigMinimum();
        	log.info("配置的最小金额分):"+minnum.toString());
        	Long maxnum = withdrawalsConfigDTO.getWdConfigMaximum();
        	log.info("配置的最大金额分):"+maxnum.toString());
        	//
        	if(wealthAccountDO.getAmount() < maxnum){
        		maxnum = wealthAccountDO.getAmount();
        		log.info("余额小于最大金额:"+maxnum.toString());
        	}
        	
        	//可提现余额不足
        	log.info("提现金额(分):"+wdamount);
        	log.info("提现余额(分):"+wealthAccountDO.getAmount());
        	if(wdamount > wealthAccountDO.getAmount()){
        		return new VirtualWealthResponse(ResponseCode.ERROR_MAX_WD);
        	}
        	
//        	if(wealthAccountDO.getAmount() < minnum){
//        		return new VirtualWealthResponse(ResponseCode.ERROR_MINI_WD,"单笔提现金额不可少于"+AuthonResult.changeF2Y(minnum)+"元");
//        	}
        	
        	if(wdamount < minnum){
        		log.info("单笔提现金额不可少于:"+AuthonResult.changeF2Y(minnum));
        		return new VirtualWealthResponse(ResponseCode.ERROR_MINI_WD,"单笔提现金额不可少于"+AuthonResult.changeF2Y(minnum)+"元");
        	}
        	if(wdamount > maxnum){
        		log.info("单笔提现金额不可大于:"+AuthonResult.changeF2Y(maxnum));
        		return new VirtualWealthResponse(ResponseCode.ERROR_MAX_WD,"单笔提现金额不可大于"+AuthonResult.changeF2Y(maxnum)+"元");
        	}
        }

//        String uuid = UUID.randomUUID().toString();
        String uuid = getWithdrawalsNumber(userId);
        
        WithdrawalsItemAppDO withdrawalsItemAppDO = new WithdrawalsItemAppDO();
        withdrawalsItemAppDO.setUserId(userId);
        withdrawalsItemAppDO.setWithdrawalsNo(withdrawals_no);
        withdrawalsItemAppDO.setWithdrawalsAmount(wdamount);
        withdrawalsItemAppDO.setWithdrawalsNumber(uuid);
        withdrawalsItemAppManager.addWithdrawalsItem(withdrawalsItemAppDO);
        
        
        WithdrawalsItemLogDO withdrawalsItemLogDO = new WithdrawalsItemLogDO();
        withdrawalsItemLogDO.setUserId(userId);
        withdrawalsItemLogDO.setWdLogNumber(uuid);
        withdrawalsItemLogDO.setWdLogStatus(1);
        withdrawalsItemLogDO.setWdLogTime(new Date());
        withdrawalsItemLogManager.insert(withdrawalsItemLogDO);
        
        //减去余额(冻结) 财富类型1=余额 
        wealthAccountManager.increaseAccountBalance(wealthAccountDO.getId(), userId, -withdrawalsItemAppDO.getWithdrawalsAmount());
        wealthAccountManager.increaseFrozenBalance(wealthAccountDO.getId(), (long)0);
        
        WdBankInfoDTO wdbd = new WdBankInfoDTO();
        wdbd.setWithdrawalsAmount(withdrawals_amount);      
        wdbd.setWithdrawalsLastno(withdrawals_no.substring(withdrawals_no.length()-4,withdrawals_no.length()));
        wdbd.setWithdrawalsNo(withdrawals_no);
        wdbd.setWithdrawalsStatus(1);
        wdbd.setWithdrawalsType(1);
        wdbd.setWithdrawalsNumber(uuid);
		return new VirtualWealthResponse(wdbd);
	}
	

	private static DateFormat DATE_FORMAT =new SimpleDateFormat("yyyyMMddHHmmssS");
	
	private String getWithdrawalsNumber(Long userId){
		String withdrawalsNumber = StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0")+"0"+StringUtils.leftPad("" + userId % 10000000, 7, "0")
				+genRandomNumber(3);
		return withdrawalsNumber;
	}
	
	private String genRandomNumber(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
        	int num = r.nextInt(str.length());
        	if(i==0){
        		num = r.nextInt(9);
        	}
            
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

}
