package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class VirtualWealthUidDTO {
    private Long virtualWealthId;
    private Long creatorId;

    public Long getVirtualWealthId() {
        return virtualWealthId;
    }

    public void setVirtualWealthId(Long virtualWealthId) {
        this.virtualWealthId = virtualWealthId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}