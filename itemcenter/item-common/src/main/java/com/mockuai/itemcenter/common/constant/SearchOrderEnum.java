package com.mockuai.itemcenter.common.constant;

/**
 * Created by yindingyu on 16/3/1.
 */
public enum SearchOrderEnum {
	SALE_VOLUMENEW(0, "销量"),
	
    SALE_VOLUME(1, "销量"),

    PRICE(2, "价格"),

    LATEST(3, "最新"),

    COMMENTS(4, "评论"),
    //历史字段
    MAX_DIRECT_COMMISSION(5, "微小店最高直接佣金"),
    COMMISSON(6,"佣金");


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

    SearchOrderEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
