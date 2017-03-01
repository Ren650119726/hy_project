package com.mockuai.giftscenter.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;


/**
 * Created by guansheng on 2016/7/14.
 */
public class ActionGiftMarketingDTO  extends  BaseDTO{

    private ActivityCouponDTO item ;

    private int count;

    public ActivityCouponDTO getItem() {
        return item;
    }

    public void setItem(ActivityCouponDTO item) {
        this.item = item;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
