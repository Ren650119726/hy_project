package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.distributioncenter.common.constant.DistLevelEnum;
import com.mockuai.distributioncenter.common.constant.SelfBuyEnum;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.NewCalculator;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.NewFinder;

/**
 * Created by duke on 16/5/16.
 */
public class NoPurchaseDistribute implements NewDistribute {
    private static final Logger log = LoggerFactory.getLogger(NoPurchaseDistribute.class);

    private NewCalculator newCalculator;
    private NewFinder newFinder;
    
    private GainsSetManager gainsSetManager;

    public NoPurchaseDistribute(NewCalculator calculator, NewFinder finder,GainsSetManager gainsSetManager) {
        this.newCalculator = calculator;
        this.newFinder = finder;
        this.gainsSetManager = gainsSetManager;
    }
    
    
    @Override
    public void distribution(final DistributionItemDTO itemDTO) throws DistributionException {
    	
		GainsSetDTO gainsSetDTO = gainsSetManager.get();
		
		// 直接上级
		Long userId = doCalculate(itemDTO,gainsSetDTO,DistLevelEnum.FIRST_LVL.getCode(),0l);
		
		// 直接上级的上级
		doCalculate(itemDTO,gainsSetDTO,DistLevelEnum.SECOND_LVL.getCode(),userId);		
		
    }
    
    private Long doCalculate(DistributionItemDTO itemDTO,GainsSetDTO gainsSetDTO,int distLevel,Long userId) throws DistributionException{
    	// 直接上级
    	itemDTO.setDistLevel(distLevel);
		Long parentId = 0l;
		
		// 自购分佣开关开启二级分给自己
		if( DistLevelEnum.FIRST_LVL.getCode() == distLevel){
			parentId = newFinder.find(itemDTO.getUserId());			
		}
		
		if(DistLevelEnum.SECOND_LVL.getCode() == distLevel){
			parentId = newFinder.find(userId);
		}
			
		newCalculator.calculate(parentId, itemDTO , gainsSetDTO);
		
		return parentId;
    }
}
