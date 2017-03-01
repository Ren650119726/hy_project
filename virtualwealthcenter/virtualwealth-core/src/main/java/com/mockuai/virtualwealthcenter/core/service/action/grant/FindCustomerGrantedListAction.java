package com.mockuai.virtualwealthcenter.core.service.action.grant;

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
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.service.action.bank.FindCustomerBankInfoListAcion;

/**
 * 客户管理 余额或者嗨币的收入
 * @author Administrator
 *
 */
@Service
public class FindCustomerGrantedListAction extends TransAction{

	private static final Logger LOGGER = LoggerFactory.getLogger(FindCustomerBankInfoListAcion.class.getName());
	
	@Autowired
	private GrantedWealthManager grantedWealthManager;
	
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.FIND_CUSTOMER_GRANTED_PAGELIST.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		
	    AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
		
	    try {
            VirtualWealthResponse response ;
            GrantedWealthQTO grantedWealthQTO = (GrantedWealthQTO) context.getRequest().getObject("grantedWealthQTO");
            Integer wealthType = grantedWealthQTO.getWealthType();
//          int count =    withdrawalsItemManager.count(withdrawalsItemQTO);
            List<GrantedWealthDO> result = grantedWealthManager.findCustomerGrantedPageList(grantedWealthQTO);
            
            if(wealthType == 1){
            	List<BossBalanceItemDTO> list = new ArrayList<BossBalanceItemDTO>();
   	            if(result != null){
   	            	for(GrantedWealthDO w : result){
   	            		BossBalanceItemDTO bw = new BossBalanceItemDTO();
   	            		bw.setB_id(String.valueOf(w.getId()));
   	            		bw.setB_amount(w.getAmount());
   	            		bw.setB_type("1");//支出
   	            		Integer sourcetype = null;
   	            		if(w.getSourceType() == 7){
   	            			sourcetype = 1;
   	            		}else if(w.getSourceType() == 8){
   	            			sourcetype = 2;
   	            		}else if(w.getSourceType() == 9){
   	            			sourcetype = 3;
   	            		}else if(w.getSourceType() == 5){
   	            			sourcetype = 4;
   	            		}else if(w.getSourceType() == 10){
   	            			sourcetype = 5;
   	            		}else{
   	            			sourcetype = 0;
   	            		}
   	            		bw.setB_source_type(String.valueOf(sourcetype));
   	            		bw.setB_status(String.valueOf(w.getStatus()+1));
   	            		bw.setB_gmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
   	            		bw.setB_remark("订单ID："+w.getOrderId()+";订单编号："+w.getOrderSN());
   	            		list.add(bw);
   	            	}
   	            	response =   new VirtualWealthResponse(list);
   	 	            response.setTotalCount(grantedWealthQTO.getTotalCount());
   	            }else{
   	            	response =   new VirtualWealthResponse(new BossBalanceItemDTO());
   	                response.setTotalCount(0);
   	            }
            }else{
            	List<BossVirtualItemDTO> list = new ArrayList<BossVirtualItemDTO>();
   	            if(result != null){
   	            	for(GrantedWealthDO w : result){
   	            		BossVirtualItemDTO bw = new BossVirtualItemDTO();
   	            		bw.setV_id(String.valueOf(w.getId()));
   	            		bw.setV_amount(w.getAmount());
   	            		bw.setV_type("1");//支出
   	            		Integer sourcetype = null;
   	            		if(w.getSourceType() == 7){
   	            			sourcetype = 1;
   	            		}else if(w.getSourceType() == 8){
   	            			sourcetype = 2;
   	            		}else if(w.getSourceType() == 9){
   	            			sourcetype = 3;
   	            		}else if(w.getSourceType() == 5){
   	            			sourcetype = 4;
   	            		}else if(w.getSourceType() == 10){
   	            			sourcetype = 5;
   	            		}else{
   	            			sourcetype = 0;
   	            		}
   	            		bw.setV_source_type(String.valueOf(sourcetype));
   	            		bw.setV_status(String.valueOf(w.getStatus()+1));
   	            		bw.setV_gmt_created(DateFormatUtils.format(w.getGmtCreated(), "yyyy-MM-dd HH:mm:ss"));
   	            		bw.setV_remark("订单ID："+w.getOrderId()+";订单编号："+w.getOrderSN());
   	            		list.add(bw);
   	            	}
   	            	response =   new VirtualWealthResponse(list);
   	 	            response.setTotalCount(grantedWealthQTO.getTotalCount());
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
