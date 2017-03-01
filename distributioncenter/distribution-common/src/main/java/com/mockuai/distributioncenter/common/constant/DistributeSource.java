package com.mockuai.distributioncenter.common.constant;

/**
 * Created by duke on 16/5/16.
 */
public enum DistributeSource {
    // 开店分拥
    OPEN_SHOP_DIST(0),
    // 销售分拥
    SALE_DIST(1),
    // 团队分拥
    TEAM_DIST(2),
    // 嗨客分享分拥
    SHARE_DIST(3),
    // 非海客分享分拥
    NOSHARE_DIST(4),
    //嗨客自购分拥
    PURCHASE_DIST(5),
    //非嗨客自购分拥
    NOPURCHASE_DIST(6);
    
    private int source;

    DistributeSource(int source) {
        this.source = source;
    }

    public int getSource() {
        return source;
    }
}
