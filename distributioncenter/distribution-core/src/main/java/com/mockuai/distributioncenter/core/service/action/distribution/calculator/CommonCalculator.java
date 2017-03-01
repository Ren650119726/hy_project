package com.mockuai.distributioncenter.core.service.action.distribution.calculator;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.constant.DistLevelEnum;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.ItemSkuDistPlanManager;

/**
 * Created by duke on 16/5/16.
 *
 * 销售分拥计算
 */
@Service
public class CommonCalculator implements NewCalculator {
    private static final Logger log = LoggerFactory.getLogger(CommonCalculator.class);

    @Autowired
    private DistRecordManager distRecordManager;
    
    @Autowired
    private ItemSkuDistPlanManager itemSkuDistPlanManager;
    
    /*@Autowired
    private GainsSetManager gainsSetManager;*/
    
    @Override
    public boolean calculate(final Long userID ,final DistributionItemDTO itemDTO,GainsSetDTO gainsSetDTO) throws DistributionException {
//    		GainsSetDTO gainsSetDTO = gainsSetManager.get();
    		if (gainsSetDTO == null) {
                log.error("no gainsSetDTO exists");
                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no gainsSetDTO exists");
            }
    		BigDecimal yongjin = null ;
    		BigDecimal haibi = null ;
    		/*if(itemDTO.getDistLevel().equals(1)){
    			
    			BigDecimal bd1 = new BigDecimal(gainsSetDTO.getSelfGains()); 
    			BigDecimal bd2 = new BigDecimal(gainsSetDTO.getSelfHib()); 
    			yongjin =  bd1.divide(new BigDecimal(100)).doubleValue();
    			haibi = bd2.divide(new BigDecimal(100)).doubleValue();
    			
    			
    		}else */
    		if (itemDTO.getDistLevel().equals(DistLevelEnum.FIRST_LVL.getCode())){
    			 
    			BigDecimal bd1 = new BigDecimal(gainsSetDTO.getOneGains()); 
    			BigDecimal bd2 = new BigDecimal(gainsSetDTO.getOneHib()); 
    			yongjin =  bd1.divide(new BigDecimal(100));
    			haibi = bd2.divide(new BigDecimal(100));
    			
    		}else if(itemDTO.getDistLevel().equals(DistLevelEnum.SECOND_LVL.getCode())){
    			BigDecimal bd1 = new BigDecimal(gainsSetDTO.getTwoGains()); 
    			BigDecimal bd2 = new BigDecimal(gainsSetDTO.getTwoHib()); 
    			yongjin =  bd1.divide(new BigDecimal(100));
    			haibi = bd2.divide(new BigDecimal(100));
    			
    		}
    	
    	
    		// 开始分拥计算
    		DistRecordDTO recordDTO = genDistRecord(userID, yongjin, itemDTO,DistributionType.REAL_AMOUNT.getType());
    		distRecordManager.add(recordDTO);
    	
            DistRecordDTO recordDTO1 = genDistRecord(userID, haibi, itemDTO,DistributionType.HI_COIN_AMOUNT.getType());
            distRecordManager.add(recordDTO1);
        return true;
    }

    private DistRecordDTO genDistRecord(final Long userID,
    									BigDecimal distRatio,
                                        final DistributionItemDTO itemDTO,Integer type) throws DistributionException {
    	
    	ItemSkuDistPlanDTO  itemSkuDistPlanDTO  = itemSkuDistPlanManager.getByItemSkuId(itemDTO.getItemSkuId());
    	if (itemSkuDistPlanDTO == null) {
            log.error("no item calculate plan exists, ItemSkuId: {}",itemDTO.getItemSkuId());
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "no item calculate plan exists");
        }
    	Double plan = itemSkuDistPlanDTO.getDistGainsRatio();
    	
    	
    	
        DistRecordDTO recordDTO = new DistRecordDTO();
        recordDTO.setOrderId(itemDTO.getOrderId());
        recordDTO.setOrderSn(itemDTO.getOrderSn());
        recordDTO.setSellerId(userID);
        recordDTO.setItemId(itemDTO.getItemId());
        recordDTO.setItemSkuId(itemDTO.getItemSkuId());
        recordDTO.setBuyerId(itemDTO.getUserId());
        recordDTO.setStatus(DistributionStatus.UNDER_DISTRIBUTION.getStatus());
        recordDTO.setDistRatio(distRatio.doubleValue());
        recordDTO.setType(type);
        recordDTO.setSource(itemDTO.getSource());
        recordDTO.setShopId(1L);
        recordDTO.setUnitPrice(itemDTO.getUnitPrice());
        recordDTO.setNumber(itemDTO.getNumber());
        //商品总价
        recordDTO.setGainsRatio(plan);

        //订单金额已经计算好了，不需要乘以数量
      //  Long totalAmount = recordDTO.getUnitPrice() * recordDTO.getNumber();

        Long totalAmount = recordDTO.getUnitPrice();

        //分拥总数
//        Long fenyong = Math.round(plan * totalAmount);
        BigDecimal fenyong = new BigDecimal(plan).multiply(new BigDecimal(totalAmount));
//        recordDTO.setDistAmount(Math.round(distRatio.doubleValue() * fenyong));
        recordDTO.setDistAmount(distRatio.multiply(fenyong).setScale(0, BigDecimal.ROUND_HALF_UP).longValue()); /* 嗨币和实际金额的比值是1:100 */
        return recordDTO;
    }
}
