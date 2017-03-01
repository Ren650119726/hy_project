package com.mockuai.rainbowcenter.common.constant;

/**
 * Created by lizg on 2016/8/4.
 */
public enum ExpressEnum {

    YTO_EXPRESS_CODE("YTO","yuantong","圆通速递"),

    ZTO_EXPRESS_CODE("ZTO","zhongtong","中通速递"),

    YUNDA_EXPRESS_CODE("YUNDA","yunda","韵达快运"),

    STO_EXPRESS_CODE("STO","shentong","申通快递"),

    YZXB_EXPRESS_CODE("YZXB","ems","邮政小包"),

    EMS_EXPRESS_CODE("EMS","ems","EMS"),

    TTKDEX_EXPRESS_CODE("TTKDEX","tiantian","海航天天快递"),

    OTHER_EXPRESS_CODE("other","other","其他快递"),

    SF_EXPRESS_CODE("SF", "shunfeng","顺丰速运");

    private String code1;

    private String code2;

    private String description;

     ExpressEnum(String code1, String code2,String description) {
        this.code1 = code1;
        this.code2 = code2;
        this.description = description;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
