package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

/**
 * 客户管理 获取提现记录
 * @author Administrator
 *
 */
@Service
public class FindCustomerWithdrawalsListAction extends TransAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerWithdrawalsListAction.class.getName());
	
	@Autowired
    private WithdrawalsItemAppManager withdrawalsItemAppManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_WD_PAGELIST.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		 Long userId = (Long) context.getRequest().getParam("userId");
	     AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
	     
	        try {
	            VirtualWealthResponse response ;
	            WithdrawalsItemQTO withdrawalsItemQTO = (WithdrawalsItemQTO) context.getRequest().getObject("withdrawalsItem");
//	            int count =    withdrawalsItemManager.count(withdrawalsItemQTO);
	            List<WithdrawalsItemAppDO> result = withdrawalsItemAppManager.findCustomerWithdrawalsPageList(withdrawalsItemQTO);
	            List<BossWithdrawalsItemDTO> list = new ArrayList<BossWithdrawalsItemDTO>();
	            if(result != null){
	            	for(WithdrawalsItemAppDO w : result){
	            		BossWithdrawalsItemDTO bw = new BossWithdrawalsItemDTO();
	            		bw.setW_id(String.valueOf(w.getId()));
	            		bw.setW_amount(w.getWithdrawalsAmount());
	            		bw.setW_type("2");
	            		bw.setW_source_type("6");
	            		bw.setW_status(String.valueOf(w.getWithdrawalsStatus()));
	            		bw.setW_gmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
	            		bw.setW_remark("提现ID："+w.getId()+"提现编号："+w.getWithdrawalsNumber());
	            		list.add(bw);
	            	}
	            	response =   new VirtualWealthResponse(list);
	 	            response.setTotalCount(withdrawalsItemQTO.getTotalCount());
	            }else{
	            	response =   new VirtualWealthResponse(new BossWithdrawalsItemDTO());
		            response.setTotalCount(0);
	            }
	            return response;
	        } catch (VirtualWealthException e) {
	            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());
	            return new VirtualWealthResponse(e.getCode(), e.getMessage());
	        }
	}

}
