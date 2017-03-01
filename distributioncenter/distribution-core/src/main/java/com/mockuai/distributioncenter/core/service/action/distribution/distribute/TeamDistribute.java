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
 * <p/>
 * 团队分拥逻辑
 */
public class TeamDistribute implements Distribute {
    private static final Logger log = LoggerFactory.getLogger(TeamDistribute.class);

    private Calculator calculator;
    private Finder<SellerDTO> finder;

    public TeamDistribute(Calculator calculator, Finder<SellerDTO> finder) {
        this.calculator = calculator;
        this.finder = finder;
    }

    @Override
    public void distribution(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException {

        SellerDTO platformSeller = finder.find(seller, new Checker<SellerDTO>() {
            @Override
            public boolean check(SellerDTO o1, SellerDTO o2) {
                return false;
            }
        });

        // 销售一级分拥
        orderDTO.setDistLevel(1);
        SellerDTO seller1 = finder.find(seller, new Checker<SellerDTO>() {
            @Override
            public boolean check(SellerDTO o1, SellerDTO o2) {
                // 父级的级别是大于等于子级的级别的
                if (o1.getLevel() <= o2.getLevel()) {
                    // 满足条件的父级是可以参与分拥的
                    return true;
                }
                return false;
            }
        });

        if (seller1.getStatus().equals(SellerStatus.INACTIVE.getStatus())) {
            // 如果一级卖家未激活，则分给一号店
            calculator.calculate(platformSeller, orderDTO);
        } else {
            calculator.calculate(seller1, orderDTO);
        }

        // 销售二级分拥
        orderDTO.setDistLevel(2);
        SellerDTO seller2 = finder.find(seller1, new Checker<SellerDTO>() {
            @Override
            public boolean check(SellerDTO o1, SellerDTO o2) {
                // 父级的级别是严格大于子级的级别的
                if (o1.getLevel() < o2.getLevel()) {
                    // 满足条件的父级是可以参与分拥的
                    return true;
                }
                return false;
            }
        });

        // 如果等级高的不存在，则查找平级的
        if (seller2.isMaster()) {
            seller2 = finder.find(seller1, new Checker<SellerDTO>() {
                @Override
                public boolean check(SellerDTO o1, SellerDTO o2) {
                    // 父级的级别是大于等于子级的级别的
                    if (o1.getLevel() <= o2.getLevel()) {
                        // 满足条件的父级是可以参与分拥的
                        return true;
                    }
                    return false;
                }
            });
        }

        if (seller2.getStatus().equals(SellerStatus.INACTIVE.getStatus())) {
            calculator.calculate(platformSeller, orderDTO);
        } else {
            calculator.calculate(seller2, orderDTO);
        }

        // 销售三级分拥
        orderDTO.setDistLevel(3);
        SellerDTO seller3 = finder.find(seller2, new Checker<SellerDTO>() {
            @Override
            public boolean check(SellerDTO o1, SellerDTO o2) {
                // 父级的级别是严格大于子级的级别的
                if (o1.getLevel() < o2.getLevel()) {
                    // 满足条件的父级是可以参与分拥的
                    return true;
                }
                return false;
            }
        });

        // 如果等级高的不存在，则查找平级的
        if (seller3.isMaster()) {
            seller3 = finder.find(seller2, new Checker<SellerDTO>() {
                @Override
                public boolean check(SellerDTO o1, SellerDTO o2) {
                    // 父级的级别是大于等于子级的级别的
                    if (o1.getLevel() <= o2.getLevel()) {
                        // 满足条件的父级是可以参与分拥的
                        return true;
                    }
                    return false;
                }
            });
        }

        if (seller3.getStatus().equals(SellerStatus.INACTIVE.getStatus())) {
            calculator.calculate(platformSeller, orderDTO);
        } else {
            calculator.calculate(seller3, orderDTO);
        }
    }
}
