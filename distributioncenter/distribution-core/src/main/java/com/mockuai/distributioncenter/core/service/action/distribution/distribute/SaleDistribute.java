package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import com.mockuai.distributioncenter.common.constant.SellerStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.Calculator;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.Finder;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.checker.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by duke on 16/5/16.
 */
public class SaleDistribute implements Distribute {
    private static final Logger log = LoggerFactory.getLogger(SaleDistribute.class);

    private Calculator calculator;
    private Finder<SellerDTO> finder;

    public SaleDistribute(Calculator calculator, Finder<SellerDTO> finder) {
        this.calculator = calculator;
        this.finder = finder;
    }

    @Override
    public void distribution(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException {
        // 开始进行销售分拥
        if (seller.getStatus() == SellerStatus.ACTIVE.getStatus()) {
            calculator.calculate(seller, orderDTO);
        } else {
            log.info("seller are not active");
            // 如果卖家没有激活，则分拥给一号店
            SellerDTO platformSeller = finder.find(seller, new Checker<SellerDTO>() {
                @Override
                public boolean check(SellerDTO o1, SellerDTO o2) {
                    return false;
                }
            });
            calculator.calculate(platformSeller, orderDTO);
        }
    }
}
