package com.mockuai.distributioncenter.core.service.action.seller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;

/**
 * Created by duke on 15/10/19.
 */
@Service
public class CreateSellerHaiKeMNSJAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(CreateSellerHaiKeMNSJAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private HkProtocolRecordManager hkProtocolRecordManager; 
    
    @Autowired
    private TradeManager tradeManager;
    
    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
    	long startTimePoint1 = System.currentTimeMillis();
        RequestAdapter request = context.getRequestAdapter();
        String appKey = (String) context.get("appKey");
        Integer shuliang = 0;
        Integer offset = 0;
        while(true){
        	long startTimePoint = System.currentTimeMillis();
        	OrderQTO orderQTO = new OrderQTO();
        	orderQTO.setOffset(offset);
        	orderQTO.setCount(500);
        	List<OrderDTO> list = tradeManager.getUsers(orderQTO, appKey);
        	for (int i = 0; i < list.size(); i++) {
        		shuliang++;
        		log.info("shuliang jindu,shuliang1:{}",shuliang );
        		SellerDTO sellerDTO = sellerManager.getByUserId(list.get(i).getUserId());
                if (sellerDTO != null && sellerDTO.getStatus().equals(1)) {
                    // 卖家已经存在，不允许创建
                    log.error("seller already exists, seller_id: {}, user_id: {}", sellerDTO.getId(), sellerDTO.getUserId());
                    continue;
                }
				
                if (sellerDTO == null) {
                    sellerDTO = new SellerDTO();
                    sellerDTO.setRealName("");
                    sellerDTO.setUserName("");
                    sellerDTO.setWechatId("");
                    sellerDTO.setUserId(list.get(i).getUserId());
                    sellerDTO.setStatus(1);
                    sellerDTO.setLevelId(1L);
                    sellerDTO.setDirectCount(0L);
                    sellerDTO.setGroupCount(0L);
                    sellerDTO.setInviterCode("");
                    Long id = sellerManager.add(sellerDTO);
                    // generate invite code
                    // 1000000 + id ==> inviteCode
                    String inviteCode = String.valueOf(1000000L + id);
                    sellerDTO.setId(id);
                    sellerDTO.setInviterCode(inviteCode);
                    sellerManager.update(sellerDTO);
                } else {
                    // 更新卖家
                	sellerDTO.setStatus(1);
                	sellerManager.update(sellerDTO);
                }
			}
        	
        	long endTimePoint = System.currentTimeMillis();
   		 	log.info("query for  :{} ms", endTimePoint - startTimePoint);
   		 	
        	if(list.size() < 500){
        		break;
        	}
        	offset += list.size();
        }
        long endTimePoint1 = System.currentTimeMillis();
    	log.info("query chaxun shijian :{} ms", endTimePoint1 - startTimePoint1);
        return new DistributionResponse(ResponseCode.SUCCESS);
    }

    @Override
    public String getName() {
        return ActionEnum.USERSELLER.getActionName();
    }
}
