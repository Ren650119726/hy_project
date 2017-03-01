package com.mockuai.deliverycenter.common.constant;

import java.util.HashMap;
import java.util.Map;

public enum ExpressCodeEnum {
	
	HUITONG("huitong","百事汇通","http://www.800bestex.com"),

	YUANTONG("yuantong","圆通","http://wap.yto.net.cn"),

    SHUNFENG("shunfeng","顺丰","http://www.sf-express.com/mobile/cn/sc/dynamic_functions/waybill/waybill_query_by_billno.html"),

    YUNDA("yunda","韵达","http://mobile.yundasys.com:2137/mobileweb/pages/index.html"),

    ZTO("zhongtong","中通","http://wap.zto.com"),

    YZXB("ems","邮政小包","http://www.ems.com.cn"),

    EMS("ems","EMS","http://www.ems.com.cn"),

    TIANTIAN("tiantian","天天","http://www.ttkdex.com"),

    STO("shentong","申通","http://wap.sto.cn"),

    YOUSU("yousu","优速","http://www.uce.cn/mobile/service/expressTracking.html"),

    QUANFENG("quanfeng","全峰","http://www.qfkd.com.cn"),

    SUER("suer","suer","http://www.sure56.com/Demo_contatc.asp"),

    OTHER("other","其他快递","http://m.kuaidi100.com/?from=openv");
	
	private String code;
	
	private String desc;
	
	private String url;

	
	private ExpressCodeEnum(String code, String desc, String url) {
		this.code = code;
		this.desc = desc;
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static ExpressCodeEnum getByCode(String code){
        for(ExpressCodeEnum type:values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (ExpressCodeEnum institution : values()) {
            enumDataMap.put(institution.getCode(), institution.getDesc());
        }
        return enumDataMap;
    }
	
}
