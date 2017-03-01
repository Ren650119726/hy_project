package com.mockuai.distributioncenter.core.service.action.distribution.finder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;

/**
 * Created by duke on 16/5/16.
 */
public class NewSimpleFinder implements NewFinder {
    private static final Logger log = LoggerFactory.getLogger(NewSimpleFinder.class);
  
    private SellerRelationshipManager relationshipManager;
    private SellerManager sellerManager;
    

    public NewSimpleFinder(SellerRelationshipManager relationshipManager, SellerManager sellerManager) {
    	 this.relationshipManager = relationshipManager;
         this.sellerManager = sellerManager;
    }
    
    
    @Override
    public Long find(final Long userID) throws DistributionException {
    	
    	if(userID == 1){
    		return userID; 
    	}
    	
    	Long parentUserId = getParentId(userID);
    	
    	// 本级或者上级是1号店返回上级id
    	if(parentUserId == 1 || parentUserId == 0){
    		return parentUserId;
    	}
    	
    	//卖家身份的有效性判断
    	if(getSeller(parentUserId)){
			return parentUserId;
		}else{			
			return 1L;
		}
    		
    }
    
    private Boolean getSeller(Long userId) throws DistributionException {
            // 如果卖家没有缓存，则从数据库获取
           SellerDTO seller = sellerManager.getByUserId(userId);
           if(seller == null || seller.getStatus() == 0){
        	   return false;
           }
           return true;
    }
    
    
    private Long getParentId(Long userId) throws DistributionException {
        Long parentUserId;
            // 如果不包含，则从数据库获取
            SellerRelationshipDTO relationshipDTO = relationshipManager.getByUserId(userId);
            if (relationshipDTO == null) {
            	 parentUserId = 1L;
            }else{
            	 parentUserId = relationshipDTO.getParentId();
            }
            
        return parentUserId;
    }

}
