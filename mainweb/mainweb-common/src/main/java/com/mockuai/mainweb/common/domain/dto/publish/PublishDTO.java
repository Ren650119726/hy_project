package com.mockuai.mainweb.common.domain.dto.publish;


/**
 * Created by Administrator on 2016/9/22.
 */
public class PublishDTO {

    private int code;
    private String msg;
    private ComponentDTO data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ComponentDTO getData() {
        return data;
    }

    public void setData(ComponentDTO data) {
        this.data = data;
    }
}
