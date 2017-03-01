package com.mockuai.giftscenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.MarketingManager;
import com.mockuai.marketingcenter.client.MarketingClient;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guansheng on 2016/7/15.
 */
@Service
public class MarketingManagerImpl implements MarketingManager {

    private static final Logger logger = LoggerFactory.getLogger(GiftsPacketManagerImpl.class);

    @Autowired
    private MarketingClient marketingClient;



    @Override
    public List<ActivityCouponDTO> queryActivityCoupon(ActivityCouponQTO activityCouponQTO, String appKey) throws GiftsException {
        Response<List<ActivityCouponDTO>> response =    marketingClient.queryActivityCoupon(activityCouponQTO,appKey);
        if(response.isSuccess()){
            return response.getModule();
        }
        logger.error("marketingClient.queryActivityCoupon occur exception:{}",response.getMessage());
        throw  new GiftsException(ResponseCode.MARKETING_ERROR,response.getMessage());
    }

    @Override
    public Boolean grantActivityCoupons(long receiverId, long couponId, int num,int grantSource ,String appKey) throws GiftsException {

        Response<Boolean> response =  marketingClient.grantActivityCouponWithNumber(couponId,receiverId,num,grantSource,appKey);

        if(!response.isSuccess()){
            logger.error("receiverId:{},couponId:{},num:{},grantSource:{},appKey:{}, marketingClient.grantActivityCoupons occur error:{}",
                    receiverId,couponId,num,grantSource,appKey,  response.getMessage());
            return false;
        }
        return true;

    }
}
