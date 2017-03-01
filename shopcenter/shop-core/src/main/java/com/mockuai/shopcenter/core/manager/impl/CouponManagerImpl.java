package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.CouponManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/15.
 */
@Service
public class CouponManagerImpl implements CouponManager {

    @Resource
    private MarketingClient marketingClient;


    @Override
    public List<ActivityCouponDTO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey) throws ShopException{

        Response<List<ActivityCouponDTO>> response;

        try {
            response = marketingClient.queryActivityCoupon(activityCouponQTO, appKey);
        }catch (Exception e){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,"查询优惠券时出现问题");
        }

        if(response.isSuccess()){
            return response.getModule();
        }else{
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR,response.getResCode()+response.getMessage());
        }

    }
}
