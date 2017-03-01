package com.mockuai.distributioncenter.common.domain.qto;

import java.util.Date;
import java.util.List;

/**
 * Created by duke on 16/5/12.
 */
public class SellerRelationshipQTO extends BaseQTO {
    private List<Long> parentIds;
    private Integer status;
    private Date startTime;
    private Date endTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Long> getParentIds() {
        return parentIds;
    }

    public void setParentIds(List<Long> parentIds) {
        this.parentIds = parentIds;
    }
}
