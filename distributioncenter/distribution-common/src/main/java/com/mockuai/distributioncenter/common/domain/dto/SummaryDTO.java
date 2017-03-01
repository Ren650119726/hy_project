package com.mockuai.distributioncenter.common.domain.dto;

import java.io.Serializable;

/**
 * Created by duke on 16/5/16.
 */
public class SummaryDTO implements Serializable, Comparable<SummaryDTO> {
    private Long orderCount;
    private Long inCome;
    private Long inviterCount;
    private Long pv;
    private String date;

    public SummaryDTO() {
        this.orderCount = 0L;
        this.inCome = 0L;
        this.inviterCount = 0L;
        this.pv = 0L;
        this.date = null;
    }

    public int compareTo(SummaryDTO o) {
        return 0 - this.date.compareTo(o.date);
    }

    public void increaseOrderCount(Long count) {
        this.orderCount += count;
    }

    public void increaseInCome(Long income) {
        this.inCome += income;
    }

    public void increaseInviterCount(Long inviterCount) {
        this.inviterCount += inviterCount;
    }

    public void increasePv(Long pv) {
        this.pv += pv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public Long getInCome() {
        return inCome;
    }

    public void setInCome(Long inCome) {
        this.inCome = inCome;
    }

    public Long getInviterCount() {
        return inviterCount;
    }

    public void setInviterCount(Long inviterCount) {
        this.inviterCount = inviterCount;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }
}
