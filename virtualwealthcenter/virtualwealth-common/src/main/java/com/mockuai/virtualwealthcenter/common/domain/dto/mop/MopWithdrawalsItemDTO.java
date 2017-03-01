package com.mockuai.virtualwealthcenter.common.domain.dto.mop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by edgar.zr on 5/21/2016.
 */
public class MopWithdrawalsItemDTO implements Serializable {
    private String number;
    private Long amount;
    private String bankName;
    private String bankNo;
    private String refusalReason;
    private byte type;

    private List<StatusItem> statusList;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public List<StatusItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusItem> statusList) {
        this.statusList = statusList;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public static class StatusItem implements Serializable {
        private Integer status;
        private String time;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}