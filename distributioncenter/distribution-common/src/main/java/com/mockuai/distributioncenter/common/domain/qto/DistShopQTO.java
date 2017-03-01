package com.mockuai.distributioncenter.common.domain.qto;

import java.util.List;

/**
 * Created by 冠生 on 16/3/4.
 */
public class DistShopQTO extends BaseQTO {
    /**
     * ID
     */
    private List<Long> ids;

    /**
     * 卖家ID
     * */
    private List<Long> sellerIds;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getSellerIds() {
        return sellerIds;
    }

    public void setSellerIds(List<Long> sellerIds) {
        this.sellerIds = sellerIds;
    }
}
