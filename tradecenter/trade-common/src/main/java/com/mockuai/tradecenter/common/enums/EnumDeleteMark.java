package com.mockuai.tradecenter.common.enums;


/**
 * 支付方式状态枚举
 * @author 
 *
 */
public enum EnumDeleteMark {

    UN_DELETE(0,"正常"),
    DELETE(1,"逻辑删除")
    ;

    private EnumDeleteMark(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	private Integer code;

    private String description;
    
    
    public Integer getCode() {
		return code;
	}

	public String getDescription() {
        return description;
    }

    public static EnumDeleteMark getByCode(Integer code){
        for(EnumDeleteMark type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }


}
