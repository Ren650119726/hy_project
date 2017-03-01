package com.mockuai.distributioncenter.common.domain.qto;

import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
public class SellerQTO extends BaseQTO {
    private Long parentId;
    private Long levelId;
    private String realName;
    private List<Long> userIds;
    private List<Long> ids;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
