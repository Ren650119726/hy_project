package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.NewCalculator;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.NewFinder;

/**
 * Created by duke on 16/5/16.
 */
public class ShareDistribute implements NewDistribute {
    private static final Logger log = LoggerFactory.getLogger(ShareDistribute.class);
    
    private NewCalculator newCalculator;
    private NewFinder newFinder;
    
    private GainsSetManager gainsSetManager;

    public ShareDistribute(NewCalculator calculator, NewFinder finder,GainsSetManager gainsSetManager) {
        this.newCalculator = calculator;
        this.newFinder = finder;
        this.gainsSetManager = gainsSetManager;
    }
    @Override
    public void distribution(final DistributionItemDTO itemDTO) throws DistributionException {
    	GainsSetDTO gainsSetDTO = gainsSetManager.get();
    	Long buyUserId = itemDTO.getUserId();
    	Long userid = newFinder.find(buyUserId);
    	
    	//等于1 就是 没有上级。
    	if(userid == 1){
    		//第一笔分拥
        	itemDTO.setDistLevel(2);
        	newCalculator.calculate(1L, itemDTO,gainsSetDTO);
    		
    		//第二笔分拥
    		itemDTO.setDistLevel(3);
    		//Long userID = newFinder.find(itemDTO.getShareUserId());
    		newCalculator.calculate(1L, itemDTO,gainsSetDTO);
    	}else{
    		//第一笔分拥
        	itemDTO.setDistLevel(2);
        	newCalculator.calculate(userid, itemDTO,gainsSetDTO);
    		
    		//第二笔分拥
    		itemDTO.setDistLevel(3);
    		Long userID = newFinder.find(userid);
    		newCalculator.calculate(userID, itemDTO,gainsSetDTO);
    	}
    	
    }
}
