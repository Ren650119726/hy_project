package com.mockuai.itemcenter.common.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luliang on 15/7/17.
 */
public class LimitEntity implements Serializable {

    private Integer limitCount;
    private Date beginTime;
    private Date endTime;
    private ExtraOp extraOp;

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ExtraOp getExtraOp() {
        return extraOp;
    }

    public void setExtraOp(ExtraOp extraOp) {
        this.extraOp = extraOp;
    }

    enum ExtraOp{

        DO_NOTHING("doNothing"),
        CLEAR_SALE_BEGIN("clearSaleBegin"),
        CLEAR_SALE_END("clearSaleEnd"),
        CLEAR_SALE_BEGIN_AND_SALE_AND("clearSaleBeginAndSaleEnd");

        private String opName;

        private ExtraOp(String opName) {
            this.opName = opName;
        }

        public static ExtraOp getExtraOp(String opName) {
            for (ExtraOp ae : ExtraOp.values()) {
                if (ae.opName.equals(opName)) {
                    return ae;
                }
            }
            return null;
        }

        public static  ExtraOp defaultOp(){
            return  ExtraOp.DO_NOTHING;
        }

        public String getOpName() {
            return opName;
        }
    }
}
