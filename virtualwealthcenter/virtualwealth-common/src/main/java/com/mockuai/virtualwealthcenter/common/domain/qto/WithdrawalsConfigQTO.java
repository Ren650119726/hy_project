package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawalsConfigQTO implements Serializable {


    private Long id;

    private Long userId;


    private String wdConfigText;

    private Long wdConfigMinimum;

    private Long wdConfigMaximum;

    private Byte wdIsflag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWdConfigText() {
        return wdConfigText;
    }

    public void setWdConfigText(String wdConfigText) {
        this.wdConfigText = wdConfigText;
    }

    public Long getWdConfigMinimum() {
        return wdConfigMinimum;
    }

    public void setWdConfigMinimum(Long wdConfigMinimum) {
        this.wdConfigMinimum = wdConfigMinimum;
    }

    public Long getWdConfigMaximum() {
        return wdConfigMaximum;
    }

    public void setWdConfigMaximum(Long wdConfigMaximum) {
        this.wdConfigMaximum = wdConfigMaximum;
    }

    public Byte getWdIsflag() {
        return wdIsflag;
    }

    public void setWdIsflag(Byte wdIsflag) {
        this.wdIsflag = wdIsflag;
    }
}