package com.mockuai.marketingcenter.common.constant;

/**限时购活动发布状态
 * Created by huangsiqian on 2016/11/7.
 */
public enum LimitTimeIssueStatus {
    /**
     * 未发布
     */
    NOISSUE(0),
    /**
     * 已发布
     */
    ISSUE(1);

    private Integer value;

    LimitTimeIssueStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}

