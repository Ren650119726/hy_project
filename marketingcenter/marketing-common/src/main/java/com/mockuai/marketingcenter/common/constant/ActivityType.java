package com.mockuai.marketingcenter.common.constant;

/**商品中间表中活动状态
 * Created by huangsiqian on 2016/10/26.
 */
public enum ActivityType {
    /**
     * 限时购
     */
    LIMITEDPURCHASE(0),
    /**
     * 满减送
     */
    FULLREDUCTION(1);



    private Integer value;

    ActivityType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

}
