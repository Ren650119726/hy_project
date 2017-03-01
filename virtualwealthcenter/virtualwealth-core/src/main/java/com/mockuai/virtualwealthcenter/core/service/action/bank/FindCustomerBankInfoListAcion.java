package com.mockuai.virtualwealthcenter.core.service.action.bank;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBankInfoItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

/**
 * 客户管理 -银行卡管理 -流水
 * @author Administrator
 *
 */
@Service
public class FindCustomerBankInfoListAcion extends TransAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerBankInfoListAcion.class.getName());
	
	
	@Autowired
	private BankInfoAppManager bankInfoAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_BANKINFO_PAGELIST.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
		
	    try {
            VirtualWealthResponse response ;
            BankInfoQTO bankInfoQTO = (BankInfoQTO) context.getRequest().getObject("bankInfoQTO");
//            int count =    withdrawalsItemManager.count(withdrawalsItemQTO);
            List<BankInfoAppDO> result = bankInfoAppManager.findCustomerBankInfoPageList(bankInfoQTO);
            
            List<BossBankInfoItemDTO> list = new ArrayList<BossBankInfoItemDTO>();
            if(result != null){
            	for(BankInfoAppDO w : result){
            		BossBankInfoItemDTO bw = new BossBankInfoItemDTO();
            		bw.setId(String.valueOf(w.getId()));
            		bw.setBank_no(w.getBankLastno());
            		bw.setBank_name(w.getBankName());
            		bw.setBank_type(String.valueOf(w.getBankType()));
            		bw.setDelete_mark(String.valueOf(w.getDeleteMark()));
            		bw.setGmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
            		bw.setGmt_modified(DateFormatUtils.format(w.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
            		bw.setWithdrawals_amount(w.getTotalSum());
            		bw.setWithdrawals_num(String.valueOf(w.getTotalCount()));
            		list.add(bw);
            	}
            	response =   new VirtualWealthResponse(list);
 	            response.setTotalCount(bankInfoQTO.getTotalCount());
            }else{
            	response =   new VirtualWealthResponse(new BossBankInfoItemDTO());
	            response.setTotalCount(0);
            }
            
            return response;
        } catch (VirtualWealthException e) {
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());

            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
	}

}
