package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/4/19.
 */
public enum ItemUnit {

    GUAN(1,"罐"),
    JIAN(2,"件"),
    PING(3,"瓶"),
    GE(4,"个"),
    XIANG(5,"箱"),
    HE(6,"盒"),
    BAO(7,"包"),
    ZHI(8,"支"),
    BA(9,"把"),
    TAO(10,"套"),
    KUAI(11,"块"),
    DAI(12,"袋"),
    ZHANG(13,"张"),
    ZHI2(14,"只"),
    GEN(15,"根"),
    FEN(16,"份"),
    DUI(17,"对"),
    TAI(18,"台"),

    ;

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ItemUnit(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ItemUnit getItemUnit(Integer code) {

        if(code == null){
            return null;
        }

        for (ItemUnit itemUnit : ItemUnit.values()) {
            if (itemUnit.code == code.intValue()) {
                return itemUnit;
            }
        }
        return null;
    }

    public static ItemUnit getItemUnit(String name) {
        for (ItemUnit itemUnit : ItemUnit.values()) {
            if (itemUnit.name.equals(name)) {
                return itemUnit;
            }
        }
        return null;
    }
}
