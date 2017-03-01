package com.mockuai.shopcenter.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luliang on 15/8/14.
 */
public class LastDaysCountDTO implements Serializable {

    private Date createDay;
    private Integer whichDay;
    private Integer num;

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public Integer getWhichDay() {
        return whichDay;
    }

    public void setWhichDay(Integer whichDay) {
        this.whichDay = whichDay;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
