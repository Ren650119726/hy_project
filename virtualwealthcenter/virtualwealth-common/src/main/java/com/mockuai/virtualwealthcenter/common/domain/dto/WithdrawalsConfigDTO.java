package com.mockuai.virtualwealthcenter.common.domain.dto;

import java.io.Serializable;

public class WithdrawalsConfigDTO implements Serializable {
    private int id;


    private int userId;

    private String wdConfigText;
    private Long wdConfigMinimum;

    private Long wdConfigMaximum;

    private Byte wdIsflag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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