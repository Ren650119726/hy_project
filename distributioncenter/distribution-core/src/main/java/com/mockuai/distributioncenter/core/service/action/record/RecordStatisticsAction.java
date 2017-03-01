package com.mockuai.distributioncenter.core.service.action.record;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.util.JsonUtil;

/**
 * Created by duke on 16/5/23.
 */
@Service
public class RecordStatisticsAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(RecordStatisticsAction.class);
    @Autowired
    private DistRecordManager distRecordManager;


    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;
    
    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
    	long startTimePoint1 = System.currentTimeMillis();
    	Request request = context.getRequest();
    	DistRecordQTO distRecordQTO = (DistRecordQTO) request.getParam("distRecordQTO");
        Long userId = (Long) request.getParam("userId");
        
    	Map<Long, Boolean> cunzai = new HashMap<Long, Boolean>();
    	Map<Long, SellerDTO> cunzai2 = new HashMap<Long, SellerDTO>();
    	
    	List<DistRecordDTO>  list = distRecordManager.queryStatistics(distRecordQTO);
    	DistRecordDTO distRecord = new DistRecordDTO();
    	for (DistRecordDTO distRecordDTO : list) {
    		 long startTimePoint = System.currentTimeMillis();
    		 Boolean b = null;
    		 SellerDTO sellerDTO = new SellerDTO();
    		 Long id =  distRecordDTO.getSellerId();
    		 
    		 if(cunzai2 != null && cunzai2.containsKey(id)){
    			 sellerDTO = cunzai2.get(id);
    			 log.info("cunzai2 for");
    		 }else{
    			 sellerDTO = sellerManager.get(id);
    			 cunzai2.put(id, sellerDTO);
    		 }
    		 
    		
    		 
    		 if(cunzai != null && cunzai.containsKey(sellerDTO.getUserId())){
    			 log.info("cunzai1 for");
    			 b = cunzai.get(sellerDTO.getUserId());
    		 }else{
    			 b = this.guanxi(userId,sellerDTO.getUserId());
    			 cunzai.put(sellerDTO.getUserId(), b);
    		 }
    		
    		 
    		 if(b){
    			 //销售额度
    			 Long totalAmount = distRecordDTO.getUnitPrice() * distRecordDTO.getNumber();
    			 if(distRecord.getXiaoshou1() == null){
    				 distRecord.setXiaoshou1(totalAmount);
    			 }else{
    				 distRecord.setXiaoshou1(totalAmount+ distRecord.getXiaoshou1());
    			 }
    			 //销售分拥
    			 Long totalAmount1 = distRecordDTO.getDistAmount();
    			 if(distRecord.getXiaoshou2() == null){
    				 distRecord.setXiaoshou2(totalAmount1);
    			 }else{
    				 distRecord.setXiaoshou2(totalAmount1 + distRecord.getXiaoshou2());
    			 }
    			 
    			 
    			 //团队分拥
    		    	DistRecordQTO distRecordsss = new DistRecordQTO();
    		    	distRecordsss.setType(0);
    		    	distRecordsss.setSource(2);
    		    	distRecordsss.setItemSkuId(distRecordDTO.getItemSkuId());
    		    	distRecordsss.setOrderId(distRecordDTO.getOrderId());
    		    	List<DistRecordDTO>  list1 = distRecordManager.query(distRecordsss);
    		    	for (int i = 0; i < list1.size(); i++) {
    		    		
						if(i == 0 && list1.get(i).getSellerId() != 1){
							
							Long tuandui1 = list1.get(i).getDistAmount();
							if(distRecord.getTuandui1() == null){
								distRecord.setTuandui1(tuandui1);
							}else{
								distRecord.setTuandui1(tuandui1 + distRecord.getTuandui1());
							}
							
						}else if (i == 1 && list1.get(i).getSellerId() != 1){
							Long tuandui2 = list1.get(i).getDistAmount();
							if(distRecord.getTuandui2() == null){
								distRecord.setTuandui2(tuandui2);
							}else{
								distRecord.setTuandui2(tuandui2 + distRecord.getTuandui2());
							}
							
							
						}else if (i == 2){
							if( list1.get(i).getSellerId() != 1){
								Long tuandui3 = list1.get(i).getDistAmount();
								if(distRecord.getTuandui3() == null){
									distRecord.setTuandui3(tuandui3);
								}else{
									distRecord.setTuandui3(tuandui3 + distRecord.getTuandui3());
								}
								
							}
							
							//第四笔分拥
		    		    	if(distRecordDTO.getDistRatio() >= 0.11 && distRecordDTO.getDistRatio() <= 0.25 ){
		    		    		Long tuandui4 = list1.get(i).getDistAmount();
		    		    		if(distRecord.getTuandui4() == null){
		    		    			distRecord.setTuandui4(tuandui4);
		    		    		}else{
		    		    			distRecord.setTuandui4(tuandui4 + distRecord.getTuandui4());
		    		    		}
		    		    		
		    		    	}
						}
					}
    			 
    		    	
    		 }
    		 long endTimePoint = System.currentTimeMillis();
    		 log.info("query for  :{} ms", endTimePoint - startTimePoint);
		}
    	
    	long endTimePoint1 = System.currentTimeMillis();
    	log.info("query chaxun shijian :{} ms", endTimePoint1 - startTimePoint1);
    	log.info("jieguo:{}", JsonUtil.toJson(distRecord));
        return new DistributionResponse(distRecord);
    }

    @Override
    public String getName() {
        return ActionEnum.RECORDSTATISTICS.getActionName();
    }
    
    private Boolean guanxi(Long userId,Long currUserId) throws DistributionException{
    	if(userId.equals(currUserId)){
    		return true;
    	}
    	if(currUserId == 1 ){
    		return false;
    	}
    	Long parentUserId = getParentId(currUserId);
    	while (true) {
    		if(parentUserId == 0){
    			return false;
    		}
            if (parentUserId.equals(userId)) {
                return true;
            }
            currUserId = parentUserId;
            parentUserId = getParentId(currUserId);
        }
    	
    }
    
    private Long getParentId(Long userId) throws DistributionException {
        Long parentUserId;
            // 如果不包含，则从数据库获取
            SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(userId);
            if (relationshipDTO == null) {
                log.error("no relationship find, userId: {}", userId);
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no relationship find");
            }
            parentUserId = relationshipDTO.getParentId();
        return parentUserId;
    }
}
