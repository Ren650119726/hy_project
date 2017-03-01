package com.mockuai.common.uils.staticpage; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by hy on 2016/10/30.
 */
public enum  SerialPageEnum {

    MAIN("main"),ITEM("item");

    public String getPrefix() {
        return prefix;
    }

    private String prefix;
    SerialPageEnum(String prefix){
        this.prefix = prefix;
    }
}
