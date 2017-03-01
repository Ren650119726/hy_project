package com.mockuai.mainweb.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum PublishStatusEnum {


	UN_PUBLISH(0,"未发布"),
    
    PUBLISHED(1,"已发布")
    ;

	PublishStatusEnum(int code, String description) {
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

    public static PublishStatusEnum getByCode(int code){
        for(PublishStatusEnum type:values()){
            if(type.getCode() == code){
                return type;
            }
        }
        return null;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> enumDataMap = new HashMap<Integer, String>();
        for (PublishStatusEnum institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDescription());
        }
        return enumDataMap;
    }

}
