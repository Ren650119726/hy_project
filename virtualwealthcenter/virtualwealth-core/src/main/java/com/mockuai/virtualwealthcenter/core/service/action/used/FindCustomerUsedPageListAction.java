package com.mockuai.virtualwealthcenter.core.service.action.used;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBalanceItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossVirtualItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

/**
 * 客户管理 余额或者嗨币的支出
 * @author Administrator
 */
@Service
public class FindCustomerUsedPageListAction extends TransAction{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerUsedPageListAction.class.getName());
	
	@Autowired
	private UsedWealthManager usedWealthManager;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stubFIND_CUSTOMER_USED_PAGELIST
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_USED_PAGELIST.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
		
	    try {
            VirtualWealthResponse response = null ;
            UsedWealthQTO usedWealthQTO = (UsedWealthQTO) context.getRequest().getObject("usedWealthQTO");
            Integer wealthType = Integer.valueOf(usedWealthQTO.getWealthType());
//          int count =    withdrawalsItemManager.count(withdrawalsItemQTO);
            List<UsedWealthDO> result = usedWealthManager.findCustomerUsedPageList(usedWealthQTO);
            
            
            if(wealthType == 1){
            	List<BossBalanceItemDTO> list = new ArrayList<BossBalanceItemDTO>();
   	            if(result != null){
   	            	for(UsedWealthDO w : result){
   	            		BossBalanceItemDTO bw = new BossBalanceItemDTO();
   	            		bw.setB_id(String.valueOf(w.getId()));
   	            		bw.setB_amount(w.getAmount());
   	            		bw.setB_type("2");//支出
   	            		bw.setB_source_type("7");//7默认交易
   	            		bw.setB_status(String.valueOf(w.getStatus()));
   	            		bw.setB_gmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
   	            		bw.setB_remark("订单ID："+w.getOrderId()+";订单编号："+w.getOrderSN());
   	            		list.add(bw);
   	            	}
   	            	response =   new VirtualWealthResponse(list);
   	 	            response.setTotalCount(usedWealthQTO.getTotalCount());
   	            }else{
   	            	response =   new VirtualWealthResponse(new BossBalanceItemDTO());
   	                response.setTotalCount(0);
   	            }
            }else{
            	List<BossVirtualItemDTO> list = new ArrayList<BossVirtualItemDTO>();
   	            if(result != null){
   	            	for(UsedWealthDO w : result){
   	            		BossVirtualItemDTO bw = new BossVirtualItemDTO();
   	            		bw.setV_id(String.valueOf(w.getId()));
   	            		bw.setV_amount(w.getAmount());
   	            		bw.setV_type("2");//支出
   	            		bw.setV_source_type("7");//7默认交易
   	            		bw.setV_status(String.valueOf(w.getStatus()));
   	            		bw.setV_gmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
   	            		bw.setV_remark("订单ID："+w.getOrderId()+";订单编号："+w.getOrderSN());
   	            		list.add(bw);
   	            	}
   	            	response =   new VirtualWealthResponse(list);
   	 	            response.setTotalCount(usedWealthQTO.getTotalCount());
   	            }else{
   	            	response =   new VirtualWealthResponse(new BossVirtualItemDTO());
   	                response.setTotalCount(0);
   	            }
            }
	         
            
            return response;
        } catch (VirtualWealthException e) {
            LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());

            return new VirtualWealthResponse(e.getCode(), e.getMessage());
        }
	}

}
