package com.mockuai.distributioncenter.core.service.action.distribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.CommonCalculator;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.Distribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.NewDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.NoPurchaseDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.NoShareDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.PurchaseDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.SaleDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.distribute.ShareDistribute;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.NewSimpleFinder;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.SimpleFinder;

/**
 * Created by duke on 16/5/17.
 */
@Service
public class DistributionAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(TransAction.class);

    
    @Autowired
    private CommonCalculator commonCalculator;
    
    @Autowired
    private SellerRelationshipManager relationshipManager;
    
    @Autowired
    private SellerManager sellerManager;
    
    @Autowired
    private GainsSetManager gainsSetManager;
    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        DistributionOrderDTO distributionOrderDTO =  (DistributionOrderDTO) request.getParam("distributionOrderDTO");
        if (distributionOrderDTO == null) {
            log.error("distributionOrderDTO are null");
            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "distributionOrderDTO is null");
        }

        for (DistributionItemDTO distributionItemDTO : distributionOrderDTO.getItemDTOs()) {
        	
        	//分拥来源
        	/*if(distributionItemDTO.getSource() == DistributeSource.NOSHARE_DIST.getSource()){

              NewDistribute newDistribute = new NoShareDistribute(this.commonCalculator, this.gainsSetManager);
              newDistribute.distribution(distributionItemDTO);
              
        	}else if (distributionItemDTO.getSource() == DistributeSource.SHARE_DIST.getSource()){
        		
        		NewDistribute newDistribute = new ShareDistribute(this.commonCalculator,new NewSimpleFinder(relationshipManager, sellerManager),this.gainsSetManager);
        		newDistribute.distribution(distributionItemDTO);
        		
        	}else */
        	if(distributionItemDTO.getSource() == DistributeSource.NOPURCHASE_DIST.getSource()){
        		
        		 NewDistribute newDistribute = new NoPurchaseDistribute(this.commonCalculator,new NewSimpleFinder(relationshipManager, sellerManager),this.gainsSetManager);
                 newDistribute.distribution(distributionItemDTO);
        		
        	}else if(distributionItemDTO.getSource() == DistributeSource.PURCHASE_DIST.getSource()){
        		
        		NewDistribute newDistribute = new PurchaseDistribute(this.commonCalculator, new NewSimpleFinder(relationshipManager, sellerManager), this.gainsSetManager);
                newDistribute.distribution(distributionItemDTO);
        		
        	}
        	
        }

        return new DistributionResponse(true);
    }
    
    
//    @Override
//    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
//        Request request = context.getRequest();
//        List<DistributionOrderDTO> distributionOrderDTOs = (List<DistributionOrderDTO>) request.getParam("distributionOrderDTOs");
//
//        if (distributionOrderDTOs == null) {
//            log.error("distributionOrderDTOs are null");
//            throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "distributionOrderDTO is null");
//        }
//
//        for (DistributionOrderDTO distributionOrderDTO : distributionOrderDTOs) {
//            // 获得直接卖家
//            SellerDTO seller = sellerManager.get(distributionOrderDTO.getSellerId());
//            if (seller == null) {
//                log.error("seller not exists, sellerId: {}", distributionOrderDTO.getSellerId());
//                throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "seller not exists");
//            }
//
//            if (distributionOrderDTO.getSource() == DistributeSource.OPEN_SHOP_DIST.getSource()) {
//                // 如果是开店分拥的订单，则进行开店分拥
//                Distribute distribute = new OpenShopDistribute(
//                        this.openShopCalculator,
//                        new SimpleFinder(sellerRelationshipManager, sellerManager));
//                distribute.distribution(seller, distributionOrderDTO);
//            } else {
//                // 设置订单中商品的销售分拥比率
//                for (DistributionItemDTO itemDTO : distributionOrderDTO.getItemDTOs()) {
//                    ItemSkuDistPlanDTO planDTO = itemSkuDistPlanManager.getByItemSkuId(itemDTO.getItemSkuId());
//                    if (planDTO == null) {
//                        log.error("no item calculate plan exists, itemSkuId: {}", itemDTO.getItemSkuId());
//                        throw new DistributionException(ResponseCode.DISTRIBUTION_ERROR, "item dist plan not exists");
//                    }
//                    itemDTO.setSaleRatio(planDTO.getSaleDistRatio());
//                }
//
//                // 进行销售分拥
//                Distribute distribute = new SaleDistribute(this.saleCalculator,
//                        new SimpleFinder(sellerRelationshipManager, sellerManager));
//                distributionOrderDTO.setSource(DistributeSource.SALE_DIST.getSource());
//                distribute.distribution(seller, distributionOrderDTO);
//
//                // 进行团队分拥
//                distribute = new TeamDistribute(this.teamCalculator,
//                        new SimpleFinder(sellerRelationshipManager, sellerManager));
//                distributionOrderDTO.setSource(DistributeSource.TEAM_DIST.getSource());
//                distribute.distribution(seller, distributionOrderDTO);
//            }
//        }
//
//        return new DistributionResponse(true);
//    }

    @Override
    public String getName() {
        return ActionEnum.DO_DISTRIBUTION.getActionName();
    }
}
