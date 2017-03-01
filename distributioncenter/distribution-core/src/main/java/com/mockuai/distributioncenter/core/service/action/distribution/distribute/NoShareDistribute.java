package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.NewCalculator;

/**
 * Created by duke on 16/5/16.
 */
public class NoShareDistribute implements NewDistribute {
    private static final Logger log = LoggerFactory.getLogger(NoShareDistribute.class);

    private NewCalculator newCalculator;
    private GainsSetManager gainsSetManager;

    public NoShareDistribute(NewCalculator calculator, GainsSetManager gainsSetManager) {
        this.newCalculator = calculator;
        this.gainsSetManager = gainsSetManager;
    }
    
    @Override
    public void distribution( final DistributionItemDTO itemDTO) throws DistributionException {
    	GainsSetDTO  gainsSetDTO  = gainsSetManager.get();
    	if (gainsSetDTO == null) {
            log.error("no gainsSetDTO exists");
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no gainsSetDTO exists");
        }
    	//非海客分享是否拿佣金
    	if(gainsSetDTO.getConsumerEnjoy() == 0){
    		//第一笔佣金
    		itemDTO.setDistLevel(2);
    		newCalculator.calculate(1L, itemDTO,gainsSetDTO);
    		
    		//第二笔佣金
    		itemDTO.setDistLevel(3);
    		newCalculator.calculate(1L, itemDTO,gainsSetDTO);
    		
    	}else{
    		log.info("");
    	}
    }
}
