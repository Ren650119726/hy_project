package com.mockuai.distributioncenter.core.service.action.distribution.distribute;

import com.mockuai.distributioncenter.common.constant.LevelEnum;
import com.mockuai.distributioncenter.common.constant.SellerStatus;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.service.action.distribution.calculator.Calculator;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.checker.Checker;
import com.mockuai.distributioncenter.core.service.action.distribution.finder.Finder;

/**
 * Created by duke on 16/5/16.
 */
public class OpenShopDistribute implements Distribute {
    private Calculator calculator;
    private Finder<SellerDTO> finder;

    public OpenShopDistribute(Calculator calculator, Finder<SellerDTO> finder) {
        this.calculator = calculator;
        this.finder = finder;
    }

    @Override
    public void distribution(final SellerDTO seller, final DistributionOrderDTO orderDTO) throws DistributionException {
        // 获得一号店卖家
        SellerDTO platformSeller = finder.find(seller, new Checker<SellerDTO>() {
            @Override
            public boolean check(SellerDTO o1, SellerDTO o2) {
                return false;
            }
        });

        // 如果卖家不能参与分拥，则分给一号店
        // 一级分拥
        orderDTO.setDistLevel(1);
        if (seller.getStatus().equals(SellerStatus.INACTIVE.getStatus())) {
            calculator.calculate(platformSeller, orderDTO);
        } else {
            // 一级分拥
            calculator.calculate(seller, orderDTO);
        }

        // 二级分拥
        orderDTO.setDistLevel(2);
        
        SellerDTO seller2 = new SellerDTO();
        // 一级推荐人为客户经理 二级分拥规则为  父级的级别是大于等于子级的级别的
        if(seller.getLevelId()  == LevelEnum.LV2.getValue()){
	        	// 首先查找等级高的
	            seller2 = finder.find(seller, new Checker<SellerDTO>() {
	            @Override
	            public boolean check(SellerDTO o1, SellerDTO o2) {
	                // 父级的级别是严格大于子级的级别的
	                if (o1.getLevel() <= o2.getLevel()) {
	                    // 满足条件的父级是可以参与分拥的
	                    return true;
	                }
	                return false;
	            }
	        });
        }else{
	        	// 首先查找等级高的
	            seller2 = finder.find(seller, new Checker<SellerDTO>() {
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
	            seller2 = finder.find(seller, new Checker<SellerDTO>() {
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
        }
        

        if (seller2.getStatus().equals(SellerStatus.INACTIVE.getStatus())) {
            calculator.calculate(platformSeller, orderDTO);
        } else {
            calculator.calculate(seller2, orderDTO);
        }

        // 接下来处理三级分拥
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