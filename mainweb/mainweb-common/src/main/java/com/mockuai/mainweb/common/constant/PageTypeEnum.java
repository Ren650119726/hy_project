package com.mockuai.mainweb.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum PageTypeEnum {


	HOME_PAGE(1,"主页"),
    
    PROMOTION_PAGE(2,"推广页")
    ;

	PageTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private int code;

    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PageTypeEnum getByCode(int code){
        for(PageTypeEnum type:values()){
            if(type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
        for (PageTypeEnum institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}
